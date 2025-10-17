package com.yann.inventoryservice.domain.vo;

class StreetNumberTest extends AbstractValueObjectTest<StreetNumber> {
    @Override
    protected StreetNumber createValidInstanceOne() {
        return new StreetNumber("50a");
    }

    @Override
    protected StreetNumber createValidInstanceTwo() {
        return new StreetNumber("50a");
    }

    @Override
    protected StreetNumber differentValidInstance() {
        return new StreetNumber("50b");
    }
}