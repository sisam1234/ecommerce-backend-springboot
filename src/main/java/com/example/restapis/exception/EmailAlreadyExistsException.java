package com.example.restapis.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String  message){
        super(message);
    }
    
}
