package com.mail.mailSender.controller;

import com.mail.mailSender.model.SMTPConfig;
import com.mail.mailSender.service.smtp.SMTPConfigInterface;
import com.mail.mailSender.service.smtp.SMTPConfigInterfaceImpl;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class SMTPConfigController {

    @Autowired
    SMTPConfigInterface smtpConfigInterface = new SMTPConfigInterfaceImpl();

    @PostMapping("/smtp-config")
    public ResponseEntity<?> storeSMTP(@RequestBody @Valid  SMTPConfig smtpConfig, BindingResult result){
        if(result.hasErrors()){
            Map<String,String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try{
        return smtpConfigInterface.storeSMTPConfig(smtpConfig);
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/smtp-config/{id}")
    public ResponseEntity<?> getSMTPConfigById(@PathVariable Long id){
        try{
            SMTPConfig smtpConfig = smtpConfigInterface.getSMTPConfig(id);
            return new ResponseEntity<>(smtpConfig,HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/smtp-config")
    public ResponseEntity<?> getAllSMTPConfig(){
        try{
            List<SMTPConfig> smtpConfigList = smtpConfigInterface.getAllSMTPConfig();
            return new ResponseEntity<>(smtpConfigList,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/smtp-config/{id}")
    public ResponseEntity<?> deleteSMTPConfigById(@PathVariable Long id){
        try{
            smtpConfigInterface.deleteSmtpConfig(id);
            return new ResponseEntity<>("App removed successfully.",HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }


}
