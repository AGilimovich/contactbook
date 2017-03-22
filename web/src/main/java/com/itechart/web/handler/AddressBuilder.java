package com.itechart.web.handler;

import com.itechart.data.entity.Address;

import java.util.Map;

/**
 * Created by Aleksandr on 17.03.2017.
 */
public class AddressBuilder {
    public Address buildAddress(Map<String, String> requestParameters) {
        Address address = new Address();
        address.setCountry(requestParameters.get("country"));
        address.setCity(requestParameters.get("city"));
        address.setStreet(requestParameters.get("street"));
        address.setHouse(requestParameters.get("house"));
        address.setApartment(requestParameters.get("apartment"));
        address.setZipCode(requestParameters.get("zipCode"));
        return address;
    }
}
