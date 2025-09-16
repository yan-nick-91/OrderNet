package com.yann.inventoryservice.application.mapper;

import com.yann.inventoryservice.application.dto.*;
import com.yann.inventoryservice.domain.*;
import com.yann.inventoryservice.domain.vo.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ProductMapper {
    private static final String dateFormat = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    public static Product toProduct(ProductRequestDTO productRequestDTO) {
        ProductID productID = new ProductID(String.format("PROD-ID-%s-%s", dateFormat, UUID.randomUUID()));
        ProductName productName = toProductName(productRequestDTO.name());
        ProductPrice productPrice = toProductPrice(productRequestDTO.price());
        return new Product(productID, productName, productPrice,
                productRequestDTO.quantity(), new MaxQuantity(productRequestDTO.maxQuantity()));
    }

    public static Product toUpdateProduct(Product product, StockUpdateRequestDTO stockUpdateRequestDTO) {
        ProductName productName = toProductName(stockUpdateRequestDTO.name());
        ProductPrice productPrice = toProductPrice(stockUpdateRequestDTO.price());
        product.setName(productName);
        product.setPrice(productPrice);
        return product;
    }

    public static ProductResponseDTO toProductRequestDTO(Product product) {
        return new ProductResponseDTO(product.getProductID(), product.getName().value(),
                product.getPrice().value(), product.getAvailableQuantity(), product.getMaxQuantity());
    }

    public static ProductCustomerResponseDTO toProductCustomerResponseDTO(Product product) {
        return new ProductCustomerResponseDTO(product.getProductID(),
                product.getName().value(), product.getPrice().value());
    }

    public static StockResponseDTO toStockResponseDTO(Product product) {
        ProductAvailability productAvailability = new ProductAvailability();

        return new StockResponseDTO(product.getProductID(), product.getName().value(),
                product.getAvailableQuantity(), productAvailability.checkAvailability(product));
    }

    public static StockUpdateResponseDTO toStockUpdateResponse(Product product) {
        return new StockUpdateResponseDTO(product.getProductID(), product.getName().value(),
                product.getPrice().value(), product.getMaxQuantity());
    }

    // Helpers
    private static ProductName toProductName(String productNameAsString) {
        return new ProductName(productNameAsString);
    }

    private static ProductPrice toProductPrice(Double productPriceAsDouble) {
        return new ProductPrice(productPriceAsDouble);
    }
}
