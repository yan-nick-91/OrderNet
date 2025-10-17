package com.yann.inventoryservice.infrastructure.events.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yann.inventoryservice.application.InventoryService;
import com.yann.inventoryservice.application.dto.OrderToInventoryRequestDTO;
import com.yann.inventoryservice.infrastructure.events.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventListener {
    private static final Logger log = LoggerFactory.getLogger(InventoryEventListener.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final InventoryService inventoryService;

    public InventoryEventListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_TO_INVENTORY_QUEUE_NAME)
    public void orderToInventory(String message) {
        try {
            OrderToInventoryRequestDTO orderToInventoryRequestDTO =
                    objectMapper.convertValue(message, OrderToInventoryRequestDTO.class);
            log.info("Received Order data: {}", orderToInventoryRequestDTO);
            inventoryService.receivingOrder(orderToInventoryRequestDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
