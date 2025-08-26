package com.yann.inventoryservice.application.dto;

import com.yann.inventoryservice.domain.vo.MaxQuantity;
import com.yann.inventoryservice.domain.vo.ProductID;

public record StockUpdateResponseDTO(
        ProductID productID,
        String productName,
        double price,
        MaxQuantity maxQuantity) {
}
