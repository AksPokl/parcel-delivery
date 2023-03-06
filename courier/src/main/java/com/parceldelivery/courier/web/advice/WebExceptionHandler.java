package com.parceldelivery.courier.web.advice;

import com.parceldelivery.courier.exception.RestApiClientException;
import com.parceldelivery.courier.exception.RestApiServerException;
import com.parceldelivery.model.response.ErrorResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidExceptionException(
            MethodArgumentNotValidException exception, WebRequest request) {
        List<Pair<String, String>> errorList = exception.getBindingResult().getFieldErrors().stream()
                .map(e -> Pair.of(e.getField(), e.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(ErrorResponse.builder()
                .validationErrorMessages(errorList)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestApiClientException.class)
    public ResponseEntity<ErrorResponse> handleRestApiClientException(
            RestApiClientException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestApiServerException.class)
    public ResponseEntity<ErrorResponse> handleRestApiServerException(
            RestApiClientException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({RuntimeException.class, IllegalStateException.class})
    public ResponseEntity<ErrorResponse> handleException(
            Exception exception, WebRequest request) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException exception, WebRequest request) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.FORBIDDEN);
    }
}
