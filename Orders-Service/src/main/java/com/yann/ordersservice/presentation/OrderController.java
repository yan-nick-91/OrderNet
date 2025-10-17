package com.yann.ordersservice.presentation;

import com.yann.ordersservice.application.services.OrderService;
import com.yann.ordersservice.domain.exceptions.OrderNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllOrders() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/newest")
    public ResponseEntity<Object> getNewestOrder() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getNewestOrder());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/oldest")
    public ResponseEntity<Object> getOldestOrder() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getOldestOrder());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{orderIDAsString}")
    public ResponseEntity<Object> getOrder(@PathVariable String orderIDAsString) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(orderIDAsString));
        } catch (OrderNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/{orderIDAsString}")
    public ResponseEntity<Object> sendOrderToInventory(@PathVariable String orderIDAsString) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.sendOrderToInventory(orderIDAsString));
        } catch (OrderNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
