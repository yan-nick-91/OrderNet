package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.exception.*;
import com.yann.inventoryservice.domain.vo.MaxQuantity;
import com.yann.inventoryservice.domain.vo.ProductID;
import com.yann.inventoryservice.domain.vo.ProductName;
import com.yann.inventoryservice.domain.vo.ProductPrice;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
            throw new IllegalInitInventoryException("Initial quantity cannot be greater than max quantity");
        }

        this.productID = productID;
        this.name = name;
        this.price = price;
        this.availableQuantity = initialQuantity;
        this.maxQuantity = maxQuantity;
    }

    public void increaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalQuantityUpdateException("Quantity to increase must be greater than zero");
        }

        this.availableQuantity += quantity;

        if (availableQuantity > maxQuantity.value()) {
            this.availableQuantity -= quantity;
            throw new IllegalQuantityUpdateException("Quantity to increase exceeds maximum quantity");
        }
    }

    public void decreaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalQuantityUpdateException("Quantity to decrease must be greater than zero");
        }

        this.availableQuantity -= quantity;

        if (quantity > availableQuantity) {
            throw new OutOfStockException("Product is out of stock. Please refill");
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
