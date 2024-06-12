package com.mail.mailSender.dto.mailJob;

import com.mail.mailSender.enums.StatusEnum;
import com.mail.mailSender.model.MailJob;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailJobCreateReqeust {

    @NotNull
    private Long smtpConfigId;

    @NotEmpty(message = "Subject cannot be empty")
    private String subject;

    @NotEmpty(message = "Mail body cannot be empty")
    @Column(columnDefinition = "LONGTEXT")
    private String mailBody;

    @ElementCollection
    @NotEmpty(message = "Recipients list cannot be empty")
    @Valid
    private List<@Email(message = "Recipient email should be valid") String> recipients;
    private StatusEnum status;

    private MultipartFile file;

    public MailJobCreateReqeust(MailJob mailJob){
        this.setSmtpConfigId(mailJob.getSmtpConfig().getId());
        this.setSubject(mailJob.getSubject());
        this.setMailBody(mailJob.getMailBody());
        this.setRecipients(mailJob.getRecipients());
        this.setStatus(mailJob.getStatus());
    }
}
