package com.mail.mailSender.service.mailJob;

import com.mail.mailSender.enums.StatusEnum;
import com.mail.mailSender.repository.MailJobRepository;
import com.mail.mailSender.repository.SMTPConfigRepository;
import com.mail.mailSender.dto.mailJob.GetAllJobDTO;
import com.mail.mailSender.dto.mailJob.MailJobCreateReqeust;
import com.mail.mailSender.exception.Conflict;
import com.mail.mailSender.exception.NotFoundException;
import com.mail.mailSender.model.MailJob;
import com.mail.mailSender.model.SMTPConfig;
import com.mail.mailSender.service.fileUpload.UploadFilesInterface;
import com.mail.mailSender.service.sendMail.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MailJobService implements MailJobInterface{

    @Autowired
    private MailJobRepository mailJobrepository;
    @Autowired
    private SMTPConfigRepository smtpConfigRepository;
    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private UploadFilesInterface fileService;
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
        mailJob.setStatus(StatusEnum.PENDING);

        //storing image
        if(mailJobCreateReqeust.getImage() != null && !mailJobCreateReqeust.getImage().isEmpty()){
            Map cloudinaryFile = fileService.upload(mailJobCreateReqeust.getImage(),"utilityMailBodyImages");
            mailJob.setImage((String)cloudinaryFile.get("secure_url"));
            mailJob.setImagePublicId((String) cloudinaryFile.get("public_id"));
        }

        //store files
        if(mailJobCreateReqeust.getAttachment() != null && !mailJobCreateReqeust.getAttachment().isEmpty()){
            Map attachment = fileService.upload(mailJobCreateReqeust.getAttachment(),"utilityMailAttachments");
            mailJob.setAttachment((String)attachment.get("secure_url"));
            mailJob.setAttachmentPublicId((String) attachment.get("public_id"));
        }

        MailJob mailJob1 = mailJobrepository.save(mailJob);

        //sending mail asynchronously
        sendMailService.chunkMail(mailJob1.getId());
        return mailJob1;
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

        if(mailJobOptional.get().getImagePublicId() != null){
            fileService.deleteFile(mailJobOptional.get().getImagePublicId());
        }

        if(mailJobOptional.get().getAttachmentPublicId() != null){
            fileService.deleteFile(mailJobOptional.get().getAttachmentPublicId());
        }
        mailJobrepository.deleteById(id);
    }
}
