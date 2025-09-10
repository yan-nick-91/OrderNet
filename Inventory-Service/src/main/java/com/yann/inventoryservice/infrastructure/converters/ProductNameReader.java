package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.ProductName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ProductNameReader implements Converter<String, ProductName> {
    @Override
    public ProductName convert(String source) {
        return new ProductName(source);
    }
}
