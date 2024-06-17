package com.mail.mailSender.service.mailJob;

import com.mail.mailSender.dto.mailJob.GetAllJobDTO;
import com.mail.mailSender.dto.mailJob.MailJobCreateReqeust;
import com.mail.mailSender.exception.Conflict;
import com.mail.mailSender.exception.NotFoundException;
import com.mail.mailSender.model.MailJob;

import java.util.List;

public interface MailJobInterface {

    public MailJob storeMailJob(MailJobCreateReqeust mailJobCreateReqeust) throws NotFoundException, Conflict, Exception;
    public MailJob getMailJob(Long id) throws NotFoundException;
    public List<GetAllJobDTO> getAllMailJob() throws NotFoundException;
    public void deleteMailJob(Long id) throws NotFoundException;
}
