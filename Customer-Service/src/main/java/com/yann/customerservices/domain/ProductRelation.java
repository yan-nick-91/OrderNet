package com.yann.customerservices.domain;

import com.yann.customerservice.domain.ProductRelationType;
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

    @TargetNode
    private final Product product;

    public ProductRelation(Product product, ProductRelationType productRelationType) {
        this.product = product;
        this.productRelationType = productRelationType;
    }

    public ProductRelationType getProductRelationType() {
        return productRelationType;
    }

    public Product getProduct() {
        return product;
    }
}
