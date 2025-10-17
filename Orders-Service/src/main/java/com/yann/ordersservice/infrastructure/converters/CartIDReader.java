package com.yann.ordersservice.infrastructure.converters;

import com.yann.ordersservice.domain.vo.CartID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class CartIDReader implements Converter<String, CartID> {
    @Override
    public CartID convert(String source) {
        return new CartID(source);
    }
}
