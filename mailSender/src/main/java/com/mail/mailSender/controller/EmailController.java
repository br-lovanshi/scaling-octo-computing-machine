package com.mail.mailSender.controller;

import com.mail.mailSender.model.SendEmailInformation;
import com.mail.mailSender.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmailController {

    @Autowired
    private MailSenderService mailSender;


    @GetMapping("/")
    public String greet(){
        return "Hello Spring Boot";
    }

    @GetMapping("/mail")
    public String sendEmail(){
        List<String> recipients = new ArrayList<>();
        recipients.add("brlovanshi2001@gmail.com");
        recipients.add("brajeshlovanshi2001@gmail.com");
        recipients.add("vickylovanshi2001@gmail.com");
        recipients.add("learning.brajeshlovanshi@gmail.com");
        mailSender.sendEmail(recipients,"java Developer Bro", "Sending Mail Using Spring Boot");
        return "Mail sent successfully." ;
    }


    @PostMapping("/dynamic-mail")
    public ResponseEntity<String> SentDynamicEmail(@RequestBody SendEmailInformation sendEmailInformation) throws Exception {

        mailSender.sendEmail(sendEmailInformation);
       return ResponseEntity.ok("Mail Sending Asynchronously.");
    }

}
