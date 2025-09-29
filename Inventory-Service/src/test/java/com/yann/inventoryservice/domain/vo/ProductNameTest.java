package com.yann.inventoryservice.domain.vo;

import com.yann.inventoryservice.domain.exception.IllegalInitInventoryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductNameTest extends AbstractValueObjectTest<ProductName> {
    @Override
    protected ProductName createValidInstanceOne() {
        return new ProductName("Samsung Galaxy");
    }

    @Override
    protected ProductName createValidInstanceTwo() {
        return new ProductName("Samsung Galaxy");
    }

    @Override
    protected ProductName differentValidInstance() {
        return new ProductName("Iphone");
    }

    @ParameterizedTest
    @DisplayName("Constructor With Null Or Blank String Value Should Throw Exception")
    @MethodSource("provideInvalidValuesForConstructor")
    void constructorWithNullOrBlankStringValueShouldThrowException(String invalidValue) {
        Exception exception = assertThrows(IllegalInitInventoryException.class,
                () -> new ProductName(invalidValue));

        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    static Stream<Arguments> provideInvalidValuesForConstructor() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of("")
        );
    }

    @Test
    @DisplayName("Should Return Same Value After Initializing Product Name")
    void shouldCreateMaxQuantityWhenValueIsWithinRange() {
        ProductName productName = new ProductName("Samsung Galaxy");
        assertEquals("Samsung Galaxy", productName.value());
    }

    @Test
    @DisplayName("fromString Should Return Equivalent ProductName")
    void fromIntegerShouldReturnEquivalentMaxQuantity() {
        ProductName direct = new ProductName("Samsung Galaxy");
        ProductName fromFactory = ProductName.fromString("Samsung Galaxy");

        assertEquals(direct, fromFactory);
        assertEquals(direct.hashCode(), fromFactory.hashCode());
    }

    @Test
    void lowercaseStringShouldNotBeConsistentWithUppercase() {
        ProductName productName = new ProductName("John");
        assertNotEquals("john", productName);
    }
}