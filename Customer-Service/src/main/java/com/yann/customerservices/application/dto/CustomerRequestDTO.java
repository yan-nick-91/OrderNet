package com.yann.customerservices.application.dto;

import com.yann.ordersservice.application.dto.AddressDTO;

public record CustomerRequestDTO(
        String firstname,
        String lastname,
        String email,
        AddressDTO address
) {
}
