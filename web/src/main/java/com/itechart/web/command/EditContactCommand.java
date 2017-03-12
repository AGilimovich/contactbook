package com.itechart.web.command;

import com.itechart.data.dao.ContactDao;
import com.itechart.data.entity.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Command for editing existing contact.
 */
public class EditContactCommand implements Command {
    private ContactDao dao = new ContactDao();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ArrayList<Contact> contacts = dao.getAll();

        request.setAttribute("contacts", contacts);
        return "/jsp/contact.jsp";

    }
}
