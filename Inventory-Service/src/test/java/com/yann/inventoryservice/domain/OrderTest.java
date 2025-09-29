package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.utils.CustomerIDFactory;
import com.yann.inventoryservice.domain.utils.IDFactory;
import com.yann.inventoryservice.domain.utils.OrderIDFactory;
import com.yann.inventoryservice.domain.utils.ProductIDFactory;
import com.yann.inventoryservice.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderTest {
    @Test
    @DisplayName("Empty Constructor For Order Should Initializes With Nulls")
    void emptyConstructorForOrderShouldInitializeWithNulls() {
        Order order = new Order();

        assertNull(order.getOrderID());
        assertNull(order.getProductID());
        assertEquals(0, order.getQuantity());
        assertNull(order.getCustomer());
        assertNull(order.getProductState());
    }

    @Test
    @DisplayName("Non Empty Constructor For Customer Should Not Return Nulls With Getters")
    void nonEmptyConstructorForCustomerShouldNotReturnNullsWithGetters() {
        IDFactory<OrderID> orderIDFactory = new OrderIDFactory();
        IDFactory<ProductID> productIDFactory = new ProductIDFactory();
        IDFactory<CustomerID> customerIDFactory = new CustomerIDFactory();

        OrderID orderID = orderIDFactory.set("ORD-ID-20250926-123e4567-e89b-12d3-a456-426614174222");
        ProductID productID = productIDFactory.set("PROD-ID-20250926-123e4567-e89b-12d3-a456-426614174333");
        CustomerID customerID = customerIDFactory.set("CUS-ID-20250926-123e4567-e89b-12d3-a456-426614174876");

        Address address = new Address("1234AB", "Test street",
                new StreetNumber("40"), "Test Town", "USA");

        Customer customer = new Customer(customerID, "John", " Doe",
                "john.doe@test.com", address);

        Order order = new Order(orderID, productID, 20, customer, ProductState.ORDER_RECEIVED);

        assertEquals(orderID, order.getOrderID());
        assertEquals(productID, order.getProductID());
        assertEquals(20, order.getQuantity());
        assertEquals(customer, order.getCustomer());
        assertEquals(ProductState.ORDER_RECEIVED, order.getProductState());
    }
}
