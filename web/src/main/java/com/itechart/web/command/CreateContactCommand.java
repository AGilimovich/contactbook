package com.itechart.web.command;

import com.itechart.data.dao.JdbcContactDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for creating new contact.
 */
public class CreateContactCommand implements Command {
    private JdbcContactDao dao;

    public CreateContactCommand(JdbcContactDao dao) {
        this.dao = dao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        return "/jsp/add.jsp";

    }
}
