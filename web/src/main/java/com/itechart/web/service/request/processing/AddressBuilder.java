package com.itechart.web.service.request.processing;

import com.itechart.data.entity.Address;

import java.util.Map;

/**
 * Created by Aleksandr on 17.03.2017.
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
