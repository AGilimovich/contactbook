package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Contact;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Map;

/**
 * Builds contact using values from map of parameters.
 */
public class ContactBuilder {
    public Contact buildContact(Map<String, String> parameters) throws ValidationException {
        String nameParam = parameters.get("name");
        String surnameParam = parameters.get("surname");
        String patronymicParam = parameters.get("patronymic");
        String dateOfBirthParam = parameters.get("dateOfBirth");
        String genderParam = parameters.get("gender");
        String familyStatusParam = parameters.get("familyStatus");
        String websiteParam = parameters.get("website");
        String emailParam = parameters.get("email");
        String placeOfWorkParam = parameters.get("placeOfWork");
        String citizenshipParam = parameters.get("citizenship");
        Contact contact = new Contact();
        //set name
        if (StringUtils.isNotBlank(nameParam)) {
            contact.setName(nameParam);
        } else {
            throw new ValidationException("Name can't be empty");
        }
        //set surname
        if (StringUtils.isNotBlank(surnameParam)) {
            contact.setSurname(surnameParam);
        } else {
            throw new ValidationException("Surname can't be empty");
        }
        if (StringUtils.isNotBlank(patronymicParam)) {
            contact.setPatronymic(patronymicParam);
        }
        //set date of birth
        DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy");
        Date dateOfBirth = null;
        if (StringUtils.isNotBlank(dateOfBirthParam)) {
            DateTime dateTime = null;
            try {
                dateTime = format.parseDateTime(dateOfBirthParam);
            } catch (Exception e){
                e.printStackTrace();
                throw new ValidationException("Date has illegal format");
            }
            if (dateTime != null)
                dateOfBirth = dateTime.toDate();
        }
        contact.setDateOfBirth(dateOfBirth);

        if (StringUtils.isNotBlank(genderParam)) {
            contact.setGender(Contact.Gender.valueOf(genderParam.toUpperCase()));
        }

        if (StringUtils.isNotBlank(familyStatusParam)) {
            contact.setFamilyStatus(Contact.FamilyStatus.valueOf(familyStatusParam.toUpperCase()));
        }
        if (StringUtils.isNotBlank(websiteParam)) {
            contact.setWebsite(websiteParam);
        }
        if (StringUtils.isNotBlank(emailParam)) {
            if (ServiceFactory.getServiceFactory().getValidationService().validateEmail(emailParam))
                contact.setEmail(emailParam);
        }
        if (StringUtils.isNotBlank(placeOfWorkParam)) {
            contact.setPlaceOfWork(placeOfWorkParam);
        }
        if (StringUtils.isNotBlank(citizenshipParam)) {
            contact.setCitizenship(citizenshipParam);
        }
        return contact;
    }
}
