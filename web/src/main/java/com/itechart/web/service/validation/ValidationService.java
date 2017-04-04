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
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (emailPattern.matcher(email).matches())
            return true;
        else {
            return false;
        }
    }

    @Override
    public boolean validatePhone(String countryCode, String operatorCode, String phoneNumber) {
        logger.info("Validating country code: {}, operator code: {}, phone number: {}", countryCode, operatorCode, phoneNumber);
        if (StringUtils.isBlank(countryCode) || StringUtils.isBlank(operatorCode) || StringUtils.isBlank(phoneNumber))
            return false;
        String countryCodeRegex = "\\+\\d{3}";
        String operatorCodeRegex = "\\d{2}";
        String phoneNumberRegex = "\\d{7}";
        Pattern countryCodePattern = Pattern.compile(countryCodeRegex);
        Pattern operatorCodePattern = Pattern.compile(operatorCodeRegex);
        Pattern phoneNumberPattern = Pattern.compile(phoneNumberRegex);
        if (countryCodePattern.matcher(countryCode).matches()) {
            if (operatorCodePattern.matcher(operatorCode).matches()) {
                if (phoneNumberPattern.matcher(phoneNumber).matches()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean validateId(String id) {
        logger.info("Validating id: {}", id);
        if (StringUtils.isBlank(id)) return false;
        String idRegex = "[1-9]\\d{0,18}";
        Pattern pattern = Pattern.compile(idRegex);
        if (pattern.matcher(id).matches())
            return true;
        return false;
    }

    @Override
    public boolean validateName(String name) {
        logger.info("Validating credential: {}", name);
        if (StringUtils.isBlank(name)) return false;
        String nameRegex = "[[А-ЯЁ][-А-яЁё]\\w\\s][^\\d]{1,50}";
        Pattern pattern = Pattern.compile(nameRegex);
        if (pattern.matcher(name).matches())
            return true;
        return false;
    }
}
