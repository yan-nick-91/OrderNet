package com.yann.customerservice.domain.vo;

class StreetNumberTest extends AbstractValueObjectTest<StreetNumber> {
    @Override
    protected StreetNumber createValidInstanceOne() {
        return new StreetNumber("44");
    }

    @Override
    protected StreetNumber createValidInstanceTwo() {
        return new StreetNumber("44");
    }

    @Override
    protected StreetNumber differentValidInstance() {
        return new StreetNumber("45");
    }
}