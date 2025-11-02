package com.yann.shopservice.domain.exceptions;

public class InventoryServiceException extends RuntimeException {
    public InventoryServiceException(String message) {
        super(message);
    }
}
