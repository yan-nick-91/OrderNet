package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.exception.IllegalInitInventoryException;
import com.yann.inventoryservice.domain.utils.CreateIDFactory;
import com.yann.inventoryservice.domain.utils.ProductIDFactory;
import com.yann.inventoryservice.domain.vo.MaxQuantity;
import com.yann.inventoryservice.domain.vo.ProductID;
import com.yann.inventoryservice.domain.vo.ProductName;
import com.yann.inventoryservice.domain.vo.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}