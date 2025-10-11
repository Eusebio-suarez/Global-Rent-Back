package com.global.GobalRent.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.global.GobalRent.utils.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExceptionImpl.class)
    public ResponseEntity<ApiResponse<Object>> handleUserException(ExceptionImpl exception){

        return ResponseEntity.status(exception.getStatus())
            .body(ApiResponse.builder()
                .success(false)
                .message(exception.getMessage())
                .data("error:" +exception.getStatus())
                .build()
            );
    }
}
