package com.stickynotes.exception;

public class EmailNotFormattedException extends  RuntimeException{

    public EmailNotFormattedException(String message){
        super(message);
    }
}