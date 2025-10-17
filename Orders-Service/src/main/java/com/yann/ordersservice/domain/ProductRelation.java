package com.yann.ordersservice.domain;


public class ProductRelation {
    private ProductRelationType productRelationType;
    private int quantity;
    public Product product;

    public ProductRelation() {
    }

    public ProductRelation(ProductRelationType productRelationType, int quantity, Product product) {
        this.productRelationType = productRelationType;
        this.quantity = quantity;
        this.product = product;
    }

    public ProductRelationType getProductRelationType() {
        return productRelationType;
    }

    public void setProductRelationType(ProductRelationType productRelationType) {
        this.productRelationType = productRelationType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
