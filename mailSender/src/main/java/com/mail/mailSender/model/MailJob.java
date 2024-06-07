package com.mail.mailSender.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
public class MailJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private  Long mailerId;
    @NotNull
    private String subject;
    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    private String mailBody;
    @ElementCollection
    @NotNull
    @Valid
    private List<@Email String> recipients;

}
