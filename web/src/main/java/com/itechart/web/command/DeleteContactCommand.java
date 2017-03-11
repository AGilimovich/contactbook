package com.itechart.web.command;

import com.itechart.data.dao.ContactDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for deleting selected contacts.
 */
public class DeleteContactCommand implements Command {
    private ContactDao dao;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String[] selectedContactsId = request.getParameterValues("isSelected");
        return "/jsp/main.jsp";
    }
}
