package com.parceldelivery.auth.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String value) {
        super(String.format("User with %s already exists.", value));
    }
}
