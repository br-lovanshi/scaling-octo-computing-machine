package com.mail.mailSender.controller;

import com.mail.mailSender.dto.smtpConfig.GetAllSMTPConfigDTO;
import com.mail.mailSender.model.SMTPConfig;
import com.mail.mailSender.response.SuccessResponse;
import com.mail.mailSender.response.SuccessResponseHandler;
import com.mail.mailSender.service.smtp.SMTPConfigInterface;
import com.mail.mailSender.service.smtp.SMTPConfigInterfaceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/smtp-config")
public class SMTPConfigController {

    @Autowired
    SMTPConfigInterface smtpConfigService;

    @PostMapping()
    public ResponseEntity<SuccessResponse<?>> storeSMTP(@RequestBody @Valid  SMTPConfig smtpConfig) throws Exception {
        smtpConfigService.storeSMTPConfig(smtpConfig);
        SuccessResponse<?> successResponse = SuccessResponseHandler.buildSuccessResponse(HttpStatus.CREATED,"SMTP configuration stored successfully.", Optional.empty());
        return new ResponseEntity<>(successResponse,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> getSMTPConfigById(@PathVariable Long id) throws Exception{

            SMTPConfig smtpConfig = smtpConfigService.getSMTPConfig(id);
            SuccessResponse<SMTPConfig> successResponse = SuccessResponseHandler.buildSuccessResponse(HttpStatus.OK,"Data retrieved successfully.",Optional.of(smtpConfig));
            return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<SuccessResponse<?>> getAllSMTPConfig() throws Exception{

        List<GetAllSMTPConfigDTO> smtpConfigList = smtpConfigService.getAllSMTPConfig();
        SuccessResponse<?> successResponse = SuccessResponseHandler.buildSuccessResponse(HttpStatus.OK,"Data retrieved successfully.",Optional.of(smtpConfigList));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSMTPConfigById(@PathVariable Long id) throws Exception{

            smtpConfigService.deleteSmtpConfig(id);
            SuccessResponse<?> successResponse = SuccessResponseHandler.buildSuccessResponse(HttpStatus.NO_CONTENT,"Data removed successfully.", Optional.empty());
            return new ResponseEntity<>(successResponse,HttpStatus.NO_CONTENT);

    }

}
