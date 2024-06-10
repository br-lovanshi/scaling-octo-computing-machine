package com.mail.mailSender.controller;

import com.mail.mailSender.exception.BadRequestException;
import com.mail.mailSender.exception.NotFoundException;
import com.mail.mailSender.model.SendEmailInformation;
import com.mail.mailSender.response.SuccessResponse;
import com.mail.mailSender.response.SuccessResponseHandler;
import com.mail.mailSender.service.MailJobInterface;
import com.mail.mailSender.service.MailJobInterfaceImpl;
import com.mail.mailSender.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private MailSenderService mailSender;

    @Autowired
    private MailJobInterface mailJobInterface = new MailJobInterfaceImpl();

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<String>> greet(@PathVariable int id){
        if(id < 10) throw new NotFoundException("Number not found");

        SuccessResponse<String> stringSuccessResponse = SuccessResponseHandler.buildSuccessResponse(
                HttpStatus.OK,
                "Request was successful",
                Optional.empty()
        );
        return new ResponseEntity<>(stringSuccessResponse, HttpStatus.OK);
    }

//    @GetMapping("/test-mail")
    public ResponseEntity<String> sendEmail(){
        List<String> recipients = new ArrayList<>();
        recipients.add("brlovanshi2001@gmail.com");
        recipients.add("brajeshlovanshi2001@gmail.com");
        recipients.add("vickylovanshi2001@gmail.com");
        recipients.add("learning.brajeshlovanshi@gmail.com");
        mailSender.sendEmail(recipients,"java Developer Bro", "Sending Mail Using Spring Boot");
        return new ResponseEntity<>("Mail sent successfully.",HttpStatus.OK);
    }


//    @PostMapping("/send-mail")
    public ResponseEntity<String> SentDynamicEmail(@RequestBody SendEmailInformation sendEmailInformation)  {
       try{
            String response = mailJobInterface.storeMailJob(sendEmailInformation);
            return ResponseEntity.ok(response);}
       catch(Exception e){
           return  ResponseEntity.status(500).body(e.getMessage());
       }
    }

//    @GetMapping("/get-mail-job/{id}")
    public ResponseEntity<?> getMailJobById(@PathVariable Long id){
        try{
            SendEmailInformation sendEmailInformation = mailJobInterface.getMailJob(id);
            return new ResponseEntity<>(sendEmailInformation,HttpStatus.OK);

        }catch (Exception e){
            return  ResponseEntity.status(400).body(e.getMessage());

        }
    }


}
