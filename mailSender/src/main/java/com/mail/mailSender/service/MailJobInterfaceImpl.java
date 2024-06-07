package com.mail.mailSender.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.mailSender.Repository.EmailInformationRepository;
import com.mail.mailSender.model.SendEmailInformation;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.OptionalInt;

@Service
public class MailJobInterfaceImpl implements MailJobInterface {

    @Autowired
    EmailInformationRepository emailInformationRepository;

    @Autowired
    MailSenderService mailSenderService;

    @Override
    public String storeMailJob(SendEmailInformation sendEmailInformation) throws Exception {

        try {

            System.out.println(sendEmailInformation.getRecipients());
            emailInformationRepository.save(sendEmailInformation);
            mailSenderService.sendEmail(sendEmailInformation);
            return "Mail job store successfully.";
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public SendEmailInformation getMailJob(Long id) throws Exception{

        Optional<SendEmailInformation> optional = emailInformationRepository.findById(id);

        if(optional.isEmpty()) throw new BadRequestException("Invalid id.");

        return optional.get();
    }
}
