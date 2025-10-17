package com.yann.inventoryservice.domain.exception;

public class IllegalInitInventoryException extends RuntimeException {
    public IllegalInitInventoryException(String message) {
        super(message);
    }
}
