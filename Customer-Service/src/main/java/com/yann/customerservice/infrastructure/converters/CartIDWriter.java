package com.yann.customerservice.infrastructure.converters;

import com.yann.customerservice.domain.vo.CartID;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class CartIDWriter implements Converter<CartID, Value> {
    @Override
    public Value convert(CartID source) {
        return Values.value(source.value());
    }
}
