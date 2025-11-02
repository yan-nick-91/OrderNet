package com.yann.shopservice.infrastructure.events;

import com.yann.shopservice.application.dto.PaymentResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CustomerEventPublisher {
    void publishCustomerEvent(PaymentResponseDTO paymentResponseDTO);
}
