package com.yann.customerservice.domain;

import com.yann.customerservice.domain.exceptions.IllegalAdjustmentTypeException;
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

    public void checkTypeForAdjustmentQuantity(String adjustmentType, int quantity) {
        AdjustmentType type = AdjustmentType.valueOf(adjustmentType.toUpperCase());

        switch (type) {
            case INCREASE -> increaseQuantity(quantity);
            case DECREASE -> decreaseQuantity(quantity);
            default -> throw new IllegalAdjustmentTypeException(
                    "Adjustment type must be increase or decrease"
            );
        }
    }

    private void increaseQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalProductQuantityException("Quantity must be greater than 0");
        }
        this.quantity += newQuantity;
    }

    private void decreaseQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalProductQuantityException("Quantity must be greater than 0");
        }

        this.quantity -= newQuantity;
        if (this.quantity <= 0) {
            this.quantity += newQuantity;
            throw new IllegalProductQuantityException("Cannot decrease quantity below 0");
        }
    }
}
