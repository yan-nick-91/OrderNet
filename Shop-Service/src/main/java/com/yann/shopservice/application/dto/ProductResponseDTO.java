package com.yann.shopservice.application.dto;

import com.yann.shopservice.domain.vo.ProductID;

public record ProductResponseDTO(
        ProductID productID,
        String productName,
        double price
) {
}
