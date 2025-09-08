package com.yann.ordersservice.application.dto;

public record CustomerDTO(
        String customerID,
        String firstname,
        String lastname,
        String email,
        AddressDTO address
) {
}
