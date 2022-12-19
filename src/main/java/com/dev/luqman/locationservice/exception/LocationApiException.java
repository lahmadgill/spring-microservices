package com.dev.luqman.locationservice.exception;

import lombok.Getter;

@Getter
public class LocationApiException extends RuntimeException {
    private int statusCode;
    public LocationApiException(final String msg, int statusCode) {
        this(msg);
        this.statusCode = statusCode;
    }
    public LocationApiException(final String msg) {
        super(msg);
    }
}
