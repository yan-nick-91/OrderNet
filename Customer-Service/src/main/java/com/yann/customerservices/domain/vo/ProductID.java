package com.yann.customerservices.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public class ProductID {
    private static final String PREFIX = "PROD-ID";
    private static final String DATE_FORMAT = "\\d{8}";
    private String value;

    public ProductID(String value) {
        if (value == null || value.isBlank()) {
            throw new NullPointerException("value is null or empty");
        }

        String formatted = String.format("%s-%s", PREFIX, DATE_FORMAT);

        if (!value.matches(formatted + "-[0-9a-fA-F\\-]{36}")) {
            throw new IllegalArgumentException("Invalid ProductID format: " + value);
        }

        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static ProductID fromString(String value) {
        return new ProductID(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductID productID = (ProductID) o;
        return Objects.equals(value, productID.value);
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

