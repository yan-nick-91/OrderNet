package com.yann.ordersservice.application.services;

import com.yann.ordersservice.application.dto.OrdersResponseDTO;
import com.yann.ordersservice.application.dto.PaymentResponseDTO;
import com.yann.ordersservice.application.mapper.OrderMapper;
import com.yann.ordersservice.domain.Order;
import com.yann.ordersservice.domain.Product;
import com.yann.ordersservice.domain.exceptions.OrderNotFound;
import com.yann.ordersservice.domain.utils.CreateIDFactory;
import com.yann.ordersservice.domain.vo.OrderID;
import com.yann.ordersservice.infrastructure.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CreateIDFactory<OrderID> orderIDFactory;

    public OrderServiceImpl(OrderRepository orderRepository, CreateIDFactory<OrderID> orderIDFactory) {
        this.orderRepository = orderRepository;
        this.orderIDFactory = orderIDFactory;
    }

    @Override
    public void saveIncomingOrderFromCustomer(PaymentResponseDTO paymentResponseDTO) {
        OrderID orderID = orderIDFactory.set(paymentResponseDTO.orderID());
        Order order = OrderMapper.toOrder(orderID, paymentResponseDTO);
        orderRepository.save(order);
    }

    @Override
    public List<OrdersResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderMapper::toOrdersResponseDTO).toList();
    }

    @Override
    public OrdersResponseDTO getOrderById(String orderIDAsString) {
        OrderID orderID = orderIDFactory.set(orderIDAsString);
        Order order = orderRepository.findById(orderID)
                                     .orElseThrow(() -> new OrderNotFound("Order not found"));
        return OrderMapper.toOrdersResponseDTO(order);
    }
}
