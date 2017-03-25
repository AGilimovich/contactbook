package com.itechart.web.service.request.processing;

import com.itechart.data.dto.FullContact;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;
import com.itechart.web.properties.PropertiesManager;
import com.itechart.web.service.DataService;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.email.Email;
import com.itechart.web.service.email.EmailAddressesParser;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Class with a set of methods designed for processing different requests.
 */
public class RequestProcessingService {

    public FullContact processContactRequest(HttpServletRequest request) {
        MultipartRequestHandler handler = new MultipartRequestHandler();
        handler.handle(request);
        //get map of form field names and corresponding values
        Map<String, String> formFields = handler.getFormFields();
        //get map of field names and corresponding file parts
        Map<String, FileItem> fileParts = handler.getFileParts();

        FilePartWriter writer = new FilePartWriter(PropertiesManager.FILE_PATH());
        //store file parts and get map of field names and file names
        Map<String, String> storedFiles = writer.writeFileParts(fileParts);

        FullContactBuilder builder = new FullContactBuilder(formFields, storedFiles);

        return builder.getFullContact();
    }

    public String[] processDeleteContactRequest(HttpServletRequest request) {
        return request.getParameterValues("isSelected");

    }


    public FullContact processFetchSingleContactRequest(HttpServletRequest request) {
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
        long id = Long.valueOf(request.getParameter("id"));
        Contact contact = dataService.getContactById(id);
        Address address = dataService.getAddressById(contact.getAddress());
        ArrayList<Phone> phones = dataService.getAllPhonesForContact(id);
        ArrayList<Attachment> attachments = dataService.getAllAttachmentsForContact(id);
        return new FullContact(contact, address, phones, attachments);

    }

    public SearchDTO processSearchContactsRequest(HttpServletRequest request) {
        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String patronymic = request.getParameter("patronymic");
        String genderParam = request.getParameter("gender");
        Contact.Gender gender = null;
        if (genderParam != null && !genderParam.equals("any"))
            gender = Contact.Gender.valueOf(genderParam.toUpperCase());


        String familyStatusParam = request.getParameter("familyStatus");
        Contact.FamilyStatus familyStatus = null;
        if (familyStatusParam != null && !familyStatusParam.equals("any"))
            familyStatus = Contact.FamilyStatus.valueOf(familyStatusParam.toUpperCase());

        String fromDateParam = request.getParameter("fromDate");
        Date fromDateOfBirth = DateTimeParser.parseDate(fromDateParam, "yyyy-MM-dd");

        String toDateParam = request.getParameter("toDate");
        Date toDateOfBirth = DateTimeParser.parseDate(toDateParam, "yyyy-MM-dd");

        String citizenship = request.getParameter("citizenship");

        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String house = request.getParameter("house");
        String apartment = request.getParameter("apartment");
        String zipCode = request.getParameter("zipCode");

        SearchDTO dto = new SearchDTO();
        dto.setSurname(surname);
        dto.setName(name);
        dto.setPatronymic(patronymic);
        dto.setFromDate(fromDateOfBirth);
        dto.setToDate(toDateOfBirth);
        dto.setGender(gender);
        dto.setFamilyStatus(familyStatus);
        dto.setCitizenship(citizenship);
        dto.setCountry(country);
        dto.setCity(city);
        dto.setStreet(street);
        dto.setHouse(house);
        dto.setApartment(apartment);
        dto.setZipCOde(zipCode);

        return dto;


    }

    public Email processSendEmailRequest(HttpServletRequest request) {
        String emailAddressesString = request.getParameter("emailAddresses");
        String subject = request.getParameter("subject");
        String body = request.getParameter("email-body");
        ArrayList<String> emailAddresses = new EmailAddressesParser().getEmailAddresses(emailAddressesString);

        return new Email(emailAddresses, subject, body);


    }

    public byte[] processGetFileRequest(HttpServletRequest request) {
        String FILE_PATH = PropertiesManager.FILE_PATH();

        String photoId = request.getParameter("id");
        if (!photoId.isEmpty()) {
            Path path = Paths.get(FILE_PATH + "\\" + photoId);
            byte[] photo = null;
            try {
                photo = Files.readAllBytes(path);
                return photo;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String[] processShowEmailViewRequest(HttpServletRequest request) {
        return request.getParameterValues("isSelected");
    }


}