package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dto.ContactWithAddressDTO;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Implementation of command which shows contacts according to request.
 */
public class ShowContacts implements Command {
    private JdbcContactDao contactDao;
    private JdbcAddressDao addressDao;

    public ShowContacts(JdbcContactDao contactDao, JdbcAddressDao addressDao) {
        this.contactDao = contactDao;
        this.addressDao = addressDao;
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ArrayList<Contact> contactsDTOs = contactDao.getAll();
        ArrayList<ContactWithAddressDTO> contactWithAddressDTOs = new ArrayList<>();
        for (Contact c : contactsDTOs) {
            Address a = addressDao.getAddressById(c.getAddress());
            ContactWithAddressDTO contactWithAddressDTO = new ContactWithAddressDTO(c, a);
            contactWithAddressDTOs.add(contactWithAddressDTO);
        }

        request.setAttribute("contacts", contactWithAddressDTOs);

        return "/jsp/main.jsp";
    }
}
