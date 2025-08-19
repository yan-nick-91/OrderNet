package com.yann.ordersservice.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Sequence {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private final int value;

    // Auto-increment
    public Sequence() {
        this.value = counter.incrementAndGet();
    }

    public Sequence(int valueFromDB) {
        this.value = valueFromDB;
        counter.updateAndGet(current -> Math.max(current, valueFromDB));
    }

    @JsonValue
    public int value() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sequence sequence = (Sequence) o;
        return value == sequence.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }
}
