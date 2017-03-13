package com.itechart.web.command;

import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dto.ContactDTO;
import com.itechart.data.entity.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Implementation of command which shows contacts according to request.
 */
public class ShowContactsCommand implements Command {
    private JdbcContactDao dao;

    public ShowContactsCommand(JdbcContactDao dao) {
        this.dao = dao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ArrayList<ContactDTO> contacts = dao.getAllDto();
        request.setAttribute("contacts", contacts);

        return "/jsp/main.jsp";
    }
}
