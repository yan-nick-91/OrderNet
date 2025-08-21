package com.yann.customerservices.domain;

import com.yann.customerservice.domain.vo.ProductID;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Product")
public class Product {
    @Id
    private ProductID id;
    private String productName;
    private double price;

    public Product() {
    }

    public Product(ProductID id, String productName, double price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
    }

    public ProductID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }
}
