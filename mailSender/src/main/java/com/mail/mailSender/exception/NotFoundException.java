package com.mail.mailSender.exception;

import org.aspectj.weaver.ast.Not;

public class NotFoundException extends RuntimeException {

    public NotFoundException(){

    }
    public NotFoundException(String message){
        super(message);
    }
}
