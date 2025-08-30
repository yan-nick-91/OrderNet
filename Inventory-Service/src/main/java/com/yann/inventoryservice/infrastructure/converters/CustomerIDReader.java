package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.CustomerID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class CustomerIDReader implements Converter<String, CustomerID> {
    @Override
    public CustomerID convert(String source) {
        return new CustomerID(source);
    }
}
