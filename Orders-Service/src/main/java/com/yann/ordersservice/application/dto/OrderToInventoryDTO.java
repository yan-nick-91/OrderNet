package com.yann.ordersservice.application.dto;

import java.util.List;

public record OrderToInventoryDTO(
        String OrderID,
        List<ProductOrderDTO> productOrderDTO
) {
}
