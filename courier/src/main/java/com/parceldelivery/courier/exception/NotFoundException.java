package com.parceldelivery.courier.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String entity, String value) {
        super(String.format("%s with %s is not found", entity, value));
    }

    public NotFoundException(String entity, String entityValue, String value) {
        super(String.format("%s with %s %s is not found", entity, entityValue, value));
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
