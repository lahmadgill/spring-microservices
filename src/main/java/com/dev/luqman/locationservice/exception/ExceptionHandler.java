package com.dev.luqman.locationservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.dev.luqman.locationservice.model.ErrorResponse;

@ControllerAdvice
public class ExceptionHandler {
    
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(final Exception ex) {
        LOG.error("Fatal error with exception:{}", ex.getMessage());
        return new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }

    @ResponseStatus
    @org.springframework.web.bind.annotation.ExceptionHandler(LocationApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(final LocationApiException ex) {
        LOG.error("Location service error, ex:{}", ex.getMessage());
        final ErrorResponse err = new ErrorResponse(ex.getMessage(), String.valueOf(ex.getStatusCode()));
        return new ResponseEntity<>(err, HttpStatus.valueOf(ex.getStatusCode()));
    }
}
