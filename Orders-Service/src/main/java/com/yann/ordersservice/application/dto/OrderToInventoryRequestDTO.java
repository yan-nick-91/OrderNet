package com.yann.ordersservice.application.dto;

import java.util.List;

public record OrderToInventoryRequestDTO(
        String OrderID,
        CustomerDTO customerDTO,
        List<ProductOrderDTO> productOrderDTO
) {
}
