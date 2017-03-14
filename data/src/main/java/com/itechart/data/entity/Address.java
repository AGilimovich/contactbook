package com.itechart.data.entity;

/**
 * Represents living address of contact.
 */
public class Address {
    private long id;
    private String country;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private String zipCode;

    public Address(long id, String country, String city, String street, String house, String apartment, String zipCode) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.zipCode = zipCode;

    }

    public Address() {
    }

    public long getId() {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
