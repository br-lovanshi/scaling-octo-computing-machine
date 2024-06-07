package com.mail.mailSender.service.smtp;

import com.mail.mailSender.Repository.SMTPConfigRepository;
import com.mail.mailSender.model.SMTPConfig;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SMTPConfigInterfaceImpl implements SMTPConfigInterface {

    @Autowired
    SMTPConfigRepository smtpConfigRepository;

    @Override
    public ResponseEntity<?> storeSMTPConfig(SMTPConfig smtpConfig) throws Exception {
        boolean emailExists = smtpConfigRepository.existsByEmail(smtpConfig.getEmail());
        if(emailExists) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        smtpConfigRepository.save(smtpConfig);
        return new ResponseEntity<>("Data store successfully.", HttpStatus.CREATED);
    }

    @Override
    public SMTPConfig getSMTPConfig(Long id) throws Exception {
        Optional<SMTPConfig> smtpConfig = smtpConfigRepository.findById(id);
        if(smtpConfig.isEmpty()) throw new Exception("Data not found.");
        return smtpConfig.get();
    }

    @Override
    public List<SMTPConfig> getAllSMTPConfig() throws Exception {
        List<SMTPConfig>smtpConfigs =  smtpConfigRepository.findAll();
        if(smtpConfigs.isEmpty()) throw new Exception("Data not found.");
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
            throw new Exception("Data not found.");
        }
    }
}
