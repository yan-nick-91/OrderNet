package com.yann.ordersservice.domain;

import com.yann.ordersservice.domain.vo.StreetNumber;

public class Address {
    private String streetName;
    private StreetNumber streetNumber;
    private String city;
    private String country;

    public Address(String streetName, StreetNumber streetNumber, String city, String country) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public StreetNumber getStreetNumber() {
        return streetNumber;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
