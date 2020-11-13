package com.novaapi.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InsufficientAmount extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InsufficientAmount(String message){
        super(message);
    }
}
