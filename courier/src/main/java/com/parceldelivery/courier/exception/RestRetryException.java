package com.parceldelivery.courier.exception;

public class RestRetryException extends RuntimeException {

    public RestRetryException() {
    }

    public RestRetryException(String message) {
        super(message);
    }

    public RestRetryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestRetryException(Throwable cause) {
        super(cause);
    }

    public RestRetryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
