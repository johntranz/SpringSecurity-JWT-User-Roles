package com.huytran.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String mess){
        super(mess);
    }
    public BadRequestException(String mess,Throwable cause){
        super(mess);
    }
}
