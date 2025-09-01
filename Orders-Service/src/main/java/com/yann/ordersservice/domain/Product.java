package com.yann.ordersservice.domain;

import com.yann.ordersservice.domain.vo.ProductID;

public class Product {
    private ProductID productID;
    private String productName;
    private double price;

    public Product() {
    }

    public Product(ProductID productID, String productName, double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }

        this.productID = productID;
        this.productName = productName;
        this.price = price;
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
}
