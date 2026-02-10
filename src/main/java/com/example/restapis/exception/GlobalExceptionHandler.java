package com.example.restapis.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleEmailExist(EmailAlreadyExistsException ex){
        Map<String,String> response = new HashMap<>();
        response.put("status",String.valueOf(HttpStatus.CONFLICT.value()));
        response.put("message",ex.getMessage());
        return new  ResponseEntity<>(response,HttpStatus.CONFLICT);

    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleUserNotFound(UserNotFoundException ex){
         Map<String,String> response = new HashMap<>();
        response.put("status",String.valueOf(HttpStatus.CONFLICT.value()));
        response.put("message",ex.getMessage());
        return new  ResponseEntity<>(response,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String,String>> handleInvalid(InvalidCredentialsException ex){
         Map<String,String> response = new HashMap<>();
        response.put("status",String.valueOf(HttpStatus.CONFLICT.value()));
        response.put("message",ex.getMessage());
        return new  ResponseEntity<>(response,HttpStatus.CONFLICT);
    }
}
