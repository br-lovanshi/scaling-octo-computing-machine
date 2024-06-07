package com.mail.mailSender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<MyErrorDetails> buildErrorResponse(HttpStatus status, Exception ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails(status, ex.getMessage(),LocalDateTime.now());
        return new ResponseEntity<>(myErrorDetails,status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MyErrorDetails> myHandler(IllegalArgumentException ie, WebRequest req) throws Exception{
        return this.buildErrorResponse(HttpStatus.BAD_REQUEST,ie);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<MyErrorDetails> myHandler(InvalidIdException ie, WebRequest req) throws Exception{
        return this.buildErrorResponse(HttpStatus.BAD_REQUEST,ie);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyErrorDetails> myExceptionHandler(Exception e, WebRequest req) throws Exception{
        return this.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MyErrorDetails> notFoundExceptionHandler(NotFoundException nfe, WebRequest req) throws Exception{
        return this.buildErrorResponse(HttpStatus.NOT_FOUND,nfe);
    }

}
