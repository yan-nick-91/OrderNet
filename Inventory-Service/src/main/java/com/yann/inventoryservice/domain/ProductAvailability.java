package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.exception.IllegalInitInventoryException;
import com.yann.inventoryservice.domain.exception.IllegalInventoryUpdateException;
import com.yann.inventoryservice.domain.exception.IllegalProductAvailabilityException;
import com.yann.inventoryservice.domain.exception.OutOfStockException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductAvailability {
    public String displayAvailability(Product product) {
        validateProductStock(product);
        double percentage = calculateAvailabilityPercentage(product);

        if (percentage <= 50.0) {
            return String.format("Product is low: %.2f%%.", percentage);
        }
        return String.format("Product in stock: %.2f%%.", percentage);
    }

    public void verifyIfProductIsInStock(Product newProduct, List<Product> products) {
        boolean productNameExists = products.stream()
                                            .map(Product::getName)
                                            .anyMatch(newProduct.getName()::equals);

        if (productNameExists) {
            throw new IllegalInventoryUpdateException(
                    "Cannot add product: '" + newProduct.getName() + "' is already in inventory."
            );
        }
    }

    // Helpers
    private void validateProductStock(Product product) {
        int availableQuantity = product.getAvailableQuantity();
        int maxQuantity = product.getMaxQuantity().value();

        if (maxQuantity < 50) {
            throw new IllegalInitInventoryException("The initialization of a product cannot be lower than 50");
        }

        if (availableQuantity < 0) {
            throw new IllegalProductAvailabilityException("The available quantity cannot be less than 0");
        }

        if (availableQuantity == 0) {
            throw new OutOfStockException("The available quantity cannot be zero");
        }
    }

    private double calculateAvailabilityPercentage(Product product) {
        return ((double) product.getAvailableQuantity() / product.getMaxQuantity().value()) * 100;
    }
}
