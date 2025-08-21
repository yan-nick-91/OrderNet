package com.yann.customerservices.infrastructure.converters;

import com.yann.customerservice.domain.vo.ProductID;
import org.neo4j.driver.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ProductIDReader implements Converter<Value, ProductID> {
    @Override
    public ProductID convert(Value source) {
        return new ProductID(source.asString());
    }
}
