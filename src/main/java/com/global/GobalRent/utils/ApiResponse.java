package com.global.GobalRent.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

    private boolean success;

    private String message;

    private T data;
}
