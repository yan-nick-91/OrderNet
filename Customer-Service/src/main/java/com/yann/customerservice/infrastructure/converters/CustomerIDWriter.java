package com.yann.customerservice.infrastructure.converters;

import com.yann.customerservice.domain.vo.CustomerID;
import org.springframework.core.convert.converter.Converter;


public class CustomerIDWriter implements Converter<CustomerID, String> {
    @Override
    public String convert(CustomerID source) {
        return source.value();
    }
}
