package com.yann.customerservice.domain.utils;

import com.yann.customerservice.domain.vo.CartID;
import com.yann.customerservice.domain.vo.CustomerID;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class CartIDFactory implements CreateIDFactory<CartID> {
    @Override
    public CartID create() {
        String dateFormat = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return new CartID(String.format("CART-ID-%s-%s", dateFormat, UUID.randomUUID()));
    }

    @Override
    public CartID set(String id) {
        return new CartID(id);
    }
}
