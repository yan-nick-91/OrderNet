package com.yann.shopservice.infrastructure.cache;

import com.yann.shopservice.application.dto.ProductCustomerResponseDTO;

import java.util.List;

public interface ProductCaching {
    List<ProductCustomerResponseDTO> getCachedProducts();
    ProductCustomerResponseDTO getCachedProductByName(String productName);
}
