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
    private long contactId;

    public Address(long id, String country, String city, String street, String house, String apartment, String zipCode, long contactId) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.zipCode = zipCode;
        this.contactId = contactId;
    }

    public Address() {
    }

    public void update(Address address) {
        if (address == null) return;
        this.country = address.getCountry();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.house = address.getHouse();
        this.apartment = address.getApartment();
        this.zipCode = address.getZipCode();
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
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
