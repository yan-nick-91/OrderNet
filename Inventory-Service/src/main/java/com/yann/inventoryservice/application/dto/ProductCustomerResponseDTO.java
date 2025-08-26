package com.yann.inventoryservice.application.dto;

import com.yann.inventoryservice.domain.vo.ProductID;

public record ProductCustomerResponseDTO(ProductID productID, String name, double price) {
}
