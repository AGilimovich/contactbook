package com.itechart.web.service.request.processing;

import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Contact;
import com.itechart.web.properties.PropertiesManager;
import com.itechart.web.service.email.Email;
import com.itechart.web.service.email.EmailAddressesParser;
import com.itechart.web.service.request.processing.builder.FullContactDTOBuilder;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

/**
 * Class with a set of methods designed for processing different requests.
 */
public class RequestProcessingService implements AbstractRequestProcessingService {

    public FullContactDTO processMultipartContactRequest(HttpServletRequest request) throws FileSizeException, ValidationException {
        MultipartRequestHandler handler = new MultipartRequestHandler();
        handler.handle(request);
        //get map of form field names and corresponding values
        Map<String, String> formFields = handler.getFormFields();
        //get map of field names and corresponding file parts
        Map<String, FileItem> fileParts = handler.getFileParts();

        FilePartWriter writer = new FilePartWriter(PropertiesManager.FILE_PATH());
        //store file parts and get map of field names and file names
        Map<String, String> storedFiles = writer.writeFileParts(fileParts);

        FullContactDTOBuilder builder = new FullContactDTOBuilder(formFields, storedFiles);

        return builder.getFullContact();
    }

    public String[] processDeleteContactRequest(HttpServletRequest request) {
        return request.getParameterValues("isSelected");

    }


    public long processFetchSingleContactRequest(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        if (StringUtils.isNotEmpty(idParam)) {
            return Long.valueOf(idParam);
        }
        return 0;


    }

    @Override
    public String processFetchContactsRequest(HttpServletRequest request) {
        return request.getParameter("page");
    }

    public SearchDTO processSearchContactsRequest(HttpServletRequest request) {
        String surnameParam = request.getParameter("surname");
        String nameParam = request.getParameter("name");
        String patronymicParam = request.getParameter("patronymic");
        String genderParam = request.getParameter("gender");
        String familyStatusParam = request.getParameter("familyStatus");
        String fromDateParam = request.getParameter("fromDate");
        String toDateParam = request.getParameter("toDate");
        String citizenshipParam = request.getParameter("citizenship");
        String countryParam = request.getParameter("country");
        String cityParam = request.getParameter("city");
        String streetParam = request.getParameter("street");
        String houseParam = request.getParameter("house");
        String apartmentParam = request.getParameter("apartment");
        String zipCodeParam = request.getParameter("zipCode");
        SearchDTO dto = new SearchDTO();
        if (StringUtils.isNotEmpty(surnameParam)) {
            dto.setSurname(surnameParam);
        }
        if (StringUtils.isNotEmpty(nameParam)) {
            dto.setName(nameParam);
        }
        if (StringUtils.isNotEmpty(patronymicParam)) {
            dto.setPatronymic(patronymicParam);
        }
        if (StringUtils.isNotEmpty(genderParam)) {
            if (!genderParam.equals("any"))
                dto.setGender(Contact.Gender.valueOf(genderParam.toUpperCase()));
        }
        if (StringUtils.isNotEmpty(familyStatusParam)) {
            if (!familyStatusParam.equals("any"))
                dto.setFamilyStatus(Contact.FamilyStatus.valueOf(familyStatusParam.toUpperCase()));
        }
        DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy");
        if (StringUtils.isNotEmpty(fromDateParam)) {
            DateTime dateTime = format.parseDateTime(fromDateParam);
            if (dateTime != null)
                dto.setFromDate(dateTime.toDate());
        }
        if (StringUtils.isNotEmpty(toDateParam)) {
            DateTime dateTime = format.parseDateTime(toDateParam);
            if (dateTime != null)
                dto.setToDate(dateTime.toDate());
        }

        if (StringUtils.isNotEmpty(citizenshipParam)) {
            dto.setCitizenship(citizenshipParam);
        }
        if (StringUtils.isNotEmpty(countryParam)) {
            dto.setCountry(countryParam);
        }
        if (StringUtils.isNotEmpty(cityParam)) {
            dto.setCity(cityParam);
        }
        if (StringUtils.isNotEmpty(streetParam)) {
            dto.setStreet(streetParam);
        }
        if (StringUtils.isNotEmpty(houseParam)) {
            dto.setHouse(houseParam);
        }
        if (StringUtils.isNotEmpty(apartmentParam)) {
            dto.setApartment(apartmentParam);
        }
        if (StringUtils.isNotEmpty(zipCodeParam)) {
            dto.setZipCOde(zipCodeParam);
        }
        return dto;
    }

    public Email processSendEmailRequest(HttpServletRequest request) {
        String emailAddressesString = request.getParameter("emailAddresses");
        String subject = request.getParameter("subject");
        String body = request.getParameter("email-body");
        ArrayList<String> emailAddresses = new EmailAddressesParser().getEmailAddresses(emailAddressesString);
        ArrayList<Email> emails = new ArrayList<>();
        return new Email(emailAddresses, subject, body);


    }


    public String[] processShowEmailViewRequest(HttpServletRequest request) {
        return request.getParameterValues("isSelected");
    }


}
