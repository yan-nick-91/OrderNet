package com.yann.customerservice.infrastructure.converters;

import com.yann.customerservice.domain.vo.CustomerID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class CustomerIDReader implements Converter<String, CustomerID> {
    @Override
    public CustomerID convert(String source) {
        return new CustomerID(source);
    }
}
