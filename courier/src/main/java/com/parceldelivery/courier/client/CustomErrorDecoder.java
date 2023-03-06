package com.parceldelivery.courier.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parceldelivery.courier.exception.RestApiClientException;
import com.parceldelivery.courier.exception.RestApiServerException;
import com.parceldelivery.courier.exception.RestRetryException;
import com.parceldelivery.model.response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
@AllArgsConstructor
public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        String requestUrl = response.request().url();
        try (InputStream is = response.body().asInputStream()) {

            ErrorResponse errorResponse = objectMapper.readValue(is, ErrorResponse.class);
            HttpStatus responseStatus = HttpStatus.valueOf(response.status());
            if (responseStatus == HttpStatus.BAD_GATEWAY || responseStatus == HttpStatus.SERVICE_UNAVAILABLE) {
                return new RestRetryException();
            } else if (responseStatus.is5xxServerError()) {
                return new RestApiServerException(requestUrl, errorResponse);
            } else if (responseStatus.is4xxClientError()) {
                return new RestApiClientException(requestUrl, errorResponse);
            }
        } catch (Exception ex) {
            log.error("Exception happened during parsing errors from client.", ex);
        }
        return new RuntimeException("Unknown exception");
    }
}
