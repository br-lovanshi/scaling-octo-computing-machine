package com.mail.mailSender.service.sendMail;

import com.mail.mailSender.repository.MailJobRepository;
import com.mail.mailSender.repository.SMTPConfigRepository;
import com.mail.mailSender.exception.Conflict;
import com.mail.mailSender.exception.NotFoundException;
import com.mail.mailSender.model.MailJob;
import com.mail.mailSender.model.SMTPConfig;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Properties;

@Service
public class SendMailService {

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

           // find smtp settings
//           Optional<SMTPConfig> smtpConfigOptional = smtpConfigRepository.findById(mailJob.getMailerId().getId());
//           if (smtpConfigOptional.isEmpty()) throw new NotFoundException("SMTP configuration not found.");

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

}
