package com.yann.ordersservice.domain.utils;

public interface CreateIDFactory<ID> extends IDFactory<ID> {
    ID create();
}
