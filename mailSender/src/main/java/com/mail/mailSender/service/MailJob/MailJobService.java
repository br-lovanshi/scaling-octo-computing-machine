package com.mail.mailSender.service.MailJob;

import com.mail.mailSender.repository.MailJobRepository;
import com.mail.mailSender.repository.SMTPConfigRepository;
import com.mail.mailSender.dto.mailJob.GetAllJobDTO;
import com.mail.mailSender.dto.mailJob.MailJobCreateReqeust;
import com.mail.mailSender.exception.Conflict;
import com.mail.mailSender.exception.NotFoundException;
import com.mail.mailSender.model.MailJob;
import com.mail.mailSender.model.SMTPConfig;
import com.mail.mailSender.service.sendMail.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MailJobService implements MailJobInterface{

    @Autowired
    private MailJobRepository mailJobrepository;
    @Autowired
    private SMTPConfigRepository smtpConfigRepository;
    @Autowired
    private SendMailService sendMailService;

    @Override
    public MailJob storeMailJob(MailJobCreateReqeust mailJobCreateReqeust) throws Conflict, NotFoundException, Exception {

        Optional<SMTPConfig> smtpExists = smtpConfigRepository.findById(mailJobCreateReqeust.getSmtpConfigId());
        if(smtpExists.isEmpty()) throw new NotFoundException("SMTP config not found.");

        boolean exists = mailJobrepository.existsBySubject(mailJobCreateReqeust.getSubject());
        if(exists) throw new Conflict("Mail job already exists.");

        MailJob mailJob = new MailJob();
        mailJob.setSmtpConfig(smtpExists.get());
        mailJob.setSubject(mailJobCreateReqeust.getSubject());
        mailJob.setMailBody(mailJobCreateReqeust.getMailBody());
        mailJob.setRecipients(mailJobCreateReqeust.getRecipients());

        MailJob mailJob1 = mailJobrepository.save(mailJob);

        sendMailService.asyncMailSender(mailJob1.getId());
        return mailJob1;
//
//         MailJobCreateReqeust mailJobCreateReqeust1 = new MailJobCreateReqeust();
//         mailJobCreateReqeust1.setMailBody(mailJob1.getMailBody());
//         mailJobCreateReqeust1.setId(mailJob1.getId());
//         mailJobCreateReqeust1.setRecipients(mailJob1.getRecipients());
//         mailJobCreateReqeust1.setSmtpConfigId(mailJob1.getSmtpConfig().getId());
//         mailJobCreateReqeust1.setSubject(mailJob1.getSubject());
//         return mailJobCreateReqeust1;
    }

    @Override
    public MailJob getMailJob(Long id) throws NotFoundException {
        Optional<MailJob> mailJobOptional = mailJobrepository.findById(id);
        if(mailJobOptional.isEmpty()) throw new NotFoundException("Mail job not found.");
        return mailJobOptional.get();
    }

    @Override
    public List<GetAllJobDTO> getAllMailJob() throws NotFoundException {
//        List<MailJob> mailJobs = mailJobrepository.findAll();
//        if(mailJobs.isEmpty()) throw new NotFoundException("Mail job not found.");
//
//        return mailJobs.stream().map(mailjob -> new GetAllJobDTO(mailjob.getId(),mailjob.getSubject())).collect(Collectors.toList());

        List<GetAllJobDTO> allJobDTOS = mailJobrepository.getAllJobDTOs();
        if(allJobDTOS.isEmpty()) throw new NotFoundException("Mail job not found.");

        return allJobDTOS;
    }

    @Override
    public void deleteMailJob(Long id) throws NotFoundException {
        Optional<MailJob> mailJobOptional = mailJobrepository.findById(id);
        if(mailJobOptional.isEmpty()){
            throw new NotFoundException("Mail job not found.");
        }
        mailJobrepository.deleteById(id);
    }
}
