package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Phone;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Parses phone objects from request parameters.
 */
public class PhoneBuilder {

    public Phone buildPhone(Map<String, String> parameters) {
        String idParam = parameters.get("id");
        String countryCodeParam = parameters.get("countryCode");
        String operatorCodeParam = parameters.get("operatorCode");
        String phoneNumberParam = parameters.get("number");
        String phoneTypeParam = parameters.get("type");
        String commentParam = parameters.get("comment");

        Phone phone = new Phone();
        if (StringUtils.isNotEmpty(idParam)){
            phone.setId(Long.valueOf(idParam));
        }
        if (StringUtils.isNotEmpty(countryCodeParam)){
            phone.setCountryCode(countryCodeParam);
        }
        if (StringUtils.isNotEmpty(operatorCodeParam)){
            phone.setOperatorCode(operatorCodeParam);
        }
        if (StringUtils.isNotEmpty(phoneNumberParam)){
            phone.setPhoneNumber(phoneNumberParam);
        }
        if (StringUtils.isNotEmpty(phoneTypeParam))
            phone.setPhoneType(Phone.PhoneType.valueOf(phoneTypeParam.toUpperCase()));
        if(StringUtils.isNotEmpty(commentParam)){
            phone.setComment(commentParam);

        }

        return phone;
    }


}
