package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.StreetNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class StreetNumberWriter implements Converter<StreetNumber, String> {
    @Override
    public String convert(StreetNumber source) {
        return source.value();
    }
}
