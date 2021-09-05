package com.codecafe.bookshop.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final HttpStatus httpStatus;
    private final String message;
    private final Map<String, String> errors;

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.errors = new HashMap<>();
    }

}