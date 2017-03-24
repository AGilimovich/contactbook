package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Contact;
import com.itechart.web.service.DateTimeParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aleksandr on 18.03.2017.
 */
public class DoSearch implements Command {

    private JdbcContactDao contactDao;
    private JdbcAddressDao addressDao;

    public DoSearch(JdbcContactDao contactDao, JdbcAddressDao addressDao) {
        this.contactDao = contactDao;
        this.addressDao = addressDao;
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
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


        ArrayList<Contact> contacts = contactDao.findContactsByFields(dto);
        return new ShowContacts(contactDao, addressDao).executeSearchResult(servlet, request, response, contacts);
    }
}
