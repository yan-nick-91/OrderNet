package com.yann.ordersservice.application.services;

import com.yann.ordersservice.application.dto.*;
import com.yann.ordersservice.application.mapper.OrderMapper;
import com.yann.ordersservice.domain.Order;
import com.yann.ordersservice.domain.PaymentValidator;
import com.yann.ordersservice.domain.exceptions.OrderNotFound;
import com.yann.ordersservice.domain.utils.CreateIDFactory;
import com.yann.ordersservice.domain.vo.OrderID;
import com.yann.ordersservice.infrastructure.events.publisher.OrderEventPublisher;
import com.yann.ordersservice.infrastructure.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;
    private final CreateIDFactory<OrderID> orderIDFactory;

    public OrderServiceImpl(OrderRepository orderRepository, OrderEventPublisher orderEventPublisher,
                            CreateIDFactory<OrderID> orderIDFactory) {
        this.orderRepository = orderRepository;
        this.orderEventPublisher = orderEventPublisher;
        this.orderIDFactory = orderIDFactory;
    }

    @Override
    public PaymentResponseDTO saveIncomingOrderFromCustomer(
            String customerIDAsString, CustomerOrderDTO customerOrderDTO) {
        PaymentValidator paymentValidator = new PaymentValidator();
        paymentValidator.verifyOrdersPayment(customerOrderDTO);
        OrderID orderID = orderIDFactory.create();
        Order order = OrderMapper.toOrder(orderID, customerIDAsString, customerOrderDTO);
        orderRepository.save(order);
        return new PaymentResponseDTO(orderID.value(), order.getDate().toString());
    }

    @Override
    public OrdersResponseDTO getNewestOrder() {
        Order order = orderRepository.findNewestOrder();
        return OrderMapper.toOrdersResponseDTO(order);
    }

    @Override
    public OrdersResponseDTO getOldestOrder() {
        Order order = orderRepository.findOldestOrder();
        return OrderMapper.toOrdersResponseDTO(order);
    }

    @Override
    public List<OrdersResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderMapper::toOrdersResponseDTO).toList();
    }

    @Override
    public OrdersResponseDTO getOrderById(String orderIDAsString) {
        Order order = getOrderByOrderID(orderIDAsString);
        return OrderMapper.toOrdersResponseDTO(order);
    }

    @Override
    public OrderToInventoryDTO sendOrderToInventory(
            String orderIDAsString) {
        Order order = getOrderByOrderID(orderIDAsString);
        OrderToInventoryDTO orderToInventoryDTO = OrderMapper.toOrderToInventoryDTO(order);
        orderEventPublisher.sendOrderToInventory(orderToInventoryDTO);
        return orderToInventoryDTO;
    }

    // Helpers
    private Order getOrderByOrderID(String orderIDAsString) {
        OrderID orderID = orderIDFactory.set(orderIDAsString);
        return orderRepository.findById(orderID)
                                     .orElseThrow(() -> new OrderNotFound("Order not found"));
    }
}
