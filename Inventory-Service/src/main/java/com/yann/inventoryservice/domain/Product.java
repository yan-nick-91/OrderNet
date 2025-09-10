package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.exception.*;
import com.yann.inventoryservice.domain.vo.MaxQuantity;
import com.yann.inventoryservice.domain.vo.ProductID;
import com.yann.inventoryservice.domain.vo.ProductName;
import com.yann.inventoryservice.domain.vo.ProductPrice;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "products")
public class Product {
    @Id
    private ProductID productID;
    private ProductName name;
    private ProductPrice price;
    private int availableQuantity;
    private MaxQuantity maxQuantity;

    public Product() {
    }

    public Product(ProductID productID, ProductName name,
                   ProductPrice price, int initialQuantity,
                   MaxQuantity maxQuantity) {

        if (initialQuantity > maxQuantity.value()) {
            throw new IllegaInitInventoryException("Initial quantity cannot be greater than max quantity");
        }

        this.productID = productID;
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
        if (maxQuantity.value() < 50) {
            throw new IllegaInitInventoryException("The initialization of a product cannot be lower than 50");
        }
        return ((double) availableQuantity / maxQuantity.value()) * 100;
    }

    public void increaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalQuantityUpdateException("Quantity to increase must be greater than zero");
        }

        if (availableQuantity > maxQuantity.value()) {
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
        boolean productNameExists = products.stream()
                                       .map(Product::getName)
                                       .anyMatch(existingName -> existingName.equals(getName()));

        if (productNameExists) {
            throw new IllegalInventoryUpdateException(
                    "Cannot add product: '" + this.name + "' is already in inventory."
            );
        }
    }

    public void updateMaxQuantity(int newMaxQuantity) {
        this.maxQuantity = new MaxQuantity(newMaxQuantity);
    }

    public ProductID getProductID() {
        return productID;
    }

    public ProductName getName() {
        return name;
    }

    public void setName(ProductName name) {
        this.name = name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void setPrice(ProductPrice price) {
        this.price = price;
    }

    public MaxQuantity getMaxQuantity() {
        return maxQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }
}
