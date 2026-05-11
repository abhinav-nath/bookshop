package com.codecafe.bookshop.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus httpStatus, String message, Map<String, String> errors) {
    public ErrorResponse(HttpStatus httpStatus, String message) {
        this(httpStatus, message, new HashMap<>());
    }
}