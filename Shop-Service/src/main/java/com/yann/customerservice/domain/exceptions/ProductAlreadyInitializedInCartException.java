package com.yann.customerservice.domain.exceptions;

public class ProductAlreadyInitializedInCartException extends RuntimeException {
    public ProductAlreadyInitializedInCartException(String message) {
        super(message);
    }
}
