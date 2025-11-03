package com.yann.customerservice.infrastructure.converters;

import com.yann.customerservice.domain.vo.Email;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class EmailWriter implements Converter<Email, String> {

    @Override
    public String convert(Email source) {
        return source.value();
    }
}
