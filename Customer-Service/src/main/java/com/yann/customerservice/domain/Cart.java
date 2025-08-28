package com.yann.customerservice.domain;

import com.yann.customerservice.domain.exceptions.IllegalProductQuantityException;
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
    private final List<ProductRelation> products = new ArrayList<>();

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

    public void removeProduct(Product product) {
        this.products.removeIf(relation -> relation.getProduct().equals(product));
    }

    public CartID getCartID() {
        return cartID;
    }

    public void setCartID(CartID cartID) {
        this.cartID = cartID;
    }

    public List<ProductRelation> getProducts() {
        return products;
    }
}
