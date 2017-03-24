package com.itechart.web.service;

import com.itechart.data.entity.Phone;

import java.util.Map;

/**
 * Parses phone objects from request parameters.
 */
public class PhoneBuilder {

    public Phone buildPhone(Map<String, String> parameters) {
        String countryCode = parameters.get("countryCode");
        String operatorCode = parameters.get("operatorCode");
        String phoneNumber = parameters.get("number");
        String phoneTypeParam = parameters.get("type");
        Phone.PhoneType phoneType = null;
        if (phoneTypeParam != null)
            phoneType = Phone.PhoneType.valueOf(phoneTypeParam.toUpperCase());
        String comment = parameters.get("comment");

        Phone phone = new Phone();
        phone.setCountryCode(countryCode);
        phone.setOperatorCode(operatorCode);
        phone.setPhoneNumber(phoneNumber);
        phone.setPhoneType(phoneType);
        phone.setComment(comment);
        return phone;
    }


}
