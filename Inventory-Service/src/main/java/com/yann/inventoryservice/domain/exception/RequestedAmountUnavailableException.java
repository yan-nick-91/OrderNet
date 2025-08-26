package com.yann.inventoryservice.domain.exception;

public class RequestedAmountUnavailableException extends RuntimeException {
    public RequestedAmountUnavailableException(String message) {
        super(message);
    }
}
