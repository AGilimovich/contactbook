package com.itechart.web.service.validation;

/**
 * Interface of validation service.
 */
public interface AbstractValidationService {
    boolean validateEmail(String email);

    boolean validatePhone(String countryCode, String operatorCode, String phoneNumber);

    boolean validateId(String id);

    boolean validateName(String name);

    boolean validateField(String field);

}
