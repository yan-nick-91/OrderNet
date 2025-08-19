package com.yann.ordersservice.application.dto;

import com.yann.ordersservice.domain.vo.CustomerID;
import com.yann.ordersservice.domain.vo.OrderID;

import java.util.List;


public record CustomerResponseDTO(
        CustomerID customerID,
        String firstname,
        String lastname,
        AddressDTO address,
        List<OrderID> orderIds
) {
}
