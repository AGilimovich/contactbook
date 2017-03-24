package com.itechart.web.command;

import com.itechart.data.dto.FullContact;
import com.itechart.web.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for creating new contact.
 */
public class ShowContactEditView implements Command {


    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        FullContact fullContact = ServiceFactory.getServiceFactory().getRequestProcessingService().processFetchSingleContactRequest(request);


        request.getSession().setAttribute("action", "update");
        request.getSession().setAttribute("id", fullContact.getContact().getContactId());
        request.setAttribute("contact", fullContact.getContact());
        request.setAttribute("address", fullContact.getAddress());
        request.setAttribute("phones", fullContact.getPhones());
        request.setAttribute("attachments", fullContact.getAttachments());
        return "/jsp/contact.jsp";

    }
}
