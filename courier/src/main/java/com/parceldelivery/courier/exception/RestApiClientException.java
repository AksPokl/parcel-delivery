package com.parceldelivery.courier.exception;

import com.parceldelivery.model.response.ErrorResponse;
import lombok.Getter;

@Getter
public class RestApiClientException extends RuntimeException {

    private String requestUrl;
    private ErrorResponse errorResponse;

    public RestApiClientException(String requestUrl, ErrorResponse errorResponse) {
        this.requestUrl = requestUrl;
        this.errorResponse = errorResponse;
    }
}
