package com.yann.customerservice.infrastructure.cache;

import com.yann.customerservice.application.dto.ProductCustomerResponseDTO;

import java.util.List;

public interface ProductCaching {
    List<ProductCustomerResponseDTO> getCachedProducts();
    ProductCustomerResponseDTO getCachedProductByName(String productName);
}
