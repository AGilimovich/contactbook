package com.itechart.web.command;

import com.itechart.web.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for creating new contact.
 */
public class ShowContactEdit implements Command {


    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ServiceFactory.getServiceFactory().getRequestProcessingService().processFetchingSingleContactRequest(request);
        return "/jsp/contact.jsp";

    }
}
