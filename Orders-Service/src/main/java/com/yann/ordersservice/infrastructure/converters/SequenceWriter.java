package com.yann.ordersservice.infrastructure.converters;

import com.yann.ordersservice.domain.vo.Sequence;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class SequenceWriter implements Converter<Sequence, Integer> {
    @Override
    public Integer convert(Sequence source) {
        return source.value();
    }
}
