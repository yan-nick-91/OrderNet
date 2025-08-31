package com.yann.customerservice.domain;

import com.yann.customerservice.domain.exceptions.InsufficientPaymentException;

public class CartPaymentChecker {
    public void checkIfPaymentMathesCartTotalPrice(double paymentAmount, Cart cart) {
        if (paymentAmount != cart.getTotalPrice()) {
            throw new InsufficientPaymentException("Payment does not match the total price in cart");
        }
    }
}
