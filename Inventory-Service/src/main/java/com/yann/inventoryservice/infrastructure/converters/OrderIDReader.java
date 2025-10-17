package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.OrderID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class OrderIDReader implements Converter<String, OrderID> {
    @Override
    public OrderID convert(String source) {
        return new OrderID(source);
    }
}
