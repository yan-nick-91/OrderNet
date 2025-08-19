package com.yann.ordersservice.application.dto;

import com.yann.ordersservice.domain.vo.ProductID;

public record ProductsResponseDTO(
        ProductID productID,
        String productName,
        double price,
        int quantity
) {
}
