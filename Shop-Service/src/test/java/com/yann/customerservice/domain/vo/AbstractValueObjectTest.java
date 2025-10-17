package com.yann.customerservice.domain.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractValueObjectTest<T> {
    protected abstract T createValidInstanceOne();
    protected abstract T createValidInstanceTwo();
    protected abstract T differentValidInstance();

    @Test
    void equalsShouldBeReflexive() {
        T instance = createValidInstanceOne();
        assertEquals(instance, instance);
    }

    @Test
    void equalsShouldBeSymmetric() {
        T instance1 = createValidInstanceOne();
        T instance2 = createValidInstanceTwo();

        assertEquals(instance1, instance2);
        assertEquals(instance2, instance1);
    }

    @Test
    void equalsShouldBeDifferentForDifferentValue() {
        T instance1 = createValidInstanceOne();
        T instance2 = differentValidInstance();

        assertNotEquals(instance1, instance2);
    }

    @Test
    void equalsShouldNotBeConsistentWithNull() {
        T instance1 = createValidInstanceOne();
        assertNotEquals(instance1, null);
    }

    @Test
    void hashCodeShouldBeConsistentWithEquals() {
        T instance1 = createValidInstanceOne();
        T instance2 = createValidInstanceTwo();

        assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    void toStringShouldReturnValue() {
        T instance = createValidInstanceOne();
        assertNotNull(instance.toString());
    }
}
