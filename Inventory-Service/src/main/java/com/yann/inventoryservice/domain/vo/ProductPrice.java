package com.yann.inventoryservice.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import com.yann.inventoryservice.domain.exception.IllegaInitInventoryException;

import java.util.Objects;

public class ProductPrice {
    private Double value;

    public ProductPrice(Double value) {
        if (value <= 0.0) {
            throw new IllegaInitInventoryException("Price cannot be less than or equal to 0.0");
        }
        this.value = value;
    }

    @JsonValue
    public Double value() {
        return value;
    }

    @JsonValue
    public static ProductPrice fromDouble(Double value) {
        return new ProductPrice(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
