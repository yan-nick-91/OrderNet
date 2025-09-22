package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.StreetNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class StreetNumberReader implements Converter<String, StreetNumber> {
    @Override
    public StreetNumber convert(String source) {
        return new StreetNumber(source);
    }
}
