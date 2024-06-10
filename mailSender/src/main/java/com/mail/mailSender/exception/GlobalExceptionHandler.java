package com.mail.mailSender.exception;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<MyErrorDetails> buildErrorResponse(HttpStatus status, Exception ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails();
        myErrorDetails.setStatusCode(status);
        myErrorDetails.setMessage(ex.getMessage());
        myErrorDetails.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(myErrorDetails,status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<MyErrorDetails> myHandler(BadRequestException ie, WebRequest req) throws Exception{
        return this.buildErrorResponse(HttpStatus.BAD_REQUEST,ie);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MyErrorDetails> notFoundExceptionHandler(NotFoundException nfe, WebRequest req) throws Exception{
        return this.buildErrorResponse(HttpStatus.NOT_FOUND,nfe);
    }

    @ExceptionHandler(Conflict.class)
    public ResponseEntity<MyErrorDetails> conflict(Conflict conflict, WebRequest req) throws Exception{
        return this.buildErrorResponse(HttpStatus.CONFLICT,conflict);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MyErrorDetails> handleValidationExcetion(MethodArgumentNotValidException  e, WebRequest re) throws Exception{
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });

        MyErrorDetails myErrorDetails = new MyErrorDetails();
        myErrorDetails.setErrors(errors);
        myErrorDetails.setStatusCode(HttpStatus.BAD_REQUEST);
        myErrorDetails.setMessage("Validation failed");
        myErrorDetails.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyErrorDetails> myExceptionHandler(Exception e, WebRequest req) throws Exception{
        return this.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e);
    }



}
