package com.yann.customerservice.domain.utils;

public interface CreateIDFactory<ID> extends IDFactory<ID> {
    ID create();
    ID set(String id);
}
