package com.global.GobalRent.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;


@Getter
public class ExceptionImpl extends RuntimeException {
    private final HttpStatus status;

    public ExceptionImpl(String message, HttpStatus status){
        super(message);
        this.status=status;
    }
}
