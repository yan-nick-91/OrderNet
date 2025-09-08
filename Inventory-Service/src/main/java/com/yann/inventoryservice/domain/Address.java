package com.yann.inventoryservice.domain;

import com.yann.inventoryservice.domain.vo.StreetNumber;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Address")
public class Address {
    @Id
    private String zipcode;
    private String streetName;
    private StreetNumber streetNumber;
    private String city;
    private String country;

    public Address() {}

    public Address(String zipcode, String streetName,
                   StreetNumber streetNumber, String city, String country) {
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
