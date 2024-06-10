package com.mail.mailSender.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SMTPConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String appName;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String smtpServer;
    @NotEmpty
    private String smtpPort;
    @NotEmpty
    private String password;

    @OneToMany(mappedBy = "smtpConfig")
    @JsonManagedReference
    private List<MailJob> mailJobs;

}
