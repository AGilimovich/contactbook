package com.itechart.web.command;

import com.itechart.web.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementation of command which shows contacts according to request.
 */
public class ShowContacts implements Command {

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ServiceFactory.getServiceFactory().getRequestProcessingService().processFetchingAllContactsRequest(request);

        return "/jsp/main.jsp";
    }


}
