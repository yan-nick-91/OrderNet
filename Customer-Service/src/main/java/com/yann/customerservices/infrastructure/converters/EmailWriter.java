package com.yann.customerservices.infrastructure.converters;

import com.yann.customerservice.domain.vo.Email;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class EmailWriter implements Converter<Email, Value> {
    @Override
    public Value convert(Email source) {
        return Values.value(source.value());
    }
}
