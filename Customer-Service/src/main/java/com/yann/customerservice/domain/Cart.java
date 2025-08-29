package com.yann.customerservice.domain;

import com.yann.customerservice.domain.exceptions.IllegalProductQuantityException;
import com.yann.customerservice.domain.exceptions.ProductNotFoundException;
import com.yann.customerservice.domain.vo.CartID;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("Cart")
public class Cart {
    @Id
    private CartID cartID;

    @Relationship(type = "HAS_PRODUCT", direction = Relationship.Direction.OUTGOING)
    private List<ProductRelation> products = new ArrayList<>();
    private double totalPrice;

    public Cart() {
    }

    public Cart(CartID cartID) {
        this.cartID = cartID;
    }

    public void addNewProductToCart(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalProductQuantityException("Quantity must be greater than 0");
        }
        products.add(new ProductRelation(product, ProductRelationType.IN_CART, quantity));
    }

    public void adjustProductQuantity(String productName, String adjustmentType, int quantity) {
        ProductRelation productRelation = products.stream()
                                                  .filter(pr -> pr.getProduct()
                                                                  .getProductName()
                                                                  .equals(productName))
                                                  .findAny()
                                                  .orElseThrow(() ->
                                                          new ProductNotFoundException("Product not found"));

        productRelation.checkTypeForAdjustmentQuantity(adjustmentType, quantity);

        if (productRelation.getQuantity() == 0) {
            removeProduct(productRelation.getProduct());
        }
    }

    public void removeProduct(Product product) {
        this.products.removeIf(relation -> relation.getProduct().equals(product));
    }

    public CartID getCartID() {
        return cartID;
    }

    public List<ProductRelation> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
