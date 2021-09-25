package com.codecafe.bookshop.error.errorhandler;

import com.codecafe.bookshop.error.ErrorResponse;
import com.codecafe.bookshop.error.exception.BookNotFoundException;
import com.codecafe.bookshop.error.exception.BookOutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderErrorHandler {

    @ExceptionHandler({BookNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleBookNotFoundError(Exception ex) {
        ErrorResponse apiError = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler({BookOutOfStockException.class})
    public ResponseEntity<ErrorResponse> handleBookOutOfStockError(Exception ex) {
        ErrorResponse apiError = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

}