package com.mail.mailSender.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse<T> {
    private HttpStatus statusCode;
    private String message;
    private Optional<T> data;
}
