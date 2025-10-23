package com.yann.ordersservice.domain;

import com.yann.ordersservice.application.dto.CustomerOrderDTO;
import com.yann.ordersservice.domain.exceptions.InternalPaymentErrorException;

public class PaymentValidator {
    public void verifyOrdersPayment(CustomerOrderDTO customerOrderDTO) {
        double reportedPrice = customerOrderDTO.cart().totalPrice();
        double calculatedTotal = customerOrderDTO.cart()
                                         .products()
                                         .stream()
                                         .mapToDouble(p -> p.getProduct().getPrice() * p.getQuantity())
                                         .sum();

        if (Double.compare(reportedPrice, calculatedTotal) != 0) {
            throw new InternalPaymentErrorException(String.format(
                    "Total price mismatch! Reported: %.2f, Calculated: %.2f",
                    reportedPrice, calculatedTotal
            ));
        }
    }
}
