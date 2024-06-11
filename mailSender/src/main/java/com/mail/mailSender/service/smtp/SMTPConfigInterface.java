package com.mail.mailSender.service.smtp;

import com.mail.mailSender.dto.smtpConfig.GetAllSMTPConfigDTO;
import com.mail.mailSender.model.SMTPConfig;

import java.util.List;

public interface SMTPConfigInterface {

    public void storeSMTPConfig(SMTPConfig smtpConfig) throws Exception;
    public SMTPConfig getSMTPConfig(Long id) throws Exception;
    public List<GetAllSMTPConfigDTO> getAllSMTPConfig() throws Exception;
    public  void deleteSmtpConfig(Long id) throws  Exception;
}
