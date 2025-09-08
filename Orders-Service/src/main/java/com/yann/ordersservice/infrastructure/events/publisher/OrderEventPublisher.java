package com.yann.ordersservice.infrastructure.events.publisher;

import com.yann.ordersservice.application.dto.OrderToInventoryRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface OrderEventPublisher {
    void sendOrderToInventory(OrderToInventoryRequestDTO orderToInventoryRequestDTO);
}
