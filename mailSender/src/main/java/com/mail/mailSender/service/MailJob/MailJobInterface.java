package com.mail.mailSender.service.MailJob;

import com.mail.mailSender.exception.InvalidIdException;
import com.mail.mailSender.model.MailJob;

import java.util.List;

public interface MailJobInterface {

    public MailJob storeMailJob(MailJob mailJob) throws Exception;
    public MailJob getMailJob(Long id) throws InvalidIdException;
    public List<MailJob> getAllMailJob() throws Exception;
    public void deleteMailJob(Long id) throws Exception;
}
