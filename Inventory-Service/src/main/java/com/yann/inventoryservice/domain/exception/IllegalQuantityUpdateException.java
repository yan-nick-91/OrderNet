package com.yann.inventoryservice.domain.exception;

public class IllegalQuantityUpdateException extends RuntimeException {
    public IllegalQuantityUpdateException(String message) {
        super(message);
    }
}
