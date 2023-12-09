package com.samuel.validator.service.exceptions;

public class LimitUnavailable extends RuntimeException {
    public LimitUnavailable(String message) {
        super(message);
    }
}
