package com.yann.customerservice.domain.vo;

import com.yann.customerservice.domain.utils.CartIDFactory;
import com.yann.customerservice.domain.utils.CreateIDFactory;
import org.junit.jupiter.api.BeforeEach;

class CartIDTest extends AbstractValueObjectTest<CartID> {
    private CreateIDFactory<CartID> cartIDFactory;

    @BeforeEach
    void setUp() {
        this.cartIDFactory = new CartIDFactory();
    }

    @Override
    protected CartID createValidInstanceOne() {
        return cartIDFactory.set("CART-ID-20250926-123e4567-e89b-12d3-a456-426614176723");
    }

    @Override
    protected CartID createValidInstanceTwo() {
        return cartIDFactory.set("CART-ID-20250926-123e4567-e89b-12d3-a456-426614176723");
    }

    @Override
    protected CartID differentValidInstance() {
        return cartIDFactory.set("CART-ID-20250926-123e4567-e89b-12d3-a456-426614176724");
    }
}