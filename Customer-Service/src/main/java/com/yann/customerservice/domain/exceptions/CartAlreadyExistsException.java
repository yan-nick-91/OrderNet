package com.yann.customerservice.domain.exceptions;

public class CartAlreadyExistsException extends RuntimeException {
    public CartAlreadyExistsException(String message) {
        super(message);
    }
}
