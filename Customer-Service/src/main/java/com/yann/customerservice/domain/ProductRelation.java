package com.yann.customerservice.domain;

import com.yann.customerservice.domain.exceptions.IllegalProductQuantityException;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class ProductRelation {
    @Id
    @GeneratedValue
    private Long id;

    private final ProductRelationType productRelationType;
    private int quantity;

    @TargetNode
    private final Product product;

    public ProductRelation(Product product, ProductRelationType productRelationType, int quantity) {
        if (quantity <= 0) {
            throw new IllegalProductQuantityException("Quantity must be greater than 0");
        }
        this.product = product;
        this.productRelationType = productRelationType;
        this.quantity = quantity;
    }

    public ProductRelationType getProductRelationType() {
        return productRelationType;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalProductQuantityException("Quantity must be greater than 0");
        }
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalProductQuantityException("Quantity must be greater than 0");
        }
        this.quantity -= quantity;
    }
}
