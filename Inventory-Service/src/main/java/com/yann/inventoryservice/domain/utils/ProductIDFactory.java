package com.yann.inventoryservice.domain.utils;

import com.yann.inventoryservice.domain.vo.ProductID;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class ProductIDFactory implements CreateIDFactory<ProductID> {
    @Override
    public ProductID create() {
        String dateFormat = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return new ProductID(String.format("PROD-ID-%s-%s", dateFormat, UUID.randomUUID()));
    }

    @Override
    public ProductID set(String id) {
        return new ProductID(id);
    }
}
