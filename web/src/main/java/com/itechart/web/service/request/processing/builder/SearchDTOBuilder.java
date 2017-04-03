package com.itechart.web.service.request.processing.builder;

import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Contact;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Map;

/**
 * Created by Aleksandr on 03.04.2017.
 */
public class SearchDTOBuilder {

    public SearchDTO buildSearchDTO(Map<String, String> formFields) throws ValidationException {

        String surnameParam = formFields.get("surname");
        String nameParam = formFields.get("name");
        String patronymicParam = formFields.get("patronymic");
        String genderParam = formFields.get("gender");
        String familyStatusParam = formFields.get("familyStatus");
        String fromDateParam = formFields.get("fromDate");
        String toDateParam = formFields.get("toDate");
        String citizenshipParam = formFields.get("citizenship");
        String countryParam = formFields.get("country");
        String cityParam = formFields.get("city");
        String streetParam = formFields.get("street");
        String houseParam = formFields.get("house");
        String apartmentParam = formFields.get("apartment");
        String zipCodeParam = formFields.get("zipCode");

        SearchDTO dto = new SearchDTO();
        DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy");
        if (StringUtils.isNotBlank(fromDateParam)) {
            DateTime dateTime = null;
            try {
                dateTime = format.parseDateTime(fromDateParam);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ValidationException("Date has illegal format");
            }
            if (dateTime != null)
                dto.setFromDate(dateTime.toDate());
        }
        if (StringUtils.isNotBlank(toDateParam)) {
            DateTime dateTime = null;
            try {
                dateTime = format.parseDateTime(toDateParam);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ValidationException("Date has illegal format");
            }
            if (dateTime != null)
                dto.setToDate(dateTime.toDate());
        }


        if (StringUtils.isNotBlank(surnameParam)) {
            dto.setSurname(surnameParam);
        }
        if (StringUtils.isNotBlank(nameParam)) {
            dto.setName(nameParam);
        }
        if (StringUtils.isNotBlank(patronymicParam)) {
            dto.setPatronymic(patronymicParam);
        }
        if (StringUtils.isNotBlank(genderParam)) {
            if (!genderParam.equals("any"))
                dto.setGender(Contact.Gender.valueOf(genderParam.toUpperCase()));
        }
        if (StringUtils.isNotBlank(familyStatusParam)) {
            if (!familyStatusParam.equals("any"))
                dto.setFamilyStatus(Contact.FamilyStatus.valueOf(familyStatusParam.toUpperCase()));
        }


        if (StringUtils.isNotBlank(citizenshipParam)) {
            dto.setCitizenship(citizenshipParam);
        }
        if (StringUtils.isNotBlank(countryParam)) {
            dto.setCountry(countryParam);
        }
        if (StringUtils.isNotBlank(cityParam)) {
            dto.setCity(cityParam);
        }
        if (StringUtils.isNotBlank(streetParam)) {
            dto.setStreet(streetParam);
        }
        if (StringUtils.isNotBlank(houseParam)) {
            dto.setHouse(houseParam);
        }
        if (StringUtils.isNotBlank(apartmentParam)) {
            dto.setApartment(apartmentParam);
        }
        if (StringUtils.isNotBlank(zipCodeParam)) {
            dto.setZipCOde(zipCodeParam);
        }
        return dto;
    }
}
