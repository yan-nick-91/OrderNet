package com.yann.customerservice.infrastructure.events;

import com.yann.customerservice.application.dto.PaymentResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CustomerEventPublisher {
    void publishCustomerEvent(PaymentResponseDTO paymentResponseDTO);
}
