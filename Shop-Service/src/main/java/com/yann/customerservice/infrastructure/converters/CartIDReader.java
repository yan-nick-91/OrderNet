package com.yann.customerservice.infrastructure.converters;

import com.yann.customerservice.domain.vo.CartID;
import org.neo4j.driver.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class CartIDReader implements Converter<Value, CartID> {
    @Override
    public CartID convert(Value source) {
        return new CartID(source.asString());
    }
}
