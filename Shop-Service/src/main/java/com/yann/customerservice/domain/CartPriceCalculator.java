package com.yann.customerservice.domain;

public class CartPriceCalculator {
    public double calculateTotalPriceInCart(Cart cart) {
        double total = cart.getProducts()
                   .stream()
                   .filter(pr -> pr.getProductRelationType() == ProductRelationType.IN_CART)
                   .mapToDouble(this::calculateLinePrice)
                   .sum();

        return Math.round(total * 100) / 100.0;
    }

    private double calculateLinePrice(ProductRelation productRelation) {
        if (productRelation.getQuantity() == 0) {
            return 0.00;
        }
        return productRelation.getProduct().getPrice() * productRelation.getQuantity();
    }
}
