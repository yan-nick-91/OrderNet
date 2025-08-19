package com.yann.inventoryservice.domain.exception;

public class IllegalInventoryUpdateException extends RuntimeException {
    public IllegalInventoryUpdateException(String message) {
        super(message);
    }
}
