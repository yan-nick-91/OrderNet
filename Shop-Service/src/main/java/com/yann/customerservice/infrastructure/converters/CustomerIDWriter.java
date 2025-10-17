package com.yann.customerservice.infrastructure.converters;

import com.yann.customerservice.domain.vo.CustomerID;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class CustomerIDWriter implements Converter<CustomerID, Value> {
    @Override
    public Value convert(CustomerID source) {
        return Values.value(source.value());
    }
}
