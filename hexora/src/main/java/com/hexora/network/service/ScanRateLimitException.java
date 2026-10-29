package com.hexora.network.service;

public class ScanRateLimitException extends RuntimeException {

    public ScanRateLimitException(String message) {
        super(message);
    }
}
