package com.mail.mailSender.exception;

public class Conflict extends RuntimeException {

    public Conflict(){}

    public Conflict(String msg){
        super(msg);
    }

}
