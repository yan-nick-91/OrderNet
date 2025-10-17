package com.yann.customerservice.infrastructure.converters;

import com.yann.customerservice.domain.vo.StreetNumber;
import org.neo4j.driver.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class StreetNumberReader implements Converter<Value, StreetNumber> {
    @Override
    public StreetNumber convert(Value source) {
        return new StreetNumber(source.asString());
    }
}
