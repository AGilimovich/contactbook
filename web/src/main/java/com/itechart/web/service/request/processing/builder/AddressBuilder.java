package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Address;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Class for building Address object from request parameters.
 */
public class AddressBuilder {
    public Address buildAddress(Map<String, String> parameters) {
        Address address = new Address();
        String countryParam = parameters.get("country");
        String cityParam = parameters.get("city");
        String streetParam = parameters.get("street");
        String houseParam = parameters.get("house");
        String apartmentParam = parameters.get("apartment");
        String zipCodeParam = parameters.get("zipCode");

        if (StringUtils.isNotBlank(countryParam)) {
            address.setCountry(countryParam);
        }
        if (StringUtils.isNotBlank(cityParam)) {
            address.setCity(cityParam);
        }
        if (StringUtils.isNotBlank(streetParam)) {
            address.setStreet(streetParam);
        }
        if (StringUtils.isNotBlank(houseParam)) {
            address.setHouse(houseParam);
        }
        if (StringUtils.isNotBlank(apartmentParam)) {
            address.setApartment(apartmentParam);
        }
        if (StringUtils.isNotBlank(zipCodeParam)) {
            address.setZipCode(zipCodeParam);
        }

        return address;
    }
}
