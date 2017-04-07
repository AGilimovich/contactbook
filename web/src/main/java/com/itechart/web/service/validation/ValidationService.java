package com.itechart.web.service.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * Created by Aleksandr on 22.03.2017.
 */
public class ValidationService implements AbstractValidationService {
    private Logger logger = LoggerFactory.getLogger(ValidationService.class);

    @Override
    public boolean validateEmail(String email) {
        logger.info("Validating email: {}", email);
        if (StringUtils.isBlank(email)) return false;
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern emailPattern = Pattern.compile(emailRegex);
        boolean isValid = emailPattern.matcher(email).matches();
        logger.info("Email validation result: {}", isValid);
        return isValid;
    }

    @Override
    public boolean validatePhone(String countryCode, String operatorCode, String phoneNumber) {
        logger.info("Validating country code: {}, operator code: {}, phone number: {}", countryCode, operatorCode, phoneNumber);
        if (StringUtils.isBlank(countryCode) || StringUtils.isBlank(operatorCode) || StringUtils.isBlank(phoneNumber))
            return false;
        String countryCodeRegex = "\\+\\d{3}";
        String operatorCodeRegex = "\\d{2}";
        String phoneNumberRegex = "\\d{7}";
        boolean isValid = false;
        Pattern countryCodePattern = Pattern.compile(countryCodeRegex);
        Pattern operatorCodePattern = Pattern.compile(operatorCodeRegex);
        Pattern phoneNumberPattern = Pattern.compile(phoneNumberRegex);
        if (countryCodePattern.matcher(countryCode).matches()) {
            if (operatorCodePattern.matcher(operatorCode).matches()) {
                if (phoneNumberPattern.matcher(phoneNumber).matches()) {
                    isValid = true;
                }
            }
        }
        logger.info("Phone validation result: {}", isValid);
        return isValid;
    }

    @Override
    public boolean validateId(String id) {
        logger.info("Validating id: {}", id);
        if (StringUtils.isBlank(id)) return false;
        String idRegex = "[1-9]\\d{0,18}";
        Pattern pattern = Pattern.compile(idRegex);
        boolean isValid = pattern.matcher(id).matches();
        logger.info("Id validation result: {}", isValid);
        return isValid;
    }

    @Override
    public boolean validateName(String name) {
        logger.info("Validating name: {}", name);
        if (StringUtils.isBlank(name)) return false;
        String nameRegex = "[[А-ЯЁ][-А-яЁё][a-zA-Z]]{1,50}";
        Pattern pattern = Pattern.compile(nameRegex);
        boolean isValid = pattern.matcher(name).matches();
        logger.info("Name validation result: {}", isValid);
        return isValid;
    }

    @Override
    public boolean validateField(String field) {
        logger.info("Validating field: {}", field);
        if (StringUtils.isBlank(field)) return false;
        String nameRegex = "[<>]";
        Pattern pattern = Pattern.compile(nameRegex);
        boolean isValid = !pattern.matcher(field).find();
        logger.info("Field validation result: {}", isValid);
        return isValid;
    }
}
