package com.yann.customerservice.infrastructure.cache;

import com.yann.customerservice.application.dto.ProductCustomerResponseDTO;

import java.util.List;

public interface ProductCaching {
    void saveProductsInCache(List<ProductCustomerResponseDTO> products);
    List<ProductCustomerResponseDTO> getCachedProducts();
    ProductCustomerResponseDTO getCachedProductByName(String productName);
}
