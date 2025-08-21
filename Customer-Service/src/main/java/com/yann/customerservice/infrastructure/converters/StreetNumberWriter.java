package com.yann.customerservice.infrastructure.converters;

import com.yann.customerservice.domain.vo.StreetNumber;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class StreetNumberWriter implements Converter<StreetNumber, Value> {
    @Override
    public Value convert(StreetNumber source) {
        return Values.value(source.value());
    }
}
