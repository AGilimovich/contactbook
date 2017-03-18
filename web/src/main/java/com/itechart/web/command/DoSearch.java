package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.entity.Contact;
import com.itechart.web.parser.DateTimeParser;

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
        else gender = null;

        String familyStatusParam = request.getParameter("familyStatus");
        Contact.FamilyStatus familyStatus = null;
        if (familyStatusParam != null && !familyStatusParam.equals("any"))
            familyStatus = Contact.FamilyStatus.valueOf(familyStatusParam.toUpperCase());
        else familyStatus = null;
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


        ArrayList<Contact> contacts = contactDao.findContactsByFields(surname, name, patronymic, fromDateOfBirth, toDateOfBirth, gender, familyStatus, citizenship, country, city, street, house, apartment, zipCode);
        return new ShowContacts(contactDao, addressDao).executeSearchResult(servlet, request, response, contacts);
    }
}
