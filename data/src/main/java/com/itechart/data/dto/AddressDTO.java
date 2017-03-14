package com.itechart.data.dto;

/**
 * DTO class for address entity. Contains all fields of Address class except for zipCode.
 */
public class AddressDTO {


    private int id;
    private String country;
    private String city;
    private String street;
    private String house;
    private String apartment;


    public AddressDTO(int id, String country, String city, String street, String house, String apartment) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

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
}
