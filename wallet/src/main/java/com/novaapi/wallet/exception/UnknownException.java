package com.novaapi.wallet.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UnknownException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnknownException(String message){
        super(message);
    }
}
