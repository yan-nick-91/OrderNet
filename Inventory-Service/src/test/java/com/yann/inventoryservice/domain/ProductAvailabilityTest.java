package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.exception.IllegalInitInventoryException;
import com.yann.inventoryservice.domain.exception.IllegalInventoryUpdateException;
import com.yann.inventoryservice.domain.exception.IllegalProductAvailabilityException;
import com.yann.inventoryservice.domain.exception.OutOfStockException;
import com.yann.inventoryservice.domain.vo.MaxQuantity;
import com.yann.inventoryservice.domain.vo.ProductName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductAvailabilityTest {
    @Mock
    private Product mockProduct;
    @Mock
    private MaxQuantity mockMaxQuantity;

    private ProductAvailability productAvailability;

    @BeforeEach
    void setUp() {
        this.productAvailability = new ProductAvailability();
    }

    @Test
    @DisplayName("Should Display Stock In Percentage")
    void shouldDisplayStockInPercentage() {
        when(mockProduct.getMaxQuantity()).thenReturn(mockMaxQuantity);
        when(mockProduct.getAvailableQuantity()).thenReturn(40);
        when(mockMaxQuantity.value()).thenReturn(50);

        String result = productAvailability.displayAvailability(mockProduct);
        assertEquals("Product in stock: 80.00%.", result);
    }

    @Test
    @DisplayName("Should Return Low Stock Message With Percentage")
    void shouldReturnLowStockMessageWithPercentage() {
        when(mockProduct.getMaxQuantity()).thenReturn(mockMaxQuantity);
        when(mockProduct.getAvailableQuantity()).thenReturn(25);
        when(mockMaxQuantity.value()).thenReturn(50);

        String result = productAvailability.displayAvailability(mockProduct);
        assertEquals("Product is low: 50.00%.", result);
    }

    @Test
    @DisplayName("Should Throw Exception When Stock Equals Zero")
    void shouldThrowExceptionWhenStockEqualsZero() {
        when(mockProduct.getMaxQuantity()).thenReturn(mockMaxQuantity);
        when(mockProduct.getAvailableQuantity()).thenReturn(0);
        when(mockMaxQuantity.value()).thenReturn(50);

        Exception exception = assertThrows(OutOfStockException.class,
                () -> productAvailability.displayAvailability(mockProduct));

        assertEquals("The available quantity cannot be zero", exception.getMessage());
    }

    @Test
    @DisplayName("Should Throw Exception When Available Quantity Is Below Zero")
    void shouldThrowExceptionWhenAvailableQuantityIsBelowZero() {
        when(mockProduct.getMaxQuantity()).thenReturn(mockMaxQuantity);
        when(mockProduct.getAvailableQuantity()).thenReturn(-1);
        when(mockMaxQuantity.value()).thenReturn(50);

        Exception exception = assertThrows(IllegalProductAvailabilityException.class,
                () -> productAvailability.displayAvailability(mockProduct)
        );

        assertEquals("The available quantity cannot be less than 0", exception.getMessage());
    }

    @Test
    @DisplayName("Should Throw Exception When Product Already Exists")
    void shouldThrowExceptionWhenProductAlreadyExists() {
        Product existingProduct = mock(Product.class);
        when(existingProduct.getName()).thenReturn(new ProductName("Laptop"));

        Product newProduct = mock(Product.class);
        when(newProduct.getName()).thenReturn(new ProductName("Laptop"));

        List<Product> products = List.of(existingProduct);

        Exception exception = assertThrows(IllegalInventoryUpdateException.class,
                () -> productAvailability.verifyIfProductIsInStock(newProduct, products));

        assertEquals("Cannot add product: 'Laptop' is already in inventory.", exception.getMessage());
    }

    @Test
    @DisplayName("Should Throw No Exception When Product Does Not Exist")
    void shouldThrowNoExceptionWhenProductDoesNotExist() {
        Product existingProduct = mock(Product.class);
        when(existingProduct.getName()).thenReturn(new ProductName("Laptop"));

        Product newProduct = mock(Product.class);
        when(newProduct.getName()).thenReturn(new ProductName("Mobile"));

        List<Product> products = List.of(existingProduct);

        assertDoesNotThrow(() -> productAvailability.verifyIfProductIsInStock(newProduct, products));
    }
}