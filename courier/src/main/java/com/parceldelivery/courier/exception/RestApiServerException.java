package com.parceldelivery.courier.exception;

import com.parceldelivery.model.response.ErrorResponse;
import lombok.Getter;

@Getter
public class RestApiServerException extends RuntimeException {

    private String requestUrl;
    private ErrorResponse errorResponse;

    public RestApiServerException(String requestUrl, ErrorResponse errorResponse) {
        this.requestUrl = requestUrl;
        this.errorResponse = errorResponse;
    }
}
