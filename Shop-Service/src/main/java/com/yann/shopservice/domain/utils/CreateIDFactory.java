package com.yann.shopservice.domain.utils;

public interface CreateIDFactory<ID> extends IDFactory<ID> {
    ID create();
    ID set(String id);
}
