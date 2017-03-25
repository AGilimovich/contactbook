package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Address;

import java.util.Map;

/**
 * Class for building Address object from request parameters.
 */
public class AddressBuilder {
    public Address buildAddress(Map<String, String> parameters) {
        Address address = new Address();
        address.setCountry(parameters.get("country"));
        address.setCity(parameters.get("city"));
        address.setStreet(parameters.get("street"));
        address.setHouse(parameters.get("house"));
        address.setApartment(parameters.get("apartment"));
        address.setZipCode(parameters.get("zipCode"));
        return address;
    }
}
