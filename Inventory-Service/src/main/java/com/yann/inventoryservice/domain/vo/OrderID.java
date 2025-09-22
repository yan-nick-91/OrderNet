package com.yann.inventoryservice.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public class OrderID {
    private static final String PREFIX = "ORD-ID";
    private static final String DATE_FORMAT = "\\d{8}";
    private final String value;

    public OrderID(String value) {
        if (value == null || value.isBlank()) {
            throw new NullPointerException("value is null or empty");
        }

        String formatted = String.format("%s-%s", PREFIX, DATE_FORMAT);

        if (!value.matches(formatted + "-[0-9a-fA-F\\-]{36}")) {
            throw new IllegalArgumentException("Invalid OrderID format: " + value);
        }

        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static OrderID fromString(String value) {
        return new OrderID(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderID orderID = (OrderID) o;
        return Objects.equals(value, orderID.value);
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
