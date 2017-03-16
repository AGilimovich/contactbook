package com.itechart.web.parser;

import com.itechart.data.entity.Phone;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for creating phone object out of request parameters.
 */
public class PhoneRequestParamParser {
    private static final String regex = "(\\w+)=(\\w+)&?";
    private static final Pattern pattern = Pattern.compile(regex);

    public static Phone parseRequest(String request) throws ParseException {
        Phone phone = new Phone();
        Matcher matcher = pattern.matcher(request);
        Map<String, String> parameters = new HashMap<>();
        while (matcher.find()) {
            parameters.put(matcher.group(1), matcher.group(2));
        }
        String countryCode = parameters.get("countryCode");
        String operatorCode = parameters.get("operatorCode");
        String phoneNumber = parameters.get("number");
        Phone.PhoneType phoneType = Phone.PhoneType.valueOf(parameters.get("type"));
        String comment = parameters.get("comment");

        if (countryCode == null || operatorCode == null || phoneNumber == null || phoneType == null || comment == null) {
            throw new ParseException();
        }


        phone.setCountryCode(countryCode);
        phone.setOperatorCode(operatorCode);
        phone.setPhoneNumber(phoneNumber);
        phone.setPhoneType(phoneType);
        phone.setComment(comment);


        return phone;


    }


}
