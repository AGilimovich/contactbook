package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;
import com.itechart.web.parser.PhoneRequestParamParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class DoUpdateContact implements Command {
    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;


    public DoUpdateContact(JdbcContactDao contactDao, JdbcAddressDao addressDao, JdbcPhoneDao phoneDao, JdbcAttachmentDao attachmentDao) {
        this.contactDao = contactDao;
        this.addressDao = addressDao;
        this.phoneDao = phoneDao;
        this.attachmentDao = attachmentDao;
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        long id = (long) request.getSession().getAttribute("id");
        Contact contact = contactDao.getContactById(id);
        Address address = addressDao.getAddressById(contact.getAddress());
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String house = request.getParameter("house");
        String apartment = request.getParameter("apartment");
        String zipCode = request.getParameter("zipCode");
        //if address was not created for contact. In this implementation address created every time new contact created even when address fields is empty
        if (address == null) {
            address = new Address();
            address.setCountry(country);
            address.setCity(city);
            address.setStreet(street);
            address.setHouse(house);
            address.setApartment(apartment);
            address.setZipCode(zipCode);
            long addressId = addressDao.save(address);
            contact.setAddress(addressId);
        } else {
            address.setCountry(request.getParameter("country"));
            address.setCity(request.getParameter("city"));
            address.setStreet(request.getParameter("street"));
            address.setHouse(request.getParameter("house"));
            address.setApartment(request.getParameter("apartment"));
            address.setZipCode(request.getParameter("zipCode"));
            addressDao.update(address);
        }

        contact.setName(request.getParameter("name"));
        contact.setSurname(request.getParameter("surname"));
        contact.setPatronymic(request.getParameter("patronymic"));
        String dateOfBirth = null;
        if ((dateOfBirth = request.getParameter("dateOfBirth")) != null) {
            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
            try {
                contact.setDateOfBirth(format.parse(dateOfBirth));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (request.getParameter("gender") != null) {
            contact.setGender(Contact.Gender.valueOf(request.getParameter("gender").toUpperCase()));
        }
        contact.setCitizenship(request.getParameter("citizenship"));
        if (request.getParameter("familyStatus") != null) {
            contact.setFamilyStatus(Contact.FamilyStatus.valueOf(request.getParameter("familyStatus").toUpperCase()));
        }
        contact.setWebsite(request.getParameter("website"));
        contact.setEmail(request.getParameter("email"));
        contact.setPlaceOfWork(request.getParameter("placeOfWork"));
        contact.setPhoto(request.getParameter("photo"));

        contactDao.update(contact);
        phoneDao.deleteForUser(contact.getId());

        String[] phoneParameterValues = request.getParameterValues("phone");
        if (phoneParameterValues != null) {
            for (String phoneParameter : phoneParameterValues) {
                try {
                    Phone phone = PhoneRequestParamParser.parseRequest(phoneParameter);
                    phone.setContact(contact.getId());
                    phoneDao.save(phone);
                } catch (com.itechart.web.parser.ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        // TODO: 14.03.2017 update attachments


        request.getSession().removeAttribute("action");
        request.getSession().removeAttribute("id");

        return (new ShowContacts(contactDao, addressDao)).execute(servlet, request, response);
    }
}

