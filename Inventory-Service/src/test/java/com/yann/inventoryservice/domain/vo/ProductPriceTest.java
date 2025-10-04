package com.yann.inventoryservice.domain.vo;

import com.yann.inventoryservice.domain.exception.IllegalInitInventoryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ProductPriceTest extends AbstractValueObjectTest<ProductPrice> {
    @Override
    protected ProductPrice createValidInstanceOne() {
        return new ProductPrice(200.00);
    }

    @Override
    protected ProductPrice createValidInstanceTwo() {
        return new ProductPrice(200.00);
    }

    @Override
    protected ProductPrice differentValidInstance() {
        return new ProductPrice(220.00);
    }

    @ParameterizedTest
    @DisplayName("Should Throw Exception When Instantiating Product Price Is Below Zero Or Below")
    @ValueSource(doubles = {0.0, -0.1, -1.0})
    void shouldThrowExceptionWhenInstantiatingProductPriceIsBelowZeroOrBelow(double invalidPriceValues) {
        Exception exception = assertThrows(IllegalInitInventoryException.class, () -> {
            new ProductPrice(invalidPriceValues);
        });
        assertEquals("Price cannot be less or equal to 0.0", exception.getMessage());
    }

    @Test
    @DisplayName("fromDouble Should Return Equivalent ProductPrice")
    void fromDoubleShouldReturnEquivalentProductPrice() {
        ProductPrice direct = new ProductPrice(150.00);
        ProductPrice fromFactory = ProductPrice.fromDouble(150.00);

        assertEquals(direct, fromFactory);
        assertEquals(direct.hashCode(), fromFactory.hashCode());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.50, 1.00, 100.0, 100.50, 1000_000.00})
    @DisplayName("Should Create A Valid Product Price")
    void shouldCreateMaxQuantityWhenValueIsWithinRange(Double valueForMaxQuantity) {
        ProductPrice productPrice = new ProductPrice(valueForMaxQuantity);
        assertEquals(valueForMaxQuantity, productPrice.value());
    }

    @Test
    void equalsShouldNotBeConsistentWithStringNumber() {
        ProductPrice productPrice = new ProductPrice(200.00);
        assertNotEquals(productPrice, "200.00");
    }
}