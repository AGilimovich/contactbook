package com.itechart.web.service.request.processing;

import com.itechart.data.dto.ContactWithAddressDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;
import com.itechart.web.properties.PropertiesManager;
import com.itechart.web.service.DataService;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.email.EmailAddressesParser;
import com.itechart.web.service.email.EmailService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.mail.EmailException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Aleksandr on 24.03.2017.
 */
public class RequestProcessingService {

    public void processCreationRequest(HttpServletRequest request) {
        MultipartRequestHandler handler = new MultipartRequestHandler();
        handler.handle(request);
        //get map of form field names and corresponding values
        Map<String, String> formFields = handler.getFormFields();
        //get map of field names and corresponding file parts
        Map<String, FileItem> fileParts = handler.getFileParts();

        FilePartWriter writer = new FilePartWriter(PropertiesManager.FILE_PATH());
        //store file parts and get map of field names and file names
        Map<String, String> storedFiles = writer.writeFileParts(fileParts);

        ObjectsFromRequestFactory parser = new ObjectsFromRequestFactory(formFields, storedFiles);
        Contact contact = parser.getContact();
        Address address = parser.getAddress();
        ArrayList<Phone> phones = parser.getNewPhones();
        ArrayList<Attachment> attachments = parser.getNewAttachments();
        // TODO: 24.03.2017
        ServiceFactory.getServiceFactory().getDataService().saveNewContact(contact, address, phones, attachments);

    }

    public void processDeletingRequest(HttpServletRequest request) {
        String[] selectedContactsId = request.getParameterValues("isSelected");
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
        if (selectedContactsId != null) {
            for (String c : selectedContactsId) {
                long contactId = Long.valueOf(c);
                dataService.deleteContact(contactId);
            }
        }
    }

    public void processUpdatingRequest(HttpServletRequest request) {
        MultipartRequestHandler handler = new MultipartRequestHandler();
        handler.handle(request);
        //get map of form field names and corresponding values
        Map<String, String> formFields = handler.getFormFields();
        //get map of field names and corresponding file parts
        Map<String, FileItem> fileParts = handler.getFileParts();

        FilePartWriter writer = new FilePartWriter(PropertiesManager.FILE_PATH());
        //store file parts and get map of field names and file names
        Map<String, String> storedFiles = writer.writeFileParts(fileParts);

        ObjectsFromRequestFactory parser = new ObjectsFromRequestFactory(formFields, storedFiles);
        long contactId = (long) request.getSession().getAttribute("id");
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
        Contact contactToUpdate = dataService.getContactById(contactId);
        Address addressToUpdate = dataService.getAddressById(contactToUpdate.getAddress());
        contactToUpdate.update(parser.getContact());
        addressToUpdate.update(parser.getAddress());


        ArrayList<Phone> newPhones = parser.getNewPhones();
        ArrayList<Phone> updatedPhones = parser.getUpdatedPhones();
        ArrayList<Phone> deletedPhones = parser.getDeletedPhones();

        ArrayList<Attachment> newAttachments = parser.getNewAttachments();
        ArrayList<Attachment> updatedAttachments = parser.getUpdatedAttachments();
        ArrayList<Attachment> deletedAttachments = parser.getDeletedAttachments();


        dataService.updateContact(contactToUpdate, addressToUpdate, newPhones, newAttachments, updatedPhones, updatedAttachments, deletedPhones, deletedAttachments);


        //remove session attributes
        request.getSession().removeAttribute("action");
        request.getSession().removeAttribute("id");
    }

    public void processFetchingAllContactsRequest(HttpServletRequest request) {
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
        ArrayList<ContactWithAddressDTO> contactWithAddressDTOs = dataService.getContactsWithAddressDTO();
        request.setAttribute("contacts", contactWithAddressDTOs);
    }

    public void processFetchingSingleContactRequest(HttpServletRequest request) {
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
        long id = Long.valueOf(request.getParameter("id"));
        Contact contact = dataService.getContactById(id);
        Address address = dataService.getAddressById(contact.getAddress());
        ArrayList<Phone> phones = dataService.getAllPhonesForContact(id);
        ArrayList<Attachment> attachments = dataService.getAllAttachmentsForContact(id);
        request.getSession().setAttribute("action", "update");
        request.getSession().setAttribute("id", id);
        request.setAttribute("contact", contact);
        request.setAttribute("address", address);
        request.setAttribute("phones", phones);
        request.setAttribute("attachments", attachments);
    }

    public void processSearchRequest(HttpServletRequest request) {
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
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
        ArrayList<Contact> contacts = dataService.getContactsByFields(dto);
        ArrayList<ContactWithAddressDTO> contactWithAddressDTOs = new ArrayList<>();

        for (Contact contact : contacts) {
            Address address = dataService.getAddressById(contact.getAddress());
            ContactWithAddressDTO contactWithAddressDTO = new ContactWithAddressDTO(contact, address);
            contactWithAddressDTOs.add(contactWithAddressDTO);
        }

        request.setAttribute("contacts", contactWithAddressDTOs);


    }

    public void processEmailSendingRequest(HttpServletRequest request) {
        String emailAddressesString = request.getParameter("emailAddresses");
        String subject = request.getParameter("subject");
        String body = request.getParameter("emailBody");
//        Email configuration
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String hostName = bundle.getString("HOST_NAME");
        int SMTPPort = Integer.valueOf(bundle.getString("PORT"));
        String userName = bundle.getString("USER_NAME");
        String password = bundle.getString("PASSWORD");
        String emailFrom = bundle.getString("EMAIL");


        EmailService emailService = new EmailService(hostName, SMTPPort, userName, password, emailFrom);

        ArrayList<String> emailAddresses = new EmailAddressesParser().getEmailAddresses(emailAddressesString);
        for (String email : emailAddresses) {
            try {
                emailService.sendEmail(email, subject, body);
            } catch (EmailException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] processFileRequest(HttpServletRequest request) {
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


}
