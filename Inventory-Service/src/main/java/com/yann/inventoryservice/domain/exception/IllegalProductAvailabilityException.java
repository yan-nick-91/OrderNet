package com.yann.inventoryservice.domain.exception;

public class IllegalProductAvailabilityException extends RuntimeException {
    public IllegalProductAvailabilityException(String message) {
        super(message);
    }
}
