package com.mail.mailSender.dto.mailJob;

import com.mail.mailSender.enums.StatusEnum;
import com.mail.mailSender.model.MailJob;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MailJobResponseDTO {

    private Long id;
    private Long smtpConfigId;
    private String subject;
    private String mailBody;
    private StatusEnum status;
    private Integer allMails;
    private Integer sentMail;
    private Integer pendingMail;
    private Set<String> recipients;
    private String image;

    public MailJobResponseDTO(MailJob mailJob){
        this.setId(mailJob.getId());
        this.setSmtpConfigId(mailJob.getSmtpConfig().getId());
        this.setSubject(mailJob.getSubject());
        this.setMailBody(mailJob.getMailBody());
        this.setRecipients(mailJob.getRecipients());
        this.setStatus(mailJob.getStatus());
        this.setSentMail(mailJob.getSentMailCount());
        this.setAllMails(mailJob.getRecipients().size());
        this.setPendingMail(this.getAllMails()-this.getSentMail());
        this.setImage(mailJob.getImage());
    }

}
