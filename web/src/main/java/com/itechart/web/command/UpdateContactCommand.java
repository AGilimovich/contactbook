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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class UpdateContactCommand implements Command {
    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;


    public UpdateContactCommand(JdbcContactDao contactDao, JdbcAddressDao addressDao, JdbcPhoneDao phoneDao, JdbcAttachmentDao attachmentDao) {
        this.contactDao = contactDao;
        this.addressDao = addressDao;
        this.phoneDao = phoneDao;
        this.attachmentDao = attachmentDao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        long id = (long) request.getSession().getAttribute("id");

        Contact contact = contactDao.getContactById(id);
        Address address = addressDao.getAddressById(contact.getAddress());
        address.setCountry(request.getParameter("country"));
        address.setCity(request.getParameter("city"));
        address.setStreet(request.getParameter("street"));
        address.setHouse(request.getParameter("house"));
        address.setApartment(request.getParameter("apartment"));
        address.setZipCode(request.getParameter("zipCode"));
        addressDao.update(address);


        contact.setName(request.getParameter("name"));
        contact.setSurname(request.getParameter("surname"));
        contact.setPatronymic(request.getParameter("patronymic"));
        String dateOfBirth = null;
        if (!(dateOfBirth = request.getParameter("dateOfBirth")).isEmpty()) {
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

        String[] phoneParameterValues = request.getParameterValues("phone[]");
        for (String phoneParameter : phoneParameterValues) {
            try {
                Phone phone = PhoneRequestParamParser.parseRequest(phoneParameter);
                phone.setContact(contact.getId());
                phoneDao.save(phone);
            } catch (com.itechart.web.parser.ParseException e) {
                e.printStackTrace();
            }
        }

        // TODO: 14.03.2017 update attachments



        request.getSession().removeAttribute("action");
        request.getSession().removeAttribute("id");

        return (new ShowContactsViewCommand(contactDao, addressDao)).execute(request, response);
    }
}

