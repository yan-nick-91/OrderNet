package com.yann.customerservice.application.dto;

import com.yann.customerservice.domain.Address;
import com.yann.customerservice.domain.Cart;
import com.yann.customerservice.domain.vo.CustomerID;
import com.yann.customerservice.domain.vo.Email;

public record CustomerResponseDTO(
        CustomerID customerID,
        String firstName,
        String lastName,
        Email email,
        Address address,
        Cart cart
) {
}
