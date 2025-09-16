package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.exception.IllegaInitInventoryException;
import com.yann.inventoryservice.domain.exception.IllegalInventoryUpdateException;
import com.yann.inventoryservice.domain.exception.OutOfStockException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductAvailability {
    public String checkAvailability(Product product) {
        if (product.getAvailableQuantity() <= 0) {
            throw new OutOfStockException("Product is out of stock. Please refill");
        }

        double percentage = getAvailabilityPercentage(product);

        if (percentage <= 50) {
            return String.format("Product is low: %.2f%%.", percentage);
        }
        return String.format("Product in stock: %.2f%%", percentage);
    }

    private double getAvailabilityPercentage(Product product) {
        if (product.getMaxQuantity().value() < 50) {
            throw new IllegaInitInventoryException("The initialization of a product cannot be lower than 50");
        }
        return ((double) product.getAvailableQuantity() / product.getMaxQuantity().value()) * 100;
    }

    public void checkIfProductIsAlreadyInitialized(Product newProduct, List<Product> products) {
        boolean productNameExists = products.stream()
                                            .map(Product::getName)
                                            .anyMatch(existingName -> existingName.equals(newProduct.getName()));

        if (productNameExists) {
            throw new IllegalInventoryUpdateException(
                    "Cannot add product: '" + newProduct.getName() + "' is already in inventory."
            );
        }
    }
}
