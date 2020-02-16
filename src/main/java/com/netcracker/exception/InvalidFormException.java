package com.netcracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class InvalidFormException extends RuntimeException{
    public InvalidFormException(String message){
        super(message);
    }
}
