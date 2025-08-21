package com.yann.customerservices.domain.exceptions;

public class InventoryServiceException extends RuntimeException {
    public InventoryServiceException(String message) {
        super(message);
    }
}
