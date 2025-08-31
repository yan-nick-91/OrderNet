package com.yann.customerservice.infrastructure.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yann.customerservice.application.dto.PaymentResponseDTO;
import com.yann.customerservice.infrastructure.events.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
class CustomerEventPublisherImpl implements CustomerEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(CustomerEventPublisherImpl.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomerEventPublisherImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishCustomerEvent(PaymentResponseDTO paymentResponseDTO) {
        try {
            String message = objectMapper.writeValueAsString(paymentResponseDTO);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.ROUTING_KEY,
                    message);
            log.info("Customer event published: {}", message);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize PaymentResponseDTO: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
