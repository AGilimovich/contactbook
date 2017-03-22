package com.itechart.web.handler;

import com.itechart.data.entity.Contact;

import java.util.Map;

/**
 * Created by Aleksandr on 17.03.2017.
 */
public class ContactBuilder {
    public Contact buildContact(Map<String, String> requestParameters) {
        Contact contact = new Contact();
        contact.setName(requestParameters.get("name"));
        contact.setSurname(requestParameters.get("surname"));
        contact.setPatronymic(requestParameters.get("patronymic"));
        String dateOfBirth = requestParameters.get("dateOfBirth");
        contact.setDateOfBirth(DateTimeParser.parseDate(dateOfBirth, "yyyy-MM-dd"));
        if (requestParameters.get("gender") != null) {
            contact.setGender(Contact.Gender.valueOf(requestParameters.get("gender").toUpperCase()));
        }
        contact.setCitizenship(requestParameters.get("citizenship"));
        if (requestParameters.get("familyStatus") != null) {
            contact.setFamilyStatus(Contact.FamilyStatus.valueOf(requestParameters.get("familyStatus").toUpperCase()));
        }
        contact.setWebsite(requestParameters.get("website"));
        contact.setEmail(requestParameters.get("email"));
        contact.setPlaceOfWork(requestParameters.get("placeOfWork"));
//        //set photo
        if (requestParameters.get("photo") != null)
            contact.setPhoto(requestParameters.get("photo"));
        return contact;
    }
}
