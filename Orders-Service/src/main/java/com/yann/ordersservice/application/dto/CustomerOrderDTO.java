package com.yann.ordersservice.application.dto;

public record CustomerOrderDTO(
        String firstname,
        String lastname,
        String email,
        AddressDTO address,
        CartDTO cart
) {
}
