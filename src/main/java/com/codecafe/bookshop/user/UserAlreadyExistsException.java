package com.codecafe.bookshop.user;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("A user with this email already exists");
    }

}