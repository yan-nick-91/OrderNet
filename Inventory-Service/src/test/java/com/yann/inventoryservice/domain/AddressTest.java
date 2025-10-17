package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.vo.StreetNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AddressTest {
    @Test
    @DisplayName("Empty Constructor For Address Should Initializes With Nulls")
    void EmptyConstructorForAddressShouldInitializesWithNulls() {
        Address address = new Address();

        assertNull(address.getZipcode());
        assertNull(address.getStreetName());
        assertNull(address.getStreetNumber());
        assertNull(address.getCity());
        assertNull(address.getCountry());
    }

    @Test
    @DisplayName("Non Empty Constructor For Address Should Not Return Nulls With Getters")
    void nonEmptyConstructorForAddressShouldNotReturnNullsWithGetters() {
        Address address = new Address("1234AB", "Test street",
                new StreetNumber("40"), "Test Town", "USA");

        assertEquals("1234AB", address.getZipcode());
        assertEquals("Test street", address.getStreetName());
        assertEquals("Test Town", address.getCity());
        assertEquals("40", address.getStreetNumber().value());
        assertEquals("USA", address.getCountry());
    }
}