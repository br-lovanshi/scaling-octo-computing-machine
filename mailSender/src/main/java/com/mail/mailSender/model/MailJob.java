package com.mail.mailSender.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mail.mailSender.enums.StatusEnum;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MailJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Mailer ID cannot be null")
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "smtpConfig_id", referencedColumnName = "id")
    @JsonBackReference
    private SMTPConfig smtpConfig;

    @NotEmpty(message = "Subject cannot be empty")
    private String subject;

    @NotEmpty(message = "Mail body cannot be empty")
    @Column(columnDefinition = "LONGTEXT")
    private String mailBody;

    @ElementCollection
    @NotEmpty(message = "Recipients list cannot be empty")
    @Valid
    private Set<@Email(message = "Recipient email should be valid") String> recipients;
    private String image;
    private String imagePublicId;
    private String attachment;
    private String attachmentPublicId;
    private Integer sentMailCount = 0;
    private StatusEnum status = StatusEnum.PENDING;
}
