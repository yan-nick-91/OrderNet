package com.yann.shopservice.domain;

import com.yann.shopservice.domain.exceptions.IllegalAdjustmentTypeException;
import com.yann.shopservice.domain.exceptions.IllegalProductQuantityException;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class ProductRelation {
    @Id
    @GeneratedValue
    private Long id;

    private ProductRelationType productRelationType;
    private int quantity;

    @TargetNode
    private final Product product;

    public ProductRelation(Product product, ProductRelationType productRelationType, int quantity) {
        if (quantity < 0) {
            throw new IllegalProductQuantityException("Quantity must be greater than 0");
        }
        this.product = product;
        this.productRelationType = productRelationType;
        this.quantity = quantity;
    }

    public ProductRelationType getProductRelationType() {
        return productRelationType;
    }

    public void setProductRelationType(ProductRelationType productRelationType) {
        this.productRelationType = productRelationType;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void adjustProductQuantity(String adjustmentType, int newQuantity) {
        AdjustmentType type = AdjustmentType.valueOf(adjustmentType.toUpperCase());
        ProductQuantityAdjuster productQuantityAdjuster = new ProductQuantityAdjuster();
        switch (type) {
            case INCREASE -> quantity = productQuantityAdjuster.increaseQuantity(quantity, newQuantity);
            case DECREASE -> quantity = productQuantityAdjuster.decreaseQuantity(quantity, newQuantity);
            default -> throw new IllegalAdjustmentTypeException(
                    "Adjustment type must be increase or decrease"
            );
        }
    }
}
