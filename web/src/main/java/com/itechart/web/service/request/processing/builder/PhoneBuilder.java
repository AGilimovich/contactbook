package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Phone;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.validation.AbstractValidationService;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Parses phone objects from request parameters.
 */
public class PhoneBuilder {

    public Phone buildPhone(Map<String, String> parameters) throws ValidationException {
        String idParam = parameters.get("id");
        String countryCodeParam = parameters.get("countryCode");
        String operatorCodeParam = parameters.get("operatorCode");
        String phoneNumberParam = parameters.get("number");
        String phoneTypeParam = parameters.get("type");
        String commentParam = parameters.get("comment");
        AbstractValidationService validationService = ServiceFactory.getServiceFactory().getValidationService();
        Phone phone = new Phone();
        if (StringUtils.isNotBlank(idParam)) {
            if (validationService.validateId(idParam))
                phone.setId(Long.valueOf(idParam));
            else throw new ValidationException("Invalid id of phone");
        }
        if (StringUtils.isNotBlank(countryCodeParam) && StringUtils.isNotBlank(operatorCodeParam) && StringUtils.isNotBlank(phoneNumberParam)) {
            if (validationService.validatePhone(countryCodeParam, operatorCodeParam, phoneNumberParam)) {
                phone.setCountryCode(countryCodeParam);
                phone.setOperatorCode(operatorCodeParam);
                phone.setPhoneNumber(phoneNumberParam);
            }
            else throw new ValidationException("Illegal phone parameters");
        }

        if (StringUtils.isNotBlank(phoneTypeParam))
            phone.setPhoneType(Phone.PhoneType.valueOf(phoneTypeParam.toUpperCase()));
        if (StringUtils.isNotBlank(commentParam)) {
            phone.setComment(commentParam);
        }

        return phone;
    }


}
