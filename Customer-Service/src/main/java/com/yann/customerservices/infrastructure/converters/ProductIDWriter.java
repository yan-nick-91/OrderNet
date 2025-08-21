package com.yann.customerservices.infrastructure.converters;

import com.yann.customerservice.domain.vo.ProductID;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class ProductIDWriter implements Converter<ProductID, Value> {
    @Override
    public Value convert(ProductID source) {
        return Values.value(source.value());
    }
}
