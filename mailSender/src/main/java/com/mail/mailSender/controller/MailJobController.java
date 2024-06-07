package com.mail.mailSender.controller;

import com.mail.mailSender.exception.InvalidIdException;
import com.mail.mailSender.model.MailJob;
import com.mail.mailSender.service.MailJob.MailJobInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MailJobController {

    @Autowired
    private MailJobInterface mailJobInterface;

    @PostMapping("/mail-job")
    public ResponseEntity<?> addMailJob(@RequestBody MailJob mailJob){
        try{
             MailJob mailJob1 = mailJobInterface.storeMailJob(mailJob);
            return new ResponseEntity<>( mailJob1, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mail-jobs")
    public ResponseEntity<?> getAllMailJobs(){
        try{
            List<MailJob> mailJobs = mailJobInterface.getAllMailJob();
            return new ResponseEntity<>(mailJobs,HttpStatus.OK);
        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
            throw new InvalidIdException("data Not found.");
        }

    }

    @GetMapping("/mail-job/{id}")
    public ResponseEntity<?> getMailJobById(@PathVariable Long id){
            MailJob mailJob = mailJobInterface.getMailJob(id);
            return new ResponseEntity<>(mailJob, HttpStatus.OK);
    }
}
