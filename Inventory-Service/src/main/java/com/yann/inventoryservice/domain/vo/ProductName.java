package com.yann.inventoryservice.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yann.inventoryservice.domain.exception.IllegaInitInventoryException;

import java.util.Objects;

public class ProductName {
    private final String value;

    public ProductName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegaInitInventoryException("Name cannot be null or empty");
        }
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static ProductName fromString(String value) {
        return new ProductName(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
