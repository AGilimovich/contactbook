package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Address;
import com.itechart.web.service.request.processing.parser.AttachmentFormFieldParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Class for building Address object from request parameters.
 */
public class AddressBuilder {
    private Logger logger = LoggerFactory.getLogger(AddressBuilder.class);

    public Address buildAddress(Map<String, String> parameters) {
        logger.info("Build address entity with parameters: {}", parameters);
        Address address = new Address();
        String countryParam = StringUtils.trim(parameters.get("country"));
        String cityParam = StringUtils.trim(parameters.get("city"));
        String streetParam = StringUtils.trim(parameters.get("street"));
        String houseParam = StringUtils.trim(parameters.get("house"));
        String apartmentParam = StringUtils.trim(parameters.get("apartment"));
        String zipCodeParam = StringUtils.trim(parameters.get("zipCode"));

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
