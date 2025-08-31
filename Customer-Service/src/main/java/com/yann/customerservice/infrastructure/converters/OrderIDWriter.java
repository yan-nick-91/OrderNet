package com.yann.customerservice.infrastructure.converters;

import com.yann.customerservice.domain.vo.OrderID;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class OrderIDWriter implements Converter<OrderID, Value> {
    @Override
    public Value convert(OrderID source) {
        return Values.value(source.value());
    }
}
