package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Phone;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.validation.AbstractValidationService;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Builder of phone object from request parameters.
 */
public class PhoneBuilder {
    private Logger logger = LoggerFactory.getLogger(PhoneBuilder.class);


    public Phone buildPhone(Map<String, String> parameters) throws ValidationException {
        logger.info("Build phone entity with parameters: {}", parameters);
        String idParam = StringUtils.trim(parameters.get("id"));
        String countryCodeParam = StringUtils.trim(parameters.get("countryCode"));
        String operatorCodeParam = StringUtils.trim(parameters.get("operatorCode"));
        String phoneNumberParam = StringUtils.trim(parameters.get("number"));
        String phoneTypeParam = StringUtils.trim(parameters.get("type"));
        String commentParam = StringUtils.trim(parameters.get("comment"));
        AbstractValidationService validationService = ServiceFactory.getInstance().getValidationService();
        Phone phone = new Phone();
        if (StringUtils.isNotBlank(idParam)) {
            if (validationService.validateId(idParam))
                try {
                    phone.setId(Long.valueOf(idParam));
                }catch (Exception e){
                    throw new ValidationException("Invalid phone id", e);
                }
            else throw new ValidationException("Invalid phone id");
        }
        if (StringUtils.isNotBlank(countryCodeParam) && StringUtils.isNotBlank(operatorCodeParam) && StringUtils.isNotBlank(phoneNumberParam)) {
            if (validationService.validatePhone(countryCodeParam, operatorCodeParam, phoneNumberParam)) {
                phone.setCountryCode(countryCodeParam);
                phone.setOperatorCode(operatorCodeParam);
                phone.setPhoneNumber(phoneNumberParam);
            } else throw new ValidationException("Illegal phone parameters");
        }

        if (StringUtils.isNotBlank(phoneTypeParam)) {
            try {
                phone.setPhoneType(Phone.PhoneType.valueOf(phoneTypeParam.toUpperCase()));
            } catch (Exception e) {
                throw new ValidationException("Phone type parameter has illegal value", e);
            }
        } else {
            throw new ValidationException("Phone type parameter is empty");
        }

        if (StringUtils.isNotBlank(commentParam)) {
            if (validationService.validateField(commentParam)) {
                phone.setComment(commentParam);
            } else {
                throw new ValidationException("Invalid phone comment field value");
            }
        }

        return phone;
    }


}
