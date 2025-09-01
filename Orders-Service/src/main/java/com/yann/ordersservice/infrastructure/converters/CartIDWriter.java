package com.yann.ordersservice.infrastructure.converters;

import com.yann.ordersservice.domain.vo.CartID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class CartIDWriter implements Converter<CartID, String> {
    @Override
    public String convert(CartID source) {
        return source.value();
    }
}
