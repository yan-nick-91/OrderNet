package com.yann.shopservice.infrastructure.converters;

import com.yann.shopservice.domain.vo.OrderID;
import org.neo4j.driver.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class OrderIDReader implements Converter<Value, OrderID> {
    @Override
    public OrderID convert(Value source) {
        return new OrderID(source.asString());
    }
}
