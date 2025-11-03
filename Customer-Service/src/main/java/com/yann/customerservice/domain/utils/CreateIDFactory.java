package com.yann.customerservice.domain.utils;

public interface CreateIDFactory<ID> {
    ID create();
    ID set(String id);
}
