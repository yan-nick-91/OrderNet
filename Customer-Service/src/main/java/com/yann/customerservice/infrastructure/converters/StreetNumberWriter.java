package com.yann.customerservice.infrastructure.converters;

import com.yann.customerservice.domain.vo.StreetNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class StreetNumberWriter implements Converter<StreetNumber, String> {

    @Override
    public String convert(StreetNumber source) {
        return source.value();
    }
}
