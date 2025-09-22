package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.ProductPrice;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class ProductPriceWriter implements Converter<ProductPrice, Double> {
    @Override
    public Double convert(ProductPrice source) {
        return source.value();
    }
}
