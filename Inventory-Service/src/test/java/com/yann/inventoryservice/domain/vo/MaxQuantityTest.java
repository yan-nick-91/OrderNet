package com.yann.inventoryservice.domain.vo;

import com.yann.inventoryservice.domain.exception.IllegalInventoryUpdateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MaxQuantityTest extends AbstractValueObjectTest<MaxQuantity> {
    @Override
    protected MaxQuantity createValidInstanceOne() {
        return new MaxQuantity(200);
    }

    @Override
    protected MaxQuantity createValidInstanceTwo() {
        return new MaxQuantity(200);
    }

    @Override
    protected MaxQuantity differentValidInstance() {
        return new MaxQuantity(201);
    }

    @Test
    @DisplayName("Should Throw Exception When Instantiating Product With Max Quantity Below 50")
    public void shouldThrowExceptionWhenInstantiatingProductWithMaxQuantityBelow50() {
        Integer valueForMaxQuantity = 49;
        Exception exception = assertThrows(IllegalInventoryUpdateException.class,
                () -> new MaxQuantity(valueForMaxQuantity));
        assertEquals("Max quantity must be equal or greater than 50", exception.getMessage());
    }

    @Test
    @DisplayName("Should Throw Exception When Instantiating Product With MaxQuantity Above 300")
    public void shouldThrowExceptionWhenInstantiatingProductWithMaxQuantityAbove300() {
        Integer valueForMaxQuantity = 301;
        Exception exception = assertThrows(IllegalInventoryUpdateException.class,
                () -> new MaxQuantity(valueForMaxQuantity));
        assertEquals("Max quantity must be less than or equal to 300", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {51, 50, 299, 300})
    @DisplayName("Should Create Max Quantity When Value Is Within Range")
    void shouldCreateMaxQuantityWhenValueIsWithinRange(Integer valueForMaxQuantity) {
        MaxQuantity maxQuantity = new MaxQuantity(valueForMaxQuantity);
        assertEquals(valueForMaxQuantity, maxQuantity.value());
    }

    @Test
    void equalsShouldNotBeConsistentWithStringNumber() {
        MaxQuantity maxQuantity = new MaxQuantity(200);
        assertNotEquals(maxQuantity, "200");
    }

    @Test
    @DisplayName("fromInteger Should Return Equivalent MaxQuantity")
    void fromIntegerShouldReturnEquivalentMaxQuantity() {
        MaxQuantity direct = new MaxQuantity(150);
        MaxQuantity fromFactory = MaxQuantity.fromInteger(150);

        assertEquals(direct, fromFactory);
        assertEquals(direct.hashCode(), fromFactory.hashCode());
    }
}