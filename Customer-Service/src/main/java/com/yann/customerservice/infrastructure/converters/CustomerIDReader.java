package com.yann.customerservice.infrastructure.converters;

import com.yann.customerservice.domain.vo.CustomerID;
import org.neo4j.driver.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class CustomerIDReader implements Converter<Value, CustomerID> {
    @Override
    public CustomerID convert(Value source) {
        return new CustomerID(source.asString());
    }
}
