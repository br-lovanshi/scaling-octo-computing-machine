package com.mail.mailSender.service.smtp;

import com.mail.mailSender.dto.mailJob.SMTPConfig.GetAllSMTPConfigDTO;
import com.mail.mailSender.repository.SMTPConfigRepository;
import com.mail.mailSender.exception.Conflict;
import com.mail.mailSender.exception.NotFoundException;
import com.mail.mailSender.model.SMTPConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SMTPConfigInterfaceImpl implements SMTPConfigInterface {

    @Autowired
    SMTPConfigRepository smtpConfigRepository;

    @Override
    public void storeSMTPConfig(SMTPConfig smtpConfig) throws Exception {
        boolean emailExists = smtpConfigRepository.existsByEmail(smtpConfig.getEmail());
        if(emailExists) {
            throw new Conflict("Email already exists");
        }
        smtpConfigRepository.save(smtpConfig);
    }

    @Override
    public SMTPConfig getSMTPConfig(Long id) throws Exception {
        Optional<SMTPConfig> smtpConfig = smtpConfigRepository.findById(id);
        if(smtpConfig.isEmpty()) throw new NotFoundException("Data not found.");
        return smtpConfig.get();
    }

    @Override
    public List<GetAllSMTPConfigDTO> getAllSMTPConfig() throws Exception {
        List<GetAllSMTPConfigDTO>smtpConfigs =  smtpConfigRepository.getAllSMTPConfigDTOs();
        if(smtpConfigs.isEmpty()) throw new NotFoundException("Data not found.");
        return smtpConfigs;
    }

    @Override
    public void deleteSmtpConfig(Long id) throws Exception {
        Optional<SMTPConfig> smtpConfig = smtpConfigRepository.findById(id);
        if(smtpConfig.isPresent())
        {
            smtpConfigRepository.deleteById(id);
        }
        else{
            throw new NotFoundException("Data not found.");
        }
    }
}
