package com.itechart.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class ShowContactAdd implements Command {


    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        //string in session current action: creating new contact (can have another value - edit)
        request.getSession().setAttribute("action", "save");

        return "/jsp/contact.jsp";

    }
}
