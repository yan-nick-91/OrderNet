package com.yann.inventoryservice.infrastructure.converters;


import com.yann.inventoryservice.domain.vo.ProductName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class ProductNameWriter implements Converter<ProductName, String> {
    @Override
    public String convert(ProductName source) {
        return source.value();
    }
}
