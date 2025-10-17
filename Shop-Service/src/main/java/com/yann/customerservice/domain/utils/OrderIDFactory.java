package com.yann.customerservice.domain.utils;

import com.yann.customerservice.domain.vo.OrderID;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class OrderIDFactory implements CreateIDFactory<OrderID> {
    @Override
    public OrderID create() {
        String dateFormat = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return new OrderID(String.format("ORD-ID-%s-%s", dateFormat, UUID.randomUUID()));
    }

    @Override
    public OrderID set(String id) {
        return new OrderID(id);
    }
}
