package com.yann.customerservice.infrastructure.events.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "order-exchange";
    public static final String QUEUE_NAME = "customer-events-queue";
    public static final String ROUTING_KEY = "customer-events";

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding binding(DirectExchange orderExchange, Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(orderExchange)
                .with(ROUTING_KEY);
    }
}
