package com.codecafe.bookshop.error.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.codecafe.bookshop.error.ErrorResponse;
import com.codecafe.bookshop.error.exception.BookNotFoundException;

@ControllerAdvice
public class BookErrorHandler {
    @ExceptionHandler({BookNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleBookNotFoundException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(errorResponse, errorResponse.httpStatus());
    }
}