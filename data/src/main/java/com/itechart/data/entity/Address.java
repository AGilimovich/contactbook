package com.itechart.data.entity;

/**
 * Represents living address of contact.
 */
public class Address {
    private int id;


    public Address(int id, String country, String city, String street, String house, String apartment, String zipCode) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.zipCode = zipCode;
    }

    private String country;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private String zipCode;

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getApartment() {
        return apartment;
    }

    public String getZipCode() {
        return zipCode;
    }
}
