package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.exception.*;
import com.yann.inventoryservice.domain.vo.CustomerID;
import com.yann.inventoryservice.domain.vo.ProductID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "products")
public class Product {
    @Id
    private ProductID id;
    private String name;
    private double price;
    private int availableQuantity;
    private int maxQuantity;

    private final List<ReservedProduct> reservedProducts = new ArrayList<>();

    public Product() {
    }

    public Product(ProductID id, String name, double price, int initialQuantity, int maxQuantity) {
        if (name == null || name.isBlank()) {
            throw new IllegaInitInventoryException("Name cannot be null or empty");
        }

        if (price <= 0.0) {
            throw new IllegaInitInventoryException("Price cannot be less than or equal to 0.0");
        }

        if (initialQuantity > maxQuantity) {
            throw new IllegaInitInventoryException("Initial quantity cannot be greater than max quantity");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.availableQuantity = initialQuantity;
        this.maxQuantity = maxQuantity;
    }

    public String checkAvailability() {
        if (availableQuantity <= 0) {
            throw new OutOfStockException("Product is out of stock. Please refill");
        }

        double percentage = getAvailabilityPercentage();

        if (percentage <= 50) {
            return String.format("Product is low: %.2f%%.", percentage);
        }

        return String.format("Product in stock: %.2f%%", percentage);
    }

    private double getAvailabilityPercentage() {
        if (maxQuantity < 50) {
            throw new IllegaInitInventoryException("The initialization of a product cannot be lower than 50");
        }

        return ((double) availableQuantity / maxQuantity) * 100;
    }

    public void increaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalQuantityUpdateException("Quantity to increase must be greater than zero");
        }

        if (availableQuantity > maxQuantity) {
            throw new IllegalQuantityUpdateException("Quantity to increase exceeds maximum quantity");
        }
        this.availableQuantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalQuantityUpdateException("Quantity to reduce must be greater than zero");
        }

        if (quantity > availableQuantity) {
            throw new OutOfStockException("Product is out of stock. Please refill");
        }
        this.availableQuantity -= quantity;
    }

    public void checkIfProductIsAlreadyInitialized(List<Product> products) {
        Set<String> seenNames = new HashSet<>();

        boolean hasDuplicate = products.stream().map(Product::getName)
                                       .anyMatch(name -> !seenNames.add(name));

        if (hasDuplicate) {
            throw new IllegalInventoryUpdateException("Duplicate product found and therefor not added");
        }
    }

    public ProductID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalInventoryUpdateException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0.0) {
            throw new IllegalInventoryUpdateException("Price cannot be less than or equal to 0.0");
        }
        this.price = price;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        if (maxQuantity < 50) {
            throw new IllegalInventoryUpdateException("Max quantity must be equal or greater than 50");
        }
        this.maxQuantity = maxQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }
}
