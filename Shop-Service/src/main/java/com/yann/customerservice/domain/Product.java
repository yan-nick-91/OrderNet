package com.yann.customerservice.domain;

import com.yann.customerservice.domain.vo.ProductID;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Product")
public class Product {
    @Id
    private ProductID productID;
    private String productName;
    private double price;

    public Product() {
    }

    public Product(ProductID productID, String productName, double price) {
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

    public double getPrice() {
        return price;
    }
}
