package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Address;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.validation.AbstractValidationService;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Class for building Address object from request parameters.
 */
public class AddressBuilder {
    private Logger logger = LoggerFactory.getLogger(AddressBuilder.class);

    public Address buildAddress(Map<String, String> parameters) throws ValidationException {
        logger.info("Build address entity with parameters: {}", parameters);
        Address address = new Address();
        String countryParam = StringUtils.trim(parameters.get("country"));
        String cityParam = StringUtils.trim(parameters.get("city"));
        String streetParam = StringUtils.trim(parameters.get("street"));
        String houseParam = StringUtils.trim(parameters.get("house"));
        String apartmentParam = StringUtils.trim(parameters.get("apartment"));
        String zipCodeParam = StringUtils.trim(parameters.get("zipCode"));
        AbstractValidationService validationService = ServiceFactory.getServiceFactory().getValidationService();
        if (StringUtils.isNotBlank(countryParam)) {
            if (validationService.validateField(countryParam)) {
                address.setCountry(countryParam);
            } else {
                throw new ValidationException("Invalid country field value");
            }
        }
        if (StringUtils.isNotBlank(cityParam)) {
            if (validationService.validateField(cityParam)) {
                address.setCity(cityParam);
            } else {
                throw new ValidationException("Invalid city field value");
            }
        }
        if (StringUtils.isNotBlank(streetParam)) {
            if (validationService.validateField(streetParam)) {
                address.setStreet(streetParam);
            } else {
                throw new ValidationException("Invalid street field value");
            }
        }
        if (StringUtils.isNotBlank(houseParam)) {
            if (validationService.validateField(houseParam)) {
                address.setHouse(houseParam);
            } else {
                throw new ValidationException("Invalid house field value");
            }
        }
        if (StringUtils.isNotBlank(apartmentParam)) {
            if (validationService.validateField(apartmentParam)) {
                address.setApartment(apartmentParam);
            } else {
                throw new ValidationException("Invalid apartment field value");
            }
        }
        if (StringUtils.isNotBlank(zipCodeParam)) {
            if (validationService.validateField(zipCodeParam)) {
                address.setZipCode(zipCodeParam);
            } else {
                throw new ValidationException("Invalid zip code field value");
            }
        }

        return address;
    }
}
