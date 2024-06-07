package com.mail.mailSender.response;

import org.springframework.http.HttpStatus;

public class SuccessResponseHandler {

    public static <T> SuccessResponse<T> buildSuccessResponse(T data, String message, HttpStatus status) {
        return new SuccessResponse<>(status, message, data);
    }

}
