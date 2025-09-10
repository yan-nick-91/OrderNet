package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.ProductPrice;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ProductPriceReader implements Converter<Double, ProductPrice> {
    @Override
    public ProductPrice convert(Double source) {
        return new ProductPrice(source);
    }
}
