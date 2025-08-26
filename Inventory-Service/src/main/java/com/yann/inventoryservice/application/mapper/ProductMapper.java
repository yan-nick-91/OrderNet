package com.yann.inventoryservice.application.mapper;

import com.yann.inventoryservice.application.dto.*;
import com.yann.inventoryservice.domain.Product;
import com.yann.inventoryservice.domain.vo.MaxQuantity;
import com.yann.inventoryservice.domain.vo.ProductID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ProductMapper {
    private static final String dateFormat = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    public static Product toProduct(ProductRequestDTO productRequestDTO) {
        ProductID productID = new ProductID(String.format("PROD-ID-%s-%s", dateFormat, UUID.randomUUID()));
        return new Product(productID, productRequestDTO.name(), productRequestDTO.price(),
                productRequestDTO.quantity(), new MaxQuantity(productRequestDTO.maxQuantity()));
    }

    public static Product toUpdateProduct(Product product, StockUpdateRequestDTO stockUpdateRequestDTO) {
        product.setName(stockUpdateRequestDTO.name());
        product.setPrice(stockUpdateRequestDTO.price());
        return product;
    }

    public static ProductResponseDTO toProductRequestDTO(Product product) {
        return new ProductResponseDTO(product.getId(), product.getName(),
                product.getPrice(), product.getAvailableQuantity(), product.getMaxQuantity());
    }

    public static ProductCustomerResponseDTO toProductCustomerResponseDTO(Product product) {
        return new ProductCustomerResponseDTO(product.getId(), product.getName(), product.getPrice());
    }

    public static StockResponseDTO toStockResponseDTO(Product product) {
        return new StockResponseDTO(product.getId(), product.getName(),
                product.getAvailableQuantity(), product.checkAvailability());
    }

    public static StockUpdateResponseDTO toStockUpdateResponse(Product product) {
        return new StockUpdateResponseDTO(product.getId(), product.getName(),
                product.getPrice(), product.getMaxQuantity());
    }
}
