package com.yann.shopservice.domain.exceptions;

public class ProductAlreadyInitializedInCartException extends RuntimeException {
    public ProductAlreadyInitializedInCartException(String message) {
        super(message);
    }
}
