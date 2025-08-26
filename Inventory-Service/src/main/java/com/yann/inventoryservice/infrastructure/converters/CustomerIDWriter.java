package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.CustomerID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class CustomerIDWriter implements Converter<CustomerID, String> {
    @Override
    public String convert(CustomerID source) {
        return source.value();
    }
}
