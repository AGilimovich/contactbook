package com.itechart.web.service.request.processing;

import com.itechart.data.entity.Contact;

import java.util.Map;

/**
 * Created by Aleksandr on 17.03.2017.
 */
public class ContactBuilder {
    public Contact buildContact(Map<String, String> parameters) {
        Contact contact = new Contact();
        contact.setName(parameters.get("name"));
        contact.setSurname(parameters.get("surname"));
        contact.setPatronymic(parameters.get("patronymic"));
        String dateOfBirth = parameters.get("dateOfBirth");
        contact.setDateOfBirth(DateTimeParser.parseDate(dateOfBirth, "yyyy-MM-dd"));
        if (parameters.get("gender") != null) {
            contact.setGender(Contact.Gender.valueOf(parameters.get("gender").toUpperCase()));
        }
        contact.setCitizenship(parameters.get("citizenship"));
        if (parameters.get("familyStatus") != null) {
            contact.setFamilyStatus(Contact.FamilyStatus.valueOf(parameters.get("familyStatus").toUpperCase()));
        }
        contact.setWebsite(parameters.get("website"));
        contact.setEmail(parameters.get("email"));
        contact.setPlaceOfWork(parameters.get("placeOfWork"));
        //set photo
//        if (parameters.get("photo") != null)
//            contact.setPhoto(parameters.get("photo_stored"));
        return contact;
    }
}
