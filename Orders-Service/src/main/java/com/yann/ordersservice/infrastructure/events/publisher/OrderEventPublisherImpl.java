package com.yann.ordersservice.infrastructure.events.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yann.ordersservice.application.dto.OrderToInventoryDTO;
import com.yann.ordersservice.infrastructure.events.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
class OrderEventPublisherImpl implements OrderEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(OrderEventPublisherImpl.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    OrderEventPublisherImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendOrderToInventory(OrderToInventoryDTO orderToInventoryDTO) {
        try {
            String message = objectMapper.writeValueAsString(orderToInventoryDTO);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ORDER_TO_INVENTORY_EXCHANGE_NAME,
                    RabbitMQConfig.ORDER_TO_INVENTORY_ROUTING_KEY,
                    message);
            log.info("Message sent from order to inventory. OrderID: {}", orderToInventoryDTO.OrderID());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
