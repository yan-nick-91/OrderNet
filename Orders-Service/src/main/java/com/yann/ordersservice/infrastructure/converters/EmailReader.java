package com.yann.ordersservice.infrastructure.converters;

import com.yann.ordersservice.domain.vo.Email;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class EmailReader implements Converter<String, Email> {
    @Override
    public Email convert(String source) {
        return new Email(source);
    }
}
