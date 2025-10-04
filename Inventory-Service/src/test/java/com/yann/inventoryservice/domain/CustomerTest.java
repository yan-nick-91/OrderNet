package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.utils.CustomerIDFactory;
import com.yann.inventoryservice.domain.utils.IDFactory;
import com.yann.inventoryservice.domain.vo.CustomerID;
import com.yann.inventoryservice.domain.vo.StreetNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    @DisplayName("Empty Constructor For Customer Should Initializes With Nulls")
    void EmptyConstructorForCustomerShouldInitializesWithNulls() {
        Customer customer = new Customer();

        assertNull(customer.getCustomerID());
        assertNull(customer.getFirstname());
        assertNull(customer.getLastname());
        assertNull(customer.getAddress());
        assertNull(customer.getEmail());
    }

    @Test
    @DisplayName("Non Empty Constructor For Customer Should Not Return Nulls With Getters")
    void nonEmptyConstructorForCustomerShouldNotReturnNullsWithGetters() {
        IDFactory<CustomerID> customerIDFactory = new CustomerIDFactory();
        CustomerID customerID =  customerIDFactory.set("CUS-ID-20250926-123e4567-e89b-12d3-a456-426614174876");
        Address address = new Address("1234AB", "Test street",
                new StreetNumber("40"), "Test Town", "USA");
        Customer customer = new Customer(customerID, "John", "Doe",
                "john.doe@test.com", address);

        assertEquals(customerID, customer.getCustomerID());
        assertEquals("John", customer.getFirstname());
        assertEquals("Doe", customer.getLastname());
        assertEquals("john.doe@test.com", customer.getEmail());
        assertEquals(address, customer.getAddress());
    }
}