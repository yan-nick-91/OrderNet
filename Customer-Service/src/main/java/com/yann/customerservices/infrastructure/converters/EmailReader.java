package com.yann.customerservices.infrastructure.converters;

import com.yann.customerservice.domain.vo.Email;
import org.neo4j.driver.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class EmailReader implements Converter<Value, Email> {
    @Override
    public Email convert(Value source) {
        return new Email(source.asString());
    }
}
