package com.itechart.web.command;

import com.itechart.data.dao.ContactDao;
import com.itechart.data.entity.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Implementation of command which shows contacts according to request.
 */
public class ShowContactsCommand implements Command {
    private ContactDao dao;

    public ShowContactsCommand(ContactDao dao) {
        this.dao = dao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ArrayList<Contact> contacts = dao.getAll();
        request.setAttribute("contacts", contacts);
        return "/jsp/main.jsp";
    }
}
