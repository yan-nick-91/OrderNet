package com.yann.customerservice.domain;

import com.yann.customerservice.domain.vo.StreetNumber;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("address")
public class Address {
    @Column("zipcode")
    private String zipcode;
    @Column("street_name" )
    private String streetName;
    @Column("street_number")
    private StreetNumber streetNumber;
    @Column("city")
    private String city;
    @Column("country")
    private String country;

    public Address() {}

    public Address(String zipcode, String streetName, StreetNumber streetNumber, String city, String country) {
        this.zipcode = zipcode;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
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
