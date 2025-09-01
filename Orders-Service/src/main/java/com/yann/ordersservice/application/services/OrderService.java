package com.yann.ordersservice.application.services;

import com.yann.ordersservice.application.dto.PaymentResponseDTO;

public interface OrderService {
    void saveIncomingOrderFromCustomer(PaymentResponseDTO paymentResponseDTO);
}
