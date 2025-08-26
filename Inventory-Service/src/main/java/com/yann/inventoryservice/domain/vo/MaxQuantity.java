package com.yann.inventoryservice.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yann.inventoryservice.domain.exception.IllegalInventoryUpdateException;

import java.util.Objects;

public class MaxQuantity {
    private final Integer value;

    public MaxQuantity (Integer value) {
        if (value < 50) {
            throw new IllegalInventoryUpdateException("Max quantity must be equal or greater than 50");
        }

        if (value > 300) {
            throw new IllegalInventoryUpdateException("Max quantity must be less than or equal to 300");
        }

        this.value = value;
    }

    @JsonValue
    public Integer value() {
        return value;
    }

    @JsonCreator
    public static MaxQuantity fromInteger(Integer value) {
        return new MaxQuantity(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaxQuantity that = (MaxQuantity) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
