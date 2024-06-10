package com.mail.mailSender.dto.mailJob.SMTPConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllSMTPConfigDTO {

    private Long id;
    private String appName;
    private String email;

}
