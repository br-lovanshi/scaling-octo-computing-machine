package com.mail.mailSender.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyErrorDetails {
    private HttpStatus statusCode;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public MyErrorDetails(HttpStatus badRequest, String message, LocalDateTime now) {
    }
}
