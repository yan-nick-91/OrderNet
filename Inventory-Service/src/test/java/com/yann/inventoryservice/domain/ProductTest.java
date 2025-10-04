package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.exception.IllegalInitInventoryException;
import com.yann.inventoryservice.domain.exception.IllegalInventoryUpdateException;
import com.yann.inventoryservice.domain.exception.IllegalQuantityUpdateException;
import com.yann.inventoryservice.domain.utils.CreateIDFactory;
import com.yann.inventoryservice.domain.utils.ProductIDFactory;
import com.yann.inventoryservice.domain.vo.MaxQuantity;
import com.yann.inventoryservice.domain.vo.ProductID;
import com.yann.inventoryservice.domain.vo.ProductName;
import com.yann.inventoryservice.domain.vo.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private CreateIDFactory<ProductID> productIDFactory;

    @BeforeEach
    void setUp() {
        this.productIDFactory = new ProductIDFactory();
    }

    @Test
    @DisplayName("Empty Constructor For Product Should Initializes With Nulls")
    void EmptyConstructorForCustomerShouldInitializesWithNulls() {
        Product product = new Product();

        assertNull(product.getProductID());
        assertNull(product.getName());
        assertNull(product.getPrice());
        assertNull(product.getPrice());
        assertNull(product.getMaxQuantity());
    }


    @Test
    void shouldInstantiateProductWithValidInitialQuantity() {
        ProductID id = productIDFactory.create();
        ProductName name = new ProductName("Test Product");
        ProductPrice price = new ProductPrice(10.0);
        MaxQuantity maxQuantity = new MaxQuantity(100);

        Product product = new Product(id, name, price, 50, maxQuantity);

        assertEquals(id, product.getProductID());
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(50, product.getAvailableQuantity());
        assertEquals(maxQuantity, product.getMaxQuantity());
    }

    @Test
    void shouldThrowExceptionWhenInstantiatingWithInitialQuantityExceedingMax() {
        ProductID id = productIDFactory.create();
        ProductName name = new ProductName("Invalid Product");
        ProductPrice price = new ProductPrice(5.0);
        MaxQuantity maxQuantity = new MaxQuantity(50);

        Exception exception = assertThrows(IllegalInitInventoryException.class, () ->
                new Product(id, name, price, 60, maxQuantity)
        );

        assertEquals("Initial quantity cannot be greater than max quantity", exception.getMessage());
    }

    @Test
    @DisplayName("Verify The Expected Result After Increasing Quantity For Product")
    void verifyTheExpectedResultAfterIncreasingQuantityForProduct() {
        ProductID id = productIDFactory.create();
        ProductName name = new ProductName("Test Product");
        ProductPrice price = new ProductPrice(10.0);
        MaxQuantity maxQuantity = new MaxQuantity(100);

        Product product = new Product(id, name, price, 50, maxQuantity);
        product.increaseQuantity(40);

        assertEquals(90, product.getAvailableQuantity());
    }

    @Test
    @DisplayName("Verify Exception When Increasing Quantity For Product Gets Over Max Quantity")
    void verifyExceptionWhenIncreasingQuantityForProductGetsMaxQuantity() {
        ProductID id = productIDFactory.create();
        ProductName name = new ProductName("Test Product");
        ProductPrice price = new ProductPrice(10.0);
        MaxQuantity maxQuantity = new MaxQuantity(200);

        Product product = new Product(id, name, price, 199, maxQuantity);
        Exception exception = assertThrows(
                IllegalQuantityUpdateException.class, () -> product.increaseQuantity(2));

        assertEquals("Quantity to increase exceeds maximum quantity", exception.getMessage());
    }

    @Test
    @DisplayName("Verify Exception When Increasing Quantity For Product Has A Value Of Zero")
    void verifyExceptionWhenIncreasingQuantityForProductHasAValueOfZero() {
        ProductID id = productIDFactory.create();
        ProductName name = new ProductName("Test Product");
        ProductPrice price = new ProductPrice(10.0);
        MaxQuantity maxQuantity = new MaxQuantity(200);

        Product product = new Product(id, name, price, 199, maxQuantity);
        Exception exception = assertThrows(
                IllegalQuantityUpdateException.class, () -> product.increaseQuantity(0));

        assertEquals("Quantity to increase must be greater than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Verify The Expected Result After Decreasing Quantity For Product")
    void verifyTheExpectedResultAfterDecreasingQuantityForProduct() {
        ProductID id = productIDFactory.create();
        ProductName name = new ProductName("Test Product");
        ProductPrice price = new ProductPrice(10.0);
        MaxQuantity maxQuantity = new MaxQuantity(200);

        Product product = new Product(id, name, price, 100, maxQuantity);
        product.decreaseQuantity(40);

        assertEquals(60, product.getAvailableQuantity());
    }

    @Test
    @DisplayName("Verify Exception When Increasing Quantity For Product Has A Value Of Zero")
    void verifyExceptionWhenDecreasingQuantityForProductHasAValueOfZero() {
        ProductID id = productIDFactory.create();
        ProductName name = new ProductName("Test Product");
        ProductPrice price = new ProductPrice(10.0);
        MaxQuantity maxQuantity = new MaxQuantity(200);

        Product product = new Product(id, name, price, 100, maxQuantity);
        Exception exception = assertThrows(
                IllegalQuantityUpdateException.class, () -> product.decreaseQuantity(0));

        assertEquals("Quantity to decrease must be greater than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Verify Updated MaxQuantity Results Equal In Getter")
    void verifyUpdatedProductResultsEqualInGetter() {
        ProductID id = productIDFactory.create();
        ProductName name = new ProductName("Test Product");
        ProductPrice price = new ProductPrice(10.0);
        MaxQuantity maxQuantity = new MaxQuantity(200);

        Product product = new Product(id, name, price, 100, maxQuantity);
        product.updateMaxQuantity(250);
        assertEquals(250, product.getMaxQuantity().value());
    }

    @Test
    @DisplayName("Verify MaxQuantity Cannot Be Above 300 After Update")
    void verifyMaxQuantityCannotBeAbove300AfterUpdate() {
        ProductID id = productIDFactory.create();
        ProductName name = new ProductName("Test Product");
        ProductPrice price = new ProductPrice(10.0);
        MaxQuantity maxQuantity = new MaxQuantity(200);

        Product product = new Product(id, name, price, 100, maxQuantity);
        Exception exception = assertThrows(IllegalInventoryUpdateException.class,
                () -> product.updateMaxQuantity(301));

        assertEquals("Max quantity must be less than or equal to 300", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Verify Threshold Of MaxQuantity After Update")
    @ValueSource(ints = {299, 300})
    void verifyThresholdOfMaxQuantityAfterUpdate(int maxQuantityToUpdate) {
        ProductID id = productIDFactory.create();
        ProductName name = new ProductName("Test Product");
        ProductPrice price = new ProductPrice(10.0);
        MaxQuantity maxQuantity = new MaxQuantity(200);

        Product product = new Product(id, name, price, 100, maxQuantity);
        product.updateMaxQuantity(maxQuantityToUpdate);

        assertEquals(maxQuantityToUpdate, product.getMaxQuantity().value());
    }
}