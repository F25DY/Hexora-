package com.hexora.network.service;

public class NetworkDiscoveryException extends RuntimeException {

    public NetworkDiscoveryException(String message) {
        super(message);
    }

    public NetworkDiscoveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
