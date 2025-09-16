package com.yann.ordersservice.application.services;

import com.yann.ordersservice.application.dto.OrderToInventoryDTO;
import com.yann.ordersservice.application.dto.OrdersResponseDTO;
import com.yann.ordersservice.application.dto.PaymentResponseDTO;

import java.util.List;

public interface OrderService {
    void saveIncomingOrderFromCustomer(PaymentResponseDTO paymentResponseDTO);

    OrdersResponseDTO getNewestOrder();

    OrdersResponseDTO getOldestOrder();

    List<OrdersResponseDTO> getAllOrders();

    OrdersResponseDTO getOrderById(String orderIDAsString);

    OrderToInventoryDTO sendOrderToInventory(String orderIDAsString);
}
