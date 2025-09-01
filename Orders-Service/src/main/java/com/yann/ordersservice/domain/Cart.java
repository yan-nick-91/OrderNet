package com.yann.ordersservice.domain;

import com.yann.ordersservice.domain.vo.CartID;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Cart")
public class Cart {
    private CartID cartID;
    private List<ProductRelation> products;
    private double totalPrice;

    public Cart() {
    }

    public Cart(CartID cartID, List<ProductRelation> products, double totalPrice) {
        this.cartID = cartID;
        this.products = products;
        this.totalPrice = totalPrice;
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
}
