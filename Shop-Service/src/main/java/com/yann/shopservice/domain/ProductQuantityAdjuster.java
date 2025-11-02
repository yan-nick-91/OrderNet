package com.yann.shopservice.domain;

import com.yann.shopservice.domain.exceptions.IllegalProductQuantityException;

class ProductQuantityAdjuster {
    public int increaseQuantity(int currentQuantity, int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalProductQuantityException("Quantity must be greater than 0");
        }
        return currentQuantity + newQuantity;
    }

    public int decreaseQuantity(int currentQuantity, int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalProductQuantityException("Quantity must be greater than 0");
        }

        int updated = currentQuantity - newQuantity;
        if (updated < 0) {
            throw new IllegalProductQuantityException("Stock quantity cannot be below 0");
        }
        return updated;
    }
}
