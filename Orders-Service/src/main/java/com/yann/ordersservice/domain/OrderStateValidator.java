package com.yann.ordersservice.domain;

import com.yann.ordersservice.domain.exceptions.InternalOrderStateErrorException;
import com.yann.ordersservice.domain.vo.OrderID;

import java.util.List;
import java.util.stream.Collectors;

public class OrderStateValidator {
    public void orderStateIsPaid(OrderID orderID, Customer customer) {
        List<ProductRelation> products = customer.getCart()
                                                 .getProducts();

        boolean paymentIsConfirmed = products.stream().allMatch(r ->
                r.getProductRelationType() ==
                        ProductRelationType.PAYMENT_CONFIRMED);

        if (!paymentIsConfirmed) {
            throw new InternalOrderStateErrorException("Something went wrong. " +
                    "Cannot set order state to Paid. Product relation types: " +
                    "[" + renderOrderStateException(products) + "], for order ID" + orderID);
        }
    }

    private String renderOrderStateException(List<ProductRelation> products) {
        return products.stream()
                       .map(r -> r.getProduct().getProductName() + " = " + r.getProductRelationType())
                       .collect(Collectors.joining(", "));
    }
}
