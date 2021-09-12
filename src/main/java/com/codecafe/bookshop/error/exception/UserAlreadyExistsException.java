package com.codecafe.bookshop.error.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("A user with this email already exists");
    }

}