package com.yann.customerservice.domain.exceptions;

public class ProductUnavailableException extends RuntimeException {
    public ProductUnavailableException(String message) {
        super(message);
    }
}
