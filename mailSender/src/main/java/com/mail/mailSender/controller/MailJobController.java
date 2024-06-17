package com.mail.mailSender.controller;

import com.mail.mailSender.dto.mailJob.GetAllJobDTO;
import com.mail.mailSender.dto.mailJob.MailJobCreateReqeust;
import com.mail.mailSender.dto.mailJob.MailJobResponseDTO;
import com.mail.mailSender.enums.StatusEnum;
import com.mail.mailSender.exception.BadRequestException;
import com.mail.mailSender.model.MailJob;
import com.mail.mailSender.response.SuccessResponse;
import com.mail.mailSender.response.SuccessResponseHandler;
import com.mail.mailSender.service.mailJob.MailJobInterface;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mail-job")
public class MailJobController {

    @Autowired
    private MailJobInterface mailJobInterface;
    private static final String[] ALLOWED_TYPES = { "image/jpeg", "image/png", "image/webp", "image/gif", "image/svg+xml" };

    private static final Logger logger = LoggerFactory.getLogger(MailJobController.class);

    @PostMapping()
    public ResponseEntity<SuccessResponse<?>> addMailJob(
            @RequestParam("smtpConfigId") Long smtpConfigId,
            @RequestParam("subject") String subject,
            @RequestParam("mailbody") String mailbody,
            @RequestParam("recipients[]") List<String> recipients,
            @RequestParam(value = "image", required = false)MultipartFile image,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment
            ) throws Exception {

        boolean isValid = false;
        if(image != null && !image.isEmpty()){
            for (String allowedType : ALLOWED_TYPES) {
                if (allowedType.equals(image.getContentType())) {
                    isValid = true;
                }
            }
            if(!isValid)
                throw new BadRequestException("Invalid image file. Only JPEG, PNG, WEBP, GIF, and SVG are allowed.");
        }


        MailJobCreateReqeust requestDTO = new MailJobCreateReqeust();
        requestDTO.setSmtpConfigId(smtpConfigId);
        requestDTO.setSubject(subject);
        requestDTO.setMailBody(mailbody);
        requestDTO.setRecipients(new HashSet<>(recipients));
        if (image != null && !image.isEmpty()) {
            requestDTO.setImage(image);
        }

        if (attachment != null && !attachment.isEmpty()) {
            requestDTO.setAttachment(attachment);
        }

        MailJob mailJob = mailJobInterface.storeMailJob(requestDTO);

        MailJobResponseDTO mailJobResponseDTO = new MailJobResponseDTO(mailJob);

        SuccessResponse<?> successResponse = SuccessResponseHandler.buildSuccessResponse(
                HttpStatus.CREATED,
                "Mail job created successfully.",
                Optional.of(mailJobResponseDTO)
        );

        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }


    @GetMapping()
    public ResponseEntity<SuccessResponse<?>> getAllMailJobs(){

            List<GetAllJobDTO> mailJobs = mailJobInterface.getAllMailJob();
            SuccessResponse<?> successResponse = SuccessResponseHandler.buildSuccessResponse(HttpStatus.OK,"Data retrived successfully.",Optional.of(mailJobs));

            return new ResponseEntity<>(successResponse,HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> getMailJobById(@PathVariable Long id){
        MailJob mailJob = mailJobInterface.getMailJob(id);
        MailJobResponseDTO mailJobResponseDTO = new MailJobResponseDTO(mailJob);

        SuccessResponse<?> successResponse = SuccessResponseHandler.buildSuccessResponse(HttpStatus.OK,"Data retrived successfully.",Optional.of(mailJobResponseDTO));
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteMailJobById(@PathVariable Long id){
        mailJobInterface.deleteMailJob(id);
        SuccessResponse<?> successResponse = SuccessResponseHandler.buildSuccessResponse(HttpStatus.NO_CONTENT, "Data Deleted successfully.",Optional.empty());
        return new ResponseEntity<>(successResponse,HttpStatus.NO_CONTENT);
    }
}
