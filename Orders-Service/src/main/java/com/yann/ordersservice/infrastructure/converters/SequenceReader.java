package com.yann.ordersservice.infrastructure.converters;

import com.yann.ordersservice.domain.vo.Sequence;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class SequenceReader implements Converter<Integer, Sequence> {

    @Override
    public Sequence convert(Integer source) {
        return new Sequence(source);
    }
}
