package com.mail.mailSender.service.smtp;

import com.mail.mailSender.model.SMTPConfig;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SMTPConfigInterface {

    public ResponseEntity<?> storeSMTPConfig(SMTPConfig smtpConfig) throws Exception;
    public SMTPConfig getSMTPConfig(Long id) throws Exception;
    public List<SMTPConfig> getAllSMTPConfig() throws Exception;
    public  void deleteSmtpConfig(Long id) throws  Exception;
}
