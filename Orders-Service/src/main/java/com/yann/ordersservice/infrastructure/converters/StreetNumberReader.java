package com.yann.ordersservice.infrastructure.converters;

import com.yann.ordersservice.domain.vo.StreetNumber;
import org.springframework.core.convert.converter.Converter;

public class StreetNumberReader implements Converter<String, StreetNumber> {
    @Override
    public StreetNumber convert(String source) {
        return new StreetNumber(source);
    }
}
