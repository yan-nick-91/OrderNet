package com.yann.ordersservice.domain;

import com.yann.ordersservice.application.dto.CustomerOrderDTO;
import com.yann.ordersservice.domain.exceptions.InternalPaymentErrorException;

import java.math.BigDecimal;

public class PaymentValidator {
    public void verifyOrdersPayment(CustomerOrderDTO customerOrderDTO) {
        BigDecimal reportedPrice = BigDecimal.valueOf(customerOrderDTO.cart().totalPrice());
        BigDecimal calculatedTotal = customerOrderDTO.cart()
                                                     .products()
                                                     .stream()
                                                     .map(p -> BigDecimal.valueOf(
                                                             p.getProduct().getPrice()).multiply(
                                                                     BigDecimal.valueOf(p.getQuantity())))
                                                     .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (reportedPrice.compareTo(calculatedTotal) != 0) {
            throw new InternalPaymentErrorException(String.format(
                    "Total price mismatch! Reported: %.2f, Calculated: %.2f",
                    reportedPrice, calculatedTotal
            ));
        }
    }
}
