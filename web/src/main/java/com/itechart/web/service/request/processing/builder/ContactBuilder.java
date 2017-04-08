package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Contact;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.validation.AbstractValidationService;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * Builder of contact object from request parameters.
 */
public class ContactBuilder {
    private Logger logger = LoggerFactory.getLogger(ContactBuilder.class);

    public Contact buildContact(Map<String, String> parameters) throws ValidationException {
        logger.info("Build contact entity with parameters: {}", parameters);
        AbstractValidationService validationService = ServiceFactory.getInstance().getValidationService();

        String nameParam = StringUtils.trim(parameters.get("name"));
        String surnameParam = StringUtils.trim(parameters.get("surname"));
        String patronymicParam = StringUtils.trim(parameters.get("patronymic"));
        String dateOfBirthParam = StringUtils.trim(parameters.get("dateOfBirth"));
        String genderParam = StringUtils.trim(parameters.get("gender"));
        String familyStatusParam = StringUtils.trim(parameters.get("familyStatus"));
        String websiteParam = StringUtils.trim(parameters.get("website"));
        String emailParam = StringUtils.trim(parameters.get("email"));
        String placeOfWorkParam = StringUtils.trim(parameters.get("placeOfWork"));
        String citizenshipParam = StringUtils.trim(parameters.get("citizenship"));
        Contact contact = new Contact();
        //set name

        if (validationService.validateName(nameParam)) {
            contact.setName(nameParam);
        } else {
            throw new ValidationException("Illegal name");
        }
        //set surname
        if (validationService.validateName(surnameParam)) {
            contact.setSurname(surnameParam);
        } else {
            throw new ValidationException("Illegal surname");
        }
        if (StringUtils.isNotBlank(patronymicParam)) {
            if (validationService.validateName(patronymicParam)) {
                contact.setPatronymic(patronymicParam);
            } else {
                throw new ValidationException("Illegal patronymic");
            }
        }
        //set date of birth
        DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy");
        Date dateOfBirth = null;
        if (StringUtils.isNotBlank(dateOfBirthParam)) {
            DateTime dateTime = null;
            try {
                dateTime = format.parseDateTime(dateOfBirthParam);
            } catch (Exception e) {
                throw new ValidationException("Date has illegal format");
            }
            if (dateTime != null)
                dateOfBirth = dateTime.toDate();
        }
        contact.setDateOfBirth(dateOfBirth);

        if (StringUtils.isNotBlank(genderParam)) {
            try {
                contact.setGender(Contact.Gender.valueOf(genderParam.toUpperCase()));
            } catch (Exception e) {
                throw new ValidationException("Gender parameter has illegal value", e);
            }
        } else {
            throw new ValidationException("Gender parameter is empty");
        }

        if (StringUtils.isNotBlank(familyStatusParam)) {
            try {
                contact.setFamilyStatus(Contact.FamilyStatus.valueOf(familyStatusParam.toUpperCase()));

            } catch (Exception e) {
                throw new ValidationException("Family status parameter has illegal value", e);
            }
        } else {
            throw new ValidationException("Family status parameter is empty");
        }
        if (StringUtils.isNotBlank(websiteParam)) {
            if (validationService.validateField(websiteParam)) {
                contact.setWebsite(websiteParam);
            } else {
                throw new ValidationException("Invalid website field value");
            }
        }
        if (StringUtils.isNotBlank(emailParam)) {
            if (validationService.validateEmail(emailParam)) {
                contact.setEmail(emailParam);
            } else {
                throw new ValidationException("Invalid email field value");
            }
        }
        if (StringUtils.isNotBlank(placeOfWorkParam)) {
            if (validationService.validateField(placeOfWorkParam)) {
                contact.setPlaceOfWork(placeOfWorkParam);
            } else {
                throw new ValidationException("Invalid place of work field value");
            }
        }
        if (StringUtils.isNotBlank(citizenshipParam)) {
            if (validationService.validateField(citizenshipParam)) {
                contact.setCitizenship(citizenshipParam);
            } else {
                throw new ValidationException("Invalid citizenship field value");
            }
        }
        return contact;
    }
}
