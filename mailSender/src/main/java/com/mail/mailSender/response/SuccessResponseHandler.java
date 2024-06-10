package com.mail.mailSender.response;

import org.springframework.http.HttpStatus;

import java.util.Optional;

public class SuccessResponseHandler {

    public static <T> SuccessResponse<T> buildSuccessResponse(HttpStatus status, String message, Optional<T> data) {
        return new SuccessResponse<>(status, message, data);
    }

}
