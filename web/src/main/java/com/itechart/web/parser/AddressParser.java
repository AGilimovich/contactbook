package com.itechart.web.parser;

import com.itechart.data.entity.Address;

import java.util.Map;

/**
 * Created by Aleksandr on 17.03.2017.
 */
public class AddressParser {
    public Address parseAddress(Map<String, String> requestParameters) {
        //create address object from request
        //-----------------------------------------------------
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
