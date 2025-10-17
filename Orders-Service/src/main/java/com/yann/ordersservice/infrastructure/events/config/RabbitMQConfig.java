package com.yann.ordersservice.infrastructure.events.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // From customer
    public static final String CUSTOMER_TO_ORDERS_EXCHANGE_NAME = "customer-to-orders-exchange";
    public static final String CUSTOMER_TO_ORDERS_QUEUE_NAME = "customer-to-orders-queue";
    public static final String CUSTOMER_TO_ORDERS_ROUTING_KEY = "customer-to-orders-events";

    // to inventory
    public static final String ORDER_TO_INVENTORY_EXCHANGE_NAME = "order-to-inventory-exchange";
    public static final String ORDER_TO_INVENTORY_QUEUE_NAME = "order-to-inventory-queue";
    public static final String ORDER_TO_INVENTORY_ROUTING_KEY = "order-to-inventory-events";

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(CUSTOMER_TO_ORDERS_EXCHANGE_NAME);
    }

    @Bean
    public DirectExchange orderToInventoryExchange() {
        return new DirectExchange(ORDER_TO_INVENTORY_EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(CUSTOMER_TO_ORDERS_QUEUE_NAME);
    }

    @Bean
    public Queue orderToInventoryQueue() {
        return new Queue(ORDER_TO_INVENTORY_QUEUE_NAME);
    }

    @Bean
    public Binding customerBinding(DirectExchange orderExchange, Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(orderExchange)
                .with(CUSTOMER_TO_ORDERS_ROUTING_KEY);
    }

    @Bean
    public Binding orderToInventoryBinding(
            DirectExchange orderToInventoryExchange, Queue orderToInventoryQueue) {
        return BindingBuilder
                .bind(orderToInventoryQueue)
                .to(orderToInventoryExchange)
                .with(ORDER_TO_INVENTORY_ROUTING_KEY);
    }
}
