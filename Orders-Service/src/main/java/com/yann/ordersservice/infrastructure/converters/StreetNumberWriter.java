package com.yann.ordersservice.infrastructure.converters;

import com.yann.ordersservice.domain.vo.StreetNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class StreetNumberWriter implements Converter<StreetNumber, String> {
    @Override
    public String convert(StreetNumber source) {
        return source.value();
    }
}
