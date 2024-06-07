package com.mail.mailSender.service;


import com.mail.mailSender.model.SendEmailInformation;
import io.micrometer.observation.GlobalObservationConvention;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Properties;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("$(spring.mail.username)")
    private String fromEmailId;

    private Transport transport;

    private static final Logger LOGGER =  LoggerFactory.getLogger(MailSenderService.class);

    public void sendEmail(List<String> recipient, String body, String subject){

       try{
           SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setBcc(recipient.toArray(new String[recipient.size()]));
        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);

        LOGGER.info("Mail send successfully");
       }catch
       (Exception e){
           throw  e;
       }

    }

    @Async
    public void sendEmail(SendEmailInformation sendEmailInformation) throws Exception {
        try{
            String fromEmail = sendEmailInformation.getEmail();
            String password = sendEmailInformation.getPassword();
                List<String> toEmail = sendEmailInformation.getRecipients();

            Properties props = new Properties();
            props.put("mail.smtp.host", sendEmailInformation.getSmtpServer());
            props.put("mail.smtp.port", sendEmailInformation.getSmtpPort());
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");

            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            Session session = Session.getInstance(props, auth);

            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail));
            msg.setReplyTo(InternetAddress.parse(sendEmailInformation.getEmail(), false));
            msg.setSubject(sendEmailInformation.getSubject(), "UTF-8");
            msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(String.join(",", toEmail), false));

            BodyPart messageBodyPart = new MimeBodyPart();
//            sending normal text in mail body
//            messageBodyPart.setText(sendEmailInformation.getMessage());

//            sending html content inside body
                messageBodyPart.setContent(sendEmailInformation.getMessage(),"text/HTML; charset=UTF-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);

            transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(msg,msg.getRecipients(Message.RecipientType.BCC));
            transport.close();

            LOGGER.info("Mail send successfully");

        }catch (Exception e) {

            try {
                transport.close();
            }catch (Exception ignored) {

            }
            e.printStackTrace();
            throw new Exception(e.getMessage());

        }
    }

//        public void sendDynamicEmail(SendEmailInformation sendEmailInformation) {
//        try {
//            String fromEmail = sendEmailInformation.getEmailAddress();
//
//            Mailer mailer = MailerBuilder
//                    .withSMTPServer(sendEmailInformation.getSmtpServer(), sendEmailInformation.getSmtpPort(), fromEmail)
//                    .withSMTPServerPassword(sendEmailInformation.getPassword())
//                    .withTransportStrategy(TransportStrategy.SMTP_TLS)
//                    .buildMailer();
//
//            Email email = EmailBuilder.startingBlank()
//                    .from(fromEmail)
//                    .to(sendEmailInformation.getEmail())
//                    .withHeader("format", "flowed")
//                    .withSubject(sendEmailInformation.getSubject())
//                    .withPlainText(sendEmailInformation.getMessage())
//                    //.withHTMLText() for HTML messages
//                    .withContentTransferEncoding(ContentTransferEncoding.BIT8)
//                    .buildEmail();
//
//            mailer.sendMail(email);
//        } catch (MailException e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
}
