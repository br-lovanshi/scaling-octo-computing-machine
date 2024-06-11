package com.mail.mailSender.dto.mailJob;

import com.mail.mailSender.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllJobDTO {

    private Long id;
    private String subject;
    private StatusEnum status;

}
