package com.yann.customerservice.domain;

import com.yann.customerservice.domain.exceptions.ProductAlreadyInitializedInCartException;

public class CustomerCartValidator {
    public void checkIfCartHasProduct(Customer existingCustomer, Cart cart, String productName) {
        if (cart.getProducts().isEmpty()) {
            return;
        }

        existingCustomer.getCart()
                        .getProducts()
                        .stream()
                        .map(ProductRelation::getProduct)
                        .filter(product -> product.getProductName().equalsIgnoreCase(productName))
                        .findAny()
                        .ifPresent(product -> {
                            throw new ProductAlreadyInitializedInCartException(
                                    "Product " + product.getProductName() +
                                            " already in cart. Use increasing or decreasing to adjust the " +
                                            "product quantity.");
                        });
    }
}
