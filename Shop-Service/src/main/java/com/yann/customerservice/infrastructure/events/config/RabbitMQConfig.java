package com.yann.customerservice.infrastructure.events.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String CUSTOMER_TO_ORDERS_EXCHANGE_NAME = "customer-to-orders-exchange";
    public static final String CUSTOMER_TO_ORDERS_QUEUE_NAME = "customer-to-orders-queue";
    public static final String CUSTOMER_TO_ORDERS_ROUTING_KEY = "customer-to-orders-events";

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(CUSTOMER_TO_ORDERS_EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(CUSTOMER_TO_ORDERS_QUEUE_NAME);
    }

    @Bean
    public Binding binding(DirectExchange orderExchange, Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(orderExchange)
                .with(CUSTOMER_TO_ORDERS_ROUTING_KEY);
    }
}
