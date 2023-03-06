package com.parceldelivery.courier.service;


import com.parceldelivery.courier.exception.RestRetryException;
import com.parceldelivery.model.request.CreateAccountApiRequest;
import com.parceldelivery.model.response.CreateEntityResponse;
import org.springframework.retry.annotation.Retryable;

public interface AccountService {

    @Retryable(value = RestRetryException.class)
    CreateEntityResponse createCourier(CreateAccountApiRequest createAccountRequest, String token);
}
