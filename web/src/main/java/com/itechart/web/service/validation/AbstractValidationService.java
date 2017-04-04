package com.itechart.web.service.validation;

/**
 * Created by Aleksandr on 27.03.2017.
 */
public interface AbstractValidationService {
    boolean validateEmail(String email);

    boolean validatePhone(String countryCode, String operatorCode, String phoneNumber);

    boolean validateId(String id);

    boolean validateName(String credential);
}
