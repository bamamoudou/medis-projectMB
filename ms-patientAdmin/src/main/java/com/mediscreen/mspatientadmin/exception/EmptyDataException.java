package com.mediscreen.mspatientadmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyDataException extends RuntimeException{
    public EmptyDataException(String message) {
        super(message);
    }
}
