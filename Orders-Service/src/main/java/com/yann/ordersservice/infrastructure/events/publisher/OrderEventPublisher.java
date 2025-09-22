package com.yann.ordersservice.infrastructure.events.publisher;

import com.yann.ordersservice.application.dto.OrderToInventoryDTO;
import org.springframework.stereotype.Service;

@Service
public interface OrderEventPublisher {
    void sendOrderToInventory(OrderToInventoryDTO orderToInventoryDTO);
}
