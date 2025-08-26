package com.yann.inventoryservice.infrastructure.converters;

import com.yann.inventoryservice.domain.vo.MaxQuantity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class MaxQuantityWriter implements Converter<MaxQuantity, Integer> {
    @Override
    public Integer convert(MaxQuantity source) {
        return source.value();
    }
}
