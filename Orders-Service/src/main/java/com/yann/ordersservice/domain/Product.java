package com.yann.ordersservice.domain;

import com.yann.ordersservice.domain.vo.ProductID;

public class Product {
    private ProductID productID;
    private String productName;
    private double price;
    private int quantity;

    public Product(ProductID productID, String productName, double price, int quantity) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }

        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductID getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
