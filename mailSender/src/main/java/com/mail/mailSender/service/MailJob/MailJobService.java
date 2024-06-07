package com.mail.mailSender.service.MailJob;

import com.mail.mailSender.Repository.MailJobRepository;
import com.mail.mailSender.Repository.SMTPConfigRepository;
import com.mail.mailSender.exception.InvalidIdException;
import com.mail.mailSender.model.MailJob;
import com.mail.mailSender.model.SMTPConfig;
import com.mail.mailSender.service.MailSenderService;
import com.mail.mailSender.service.sendMail.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MailJobService implements MailJobInterface{

    @Autowired
    private MailJobRepository mailJobrepository;
    @Autowired
    private SMTPConfigRepository smtpConfigRepository;
    @Autowired
    private SendMailService sendMailService;
    @Override
    public MailJob storeMailJob(MailJob mailJob) throws Exception {

        boolean smtpExists = smtpConfigRepository.existsById(mailJob.getMailerId());
        if(smtpExists == false) throw new Exception("Mailer not found.");

        boolean exists = mailJobrepository.existsBySubject(mailJob.getSubject());
        if(exists) throw new Exception("Mail job already exists.");

         MailJob mailJob1 = mailJobrepository.save(mailJob);

        sendMailService.asyncMailSender(mailJob.getId());
         return mailJob1;
    }

    @Override
    public MailJob getMailJob(Long id) throws InvalidIdException {
        Optional<MailJob> mailJobOptional = mailJobrepository.findById(id);
        if(mailJobOptional.isEmpty()) throw new InvalidIdException("Mail job not found.");
        return mailJobOptional.get();
    }

    @Override
    public List<MailJob> getAllMailJob() throws Exception {
        List<MailJob> mailJobs = mailJobrepository.findAll();
        if(mailJobs.isEmpty()) throw new Exception("Mail job not found.");
        return mailJobs;
    }

    @Override
    public void deleteMailJob(Long id) throws Exception {
        Optional<MailJob> mailJobOptional = mailJobrepository.findById(id);
        if(mailJobOptional.isEmpty()){
            throw new Exception("Mail job not found.");
        }
        mailJobrepository.deleteById(id);
    }
}
