package com.yann.ordersservice.application.dto;

public record CustomerDetailDTO(
        String customerID,
        String firstname,
        String lastname,
        String email,
        AddressDTO address,
        CartDTO cart
) {
}
