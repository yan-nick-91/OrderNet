package com.yann.ordersservice.infrastructure.events.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.yann.ordersservice.application.dto.PaymentResponseDTO;
import com.yann.ordersservice.application.services.OrderService;
import com.yann.ordersservice.infrastructure.events.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerEventListener {
    private static final Logger log = LoggerFactory.getLogger(CustomerEventListener.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OrderService orderService;

    public CustomerEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitMQConfig.CUSTOMER_TO_ORDERS_QUEUE_NAME)
    public void receiveCustomerPayment(String message) {
        try {
            PaymentResponseDTO paymentResponseDTO = objectMapper.readValue(message, PaymentResponseDTO.class);

            log.info("Received payment event: {}", paymentResponseDTO);

            orderService.saveIncomingOrderFromCustomer(paymentResponseDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
