package com.yann.customerservices.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;
import java.util.regex.Pattern;

public class StreetNumber {
    private final static Pattern STREETNUMBER_PATTERN = Pattern.compile("^[1-9][0-9]*[A-Za-z]?$");
    private String value;

    public StreetNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("StreetNumber value is null or empty");
        }

        if (!value.matches(STREETNUMBER_PATTERN.pattern())) {
            throw new IllegalArgumentException("StreetNumber value is invalid");
        }
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static StreetNumber fromString(String value) {
        return new StreetNumber(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetNumber that = (StreetNumber) o;
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
