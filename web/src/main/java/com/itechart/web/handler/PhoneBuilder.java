package com.itechart.web.handler;

import com.itechart.data.entity.Phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses phone objects from request parameters.
 */
public class PhoneBuilder {
    private static final String regex = "(\\w+)=(\\+*\\w*)&?";
    private static final Pattern pattern = Pattern.compile(regex);
    private ArrayList<Phone> newPhones = new ArrayList<>();
    private ArrayList<Phone> updatedPhones = new ArrayList<>();
    private ArrayList<Phone> deletedPhones = new ArrayList<>();


    public void buildPhones(Map<String, String> requestParameters) {
        ArrayList<Phone> phones = new ArrayList<>();
        for (Map.Entry<String, String> requestParam : requestParameters.entrySet()) {
            //if phone parameters received in request
            if (requestParam.getKey().equals("phone")) {
                Matcher matcher = pattern.matcher(requestParam.getValue());

                Map<String, String> parameters = new HashMap<>();
                while (matcher.find()) {
                    parameters.put(matcher.group(1), matcher.group(2));
                }
                String countryCode = parameters.get("countryCode");
                String operatorCode = parameters.get("operatorCode");
                String phoneNumber = parameters.get("number");
                String phoneTypeParam = parameters.get("type");
//                String status = parameters.get("status");
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

//                switch (status) {
//                    case "NEW":
//                        newPhones.add(phone);
//                        break;
//                    case "EDITED":
//                        updatedPhones.add(phone);
//                        break;
//                    case "NONE":
//                        break;
//                    case "DELETED":
//                        deletedPhones.add(phone);
//                        break;
//                    default:
//                }

                newPhones.add(phone);
            }
        }
    }

    public Phone buildPhone(Map<String, String> parameters) {
        String countryCode = parameters.get("countryCode");
        String operatorCode = parameters.get("operatorCode");
        String phoneNumber = parameters.get("number");
        String phoneTypeParam = parameters.get("type");
//                String status = parameters.get("status");
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

//    public ArrayList<Phone> getNewPhones() {
//        return newPhones;
//    }
//
//    public ArrayList<Phone> getUpdatedPhones() {
//        return updatedPhones;
//
//    }
//
//    public ArrayList<Phone> getDeletedPhones() {
//        return deletedPhones;
//
//    }


}
