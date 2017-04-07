package com.itechart.web.service.validation;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * Validation service.
 */
public class ValidationService implements AbstractValidationService {
    private Logger logger = LoggerFactory.getLogger(ValidationService.class);

    @Override
    public boolean validateEmail(String email) {
        logger.info("Validating email: {}", email);
        if (StringUtils.isBlank(email)) return false;
        boolean isValid = EmailValidator.getInstance().isValid(email);
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
