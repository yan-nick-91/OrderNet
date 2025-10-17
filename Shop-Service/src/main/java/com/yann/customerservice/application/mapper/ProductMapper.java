package com.yann.customerservice.application.mapper;

import com.yann.customerservice.application.dto.ProductCustomerResponseDTO;
import com.yann.customerservice.domain.Product;

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
