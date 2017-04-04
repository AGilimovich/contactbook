package com.itechart.web.service.request.processing.builder;

import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Aleksandr on 03.04.2017.
 */
public class SearchDTOBuilder {
    private Logger logger = LoggerFactory.getLogger(SearchDTOBuilder.class);

    public SearchDTO buildSearchDTO(Map<String, String> formFields) throws ValidationException {
        logger.info("Build search DTO from form fields: {}", formFields);
        String surnameParam = StringUtils.trim(formFields.get("surname"));
        String nameParam = StringUtils.trim(formFields.get("name"));
        String patronymicParam = StringUtils.trim(formFields.get("patronymic"));
        String genderParam = StringUtils.trim(formFields.get("gender"));
        String familyStatusParam = StringUtils.trim(formFields.get("familyStatus"));
        String fromDateParam = StringUtils.trim(formFields.get("fromDate"));
        String toDateParam = StringUtils.trim(formFields.get("toDate"));
        String citizenshipParam = StringUtils.trim(formFields.get("citizenship"));
        String countryParam = StringUtils.trim(formFields.get("country"));
        String cityParam = StringUtils.trim(formFields.get("city"));
        String streetParam = StringUtils.trim(formFields.get("street"));
        String houseParam = StringUtils.trim(formFields.get("house"));
        String apartmentParam = StringUtils.trim(formFields.get("apartment"));
        String zipCodeParam = StringUtils.trim(formFields.get("zipCode"));

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
            if (!genderParam.equals("any")) {
                try {
                    dto.setGender(Contact.Gender.valueOf(genderParam.toUpperCase()));
                } catch (Exception e){
                    throw new ValidationException("Gender parameter has illegal value", e);
                }
            }
        }
        if (StringUtils.isNotBlank(familyStatusParam)) {
            if (!familyStatusParam.equals("any")) {
                try {
                    dto.setFamilyStatus(Contact.FamilyStatus.valueOf(familyStatusParam.toUpperCase()));
                } catch (Exception e){
                    throw new ValidationException("Gender parameter has illegal value", e);
                }
            }
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
