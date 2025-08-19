package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.ProductID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ProductIDReader implements Converter<String, ProductID> {
    @Override
    public ProductID convert(String source) {
        return new ProductID(source);
    }
}
