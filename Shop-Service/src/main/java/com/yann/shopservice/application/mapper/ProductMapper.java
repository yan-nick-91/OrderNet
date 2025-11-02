package com.yann.shopservice.application.mapper;

import com.yann.shopservice.application.dto.ProductCustomerResponseDTO;
import com.yann.shopservice.domain.Product;

public class ProductMapper {
    public static Product toProduct(ProductCustomerResponseDTO productCustomerResponseDTO) {
        return new Product(productCustomerResponseDTO.productID(),
                productCustomerResponseDTO.name(),
                productCustomerResponseDTO.price());
    }

    public static ProductCustomerResponseDTO toProductCustomerResponseDTO(Product product) {
        return new ProductCustomerResponseDTO(
                product.getProductID(),
                product.getProductName(),
                product.getPrice());
    }
}
