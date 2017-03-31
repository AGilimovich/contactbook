package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Contact;
import com.itechart.web.service.request.processing.parser.DateTimeParser;
import org.apache.commons.lang3.StringUtils;


import java.util.Map;

/**
 * Created by Aleksandr on 17.03.2017.
 */
public class ContactBuilder {
    public Contact buildContact(Map<String, String> parameters) {
        Contact contact = new Contact();
        String name = parameters.get("name");
        String surname = parameters.get("surname");

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(surname))
            // TODO: 29.03.2017 validation exception
            return null;
        contact.setName(name);
        contact.setSurname(surname);
        contact.setPatronymic(parameters.get("patronymic"));
        String dateOfBirth = parameters.get("dateOfBirth");
        contact.setDateOfBirth(DateTimeParser.parseDate(dateOfBirth, "dd.MM.yyyy"));
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
        return contact;
    }
}
