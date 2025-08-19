package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.ProductID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class ProductIDWriter implements Converter<ProductID, String> {
    @Override
    public String convert(ProductID source) {
        return source.value();
    }
}
