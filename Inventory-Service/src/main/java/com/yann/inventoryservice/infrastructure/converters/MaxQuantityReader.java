package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.MaxQuantity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class MaxQuantityReader implements Converter<Integer, MaxQuantity> {
    @Override
    public MaxQuantity convert(Integer source) {
        return new MaxQuantity(source);
    }
}
