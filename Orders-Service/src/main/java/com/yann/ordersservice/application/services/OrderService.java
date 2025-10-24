package com.yann.ordersservice.application.services;

import com.yann.ordersservice.application.dto.*;

import java.util.List;

public interface OrderService {
    PaymentResponseDTO saveIncomingOrderFromCustomer(String customerIDAsString, CustomerOrderDTO customerOrderDTO);

    OrdersResponseDTO getNewestOrder();

    OrdersResponseDTO getOldestOrder();

    List<OrdersResponseDTO> getAllOrders();

    OrdersResponseDTO getOrderById(String orderIDAsString);

    OrderToInventoryDTO sendOrderToInventory(String orderIDAsString);
}
