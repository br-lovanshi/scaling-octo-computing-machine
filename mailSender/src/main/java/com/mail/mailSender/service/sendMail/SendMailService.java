package com.mail.mailSender.service.sendMail;

import com.cloudinary.Url;
import com.mail.mailSender.enums.StatusEnum;
import com.mail.mailSender.repository.MailJobRepository;
import com.mail.mailSender.repository.SMTPConfigRepository;
import com.mail.mailSender.exception.Conflict;
import com.mail.mailSender.exception.NotFoundException;
import com.mail.mailSender.model.MailJob;
import com.mail.mailSender.model.SMTPConfig;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTML;
import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

@Service
@Slf4j
public class SendMailService {

    private static final int CHUNK_SIZE = 5;
    private static final int DELAY_IN_MINUTES = 2 * 60 * 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMailService.class);
    private Transport transport;

    @Autowired
    private SMTPConfigRepository smtpConfigRepository;

    @Autowired
    private MailJobRepository mailJobrepository;

    @Async
    public void asyncMailSender(Long id) throws Conflict, NotFoundException, Exception{

       try {
           Optional<MailJob> mailJobOptional = mailJobrepository.findByIdWithRecipients(id);
           if (mailJobOptional.isEmpty()) throw new NotFoundException("Mail job not found.");

           MailJob mailJob = mailJobOptional.get();

           SMTPConfig smtpConfig = mailJob.getSmtpConfig();

           Properties properties = new Properties();
           properties.put("mail.smtp.host", smtpConfig.getSmtpServer());
           properties.put("mail.smtp.port", smtpConfig.getSmtpPort());
           properties.put("mail.smtp.starttls.enable", "true");
           properties.put("mail.smtp.auth", "true");

           Authenticator auth = new Authenticator() {
               protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication(smtpConfig.getEmail(), smtpConfig.getPassword());
               }
           };

           Session session = Session.getInstance(properties, auth);

           MimeMessage msg = new MimeMessage(session);
           msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
           msg.addHeader("format", "flowed");
           msg.addHeader("Content-Transfer-Encoding", "8bit");

           msg.setFrom(new InternetAddress(smtpConfig.getEmail()));
           msg.setReplyTo(InternetAddress.parse(smtpConfig.getEmail(), false));
           msg.setSubject(mailJob.getSubject());
           msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(String.join(",", mailJob.getRecipients()), false));

           BodyPart bodyPart = new MimeBodyPart();

           bodyPart.setContent(mailJob.getMailBody(), "text/HTML; charset=UTF-8");

           Multipart multipart = new MimeMultipart();
           multipart.addBodyPart(bodyPart);

           msg.setContent(multipart);

           transport = session.getTransport("smtp");
           transport.connect();
           transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.BCC));
           transport.close();
       }catch (Exception e){
           throw new Exception(e);
       }

    }

    @Async
    public void chunkMail(Long id) throws Exception{
        Optional<MailJob> mailJobOptional = mailJobrepository.findByIdWithRecipients(id);

        try {


            if (mailJobOptional.isEmpty()) throw new NotFoundException("Mail job not found");

            MailJob mailJob = mailJobOptional.get();
            SMTPConfig smtpConfig = mailJobOptional.get().getSmtpConfig();
            List<String> recipients = mailJob.getRecipients().stream().toList();

            Properties properties = new Properties();
            properties.put("mail.smtp.host", smtpConfig.getSmtpServer());
            properties.put("mail.smtp.port", smtpConfig.getSmtpPort());
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.auth", "true");

            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpConfig.getEmail(), smtpConfig.getPassword());
                }
            };

            Session session = Session.getInstance(properties, auth);
            mailJob.setStatus(StatusEnum.PROCESSING);
            int sentMailCount = 0;
            for (int i = 0; i < recipients.size(); i += CHUNK_SIZE) {

                int endIndex = Math.min(i + CHUNK_SIZE, recipients.size());
                List<String> batchRecipients = recipients.subList(i, endIndex);
                sentMailCount += batchRecipients.size();
                sendEmail(session, smtpConfig.getEmail(), mailJob.getSubject(), batchRecipients, mailJob.getMailBody(), mailJob.getAttachment(), mailJob.getImage());

                mailJob.setSentMailCount(sentMailCount);
                mailJobrepository.save(mailJob);

                if(endIndex < recipients.size()){
                    Thread.sleep(DELAY_IN_MINUTES);
                }
            }
                LOGGER.info("Mail sent successfully.");

                if(sentMailCount >= recipients.size()){
                    LOGGER.info("Mail sent ==========> " + sentMailCount + " " + recipients.size());
                    mailJob.setStatus(StatusEnum.COMPLETED);
                    mailJobrepository.save(mailJob);
                }

        }catch(Exception e){
            mailJobOptional.get().setStatus(StatusEnum.FAILED);
            mailJobrepository.save(mailJobOptional.get());

            throw new Exception(e);
        }finally {
            if(transport != null && transport.isConnected()){
                transport.close();
            }
        }

    }

    private void sendEmail(Session session, String fromEmail, String subject, List<String> recipients, String body, String attachmentUrl, String inlineImageUrl) throws MessagingException{

        try{
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setSubject(subject);


            InternetAddress[] bccAddresses = new InternetAddress[recipients.size()];

            for (int i = 0; i < recipients.size(); i++) {
                bccAddresses[i] = new InternetAddress(recipients.get(i));
            }

            msg.setRecipients(Message.RecipientType.BCC, bccAddresses);
            Multipart multipart = new MimeMultipart();

            // Email body
//            BodyPart bodyPart = new MimeBodyPart();
//            bodyPart.setContent(body, "text/html; charset=UTF-8");
//            multipart.addBodyPart(bodyPart);
            MimeBodyPart bodyPart = new MimeBodyPart();
            String cid = "image_cid";
            String htmlText = body + "<br><img src='cid:" + cid + "' style='width:400px;'>";
            bodyPart.setContent(htmlText, "text/html; charset=UTF-8");
            multipart.addBodyPart(bodyPart);

            // Inline image part
            if (inlineImageUrl != null && !inlineImageUrl.isEmpty()) {
                URL imageUrl = new URL(inlineImageUrl);
                InputStream imageStream = imageUrl.openStream();
                String imageFileName = getFileNameFromUrl(inlineImageUrl);
                String imageMimeType = URLConnection.guessContentTypeFromName(imageFileName);

                DataSource imageDataSource = new ByteArrayDataSource(imageStream, imageMimeType != null ? imageMimeType : "application/octet-stream");
                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.setDataHandler(new DataHandler(imageDataSource));
                imagePart.setHeader("Content-ID", "<" + cid + ">");
                imagePart.setFileName(imageFileName);
                imagePart.setDisposition(MimeBodyPart.INLINE);
                multipart.addBodyPart(imagePart);
            }
            // Attachment
            if (attachmentUrl != null && !attachmentUrl.isEmpty()) {
                URL url = new URL(attachmentUrl);
                InputStream inputStream = url.openStream();
                String fileName = getFileNameFromUrl(attachmentUrl);
                String mimeType = URLConnection.guessContentTypeFromName(fileName);

                DataSource dataSource = new ByteArrayDataSource(inputStream, mimeType != null ? mimeType : "application/octet-stream");
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
                attachmentBodyPart.setFileName(fileName);
                multipart.addBodyPart(attachmentBodyPart);
            }

            msg.setContent(multipart);

            // Connect and send email
            transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (Exception e) {
            throw new MessagingException("Failed to send email: " + e.getMessage());
        }
    }
    // Method to extract filename from URL
    private String getFileNameFromUrl(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }
}
