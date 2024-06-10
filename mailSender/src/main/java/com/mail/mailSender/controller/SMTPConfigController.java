package com.mail.mailSender.controller;

import com.mail.mailSender.dto.mailJob.SMTPConfig.GetAllSMTPConfigDTO;
import com.mail.mailSender.model.SMTPConfig;
import com.mail.mailSender.response.SuccessResponse;
import com.mail.mailSender.response.SuccessResponseHandler;
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
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SMTPConfigController {

    @Autowired
    SMTPConfigInterface smtpConfigInterface = new SMTPConfigInterfaceImpl();

    @PostMapping("/smtp-config")
    public ResponseEntity<SuccessResponse<?>> storeSMTP(@RequestBody @Valid  SMTPConfig smtpConfig) throws Exception {
        smtpConfigInterface.storeSMTPConfig(smtpConfig);
        SuccessResponse<?> successResponse = SuccessResponseHandler.buildSuccessResponse(HttpStatus.CREATED,"SMTP configuration stored successfully.", Optional.empty());
        return new ResponseEntity<>(successResponse,HttpStatus.CREATED);
    }

    @GetMapping("/smtp-config/{id}")
    public ResponseEntity<SuccessResponse<?>> getSMTPConfigById(@PathVariable Long id) throws Exception{

            SMTPConfig smtpConfig = smtpConfigInterface.getSMTPConfig(id);
            SuccessResponse<SMTPConfig> successResponse = SuccessResponseHandler.buildSuccessResponse(HttpStatus.OK,"Data retrieved successfully.",Optional.of(smtpConfig));
            return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @GetMapping("/smtp-config")
    public ResponseEntity<SuccessResponse<?>> getAllSMTPConfig() throws Exception{

        List<GetAllSMTPConfigDTO> smtpConfigList = smtpConfigInterface.getAllSMTPConfig();
        SuccessResponse<?> successResponse = SuccessResponseHandler.buildSuccessResponse(HttpStatus.OK,"Data retrieved successfully.",Optional.of(smtpConfigList));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);

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
