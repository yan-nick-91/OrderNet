package com.yann.customerservice.domain.vo;

class EmailTest extends AbstractValueObjectTest<Email> {

    @Override
    protected Email createValidInstanceOne() {
        return new Email("john@test.com");
    }

    @Override
    protected Email createValidInstanceTwo() {
        return new Email("john@test.com");
    }

    @Override
    protected Email differentValidInstance() {
        return new Email("jane@test.com");
    }
}