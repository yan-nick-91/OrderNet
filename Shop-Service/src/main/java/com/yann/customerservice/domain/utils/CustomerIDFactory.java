package com.yann.customerservice.domain.utils;

import com.yann.customerservice.domain.vo.CustomerID;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class CustomerIDFactory implements CreateIDFactory<CustomerID> {
    @Override
    public CustomerID create() {
        String dateFormat = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return new CustomerID(String.format("CUS-ID-%s-%s", dateFormat, UUID.randomUUID()));
    }

    @Override
    public CustomerID set(String id) {
        return new CustomerID(id);
    }
}
