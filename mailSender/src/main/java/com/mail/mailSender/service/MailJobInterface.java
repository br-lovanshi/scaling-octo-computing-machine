package com.mail.mailSender.service;

import com.mail.mailSender.model.SendEmailInformation;

import java.util.List;

public interface MailJobInterface {

    public String storeMailJob(SendEmailInformation sendEmailInformation) throws Exception;
    public SendEmailInformation getMailJob(Long id) throws Exception;
//    public List<SendEmailInformation> getAllMailJob() throws Exception;
}
