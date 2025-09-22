package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.OrderID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class OrderIDWriter implements Converter<OrderID, String> {
    @Override
    public String convert(OrderID source) {
        return source.value();
    }
}
