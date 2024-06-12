package com.mail.mailSender.controller;

import com.mail.mailSender.dto.mailJob.GetAllJobDTO;
import com.mail.mailSender.dto.mailJob.MailJobCreateReqeust;
import com.mail.mailSender.dto.mailJob.MailJobResponseDTO;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mail-job")
public class MailJobController {

    @Autowired
    private MailJobInterface mailJobInterface;

    private static final Logger logger = LoggerFactory.getLogger(MailJobController.class);

    @PostMapping()
    public ResponseEntity<SuccessResponse<?>> addMailJob(@RequestBody @Valid MailJobCreateReqeust mailJobCreateReqeust) throws Exception {


        MailJob mailJob = mailJobInterface.storeMailJob(mailJobCreateReqeust);

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
