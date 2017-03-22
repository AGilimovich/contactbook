package com.itechart.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Invokes contact searching view.
 */
public class ShowSearch implements Command {


    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        return "/jsp/search.jsp";
    }
}
