package com.itechart.web.command;

import com.itechart.data.entity.FullContactEntity;
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
        long id = ServiceFactory.getServiceFactory().getRequestProcessingService().processFetchSingleContactRequest(request);

        FullContactEntity fullContactEntity = ServiceFactory.getServiceFactory().getDataService().getFullContactEntityById(id);

        request.getSession().setAttribute("action", "update");
        request.getSession().setAttribute("id", fullContactEntity.getContact().getContactId());
        request.getSession().setAttribute("contactToUpdate", fullContactEntity);
        request.setAttribute("photo", fullContactEntity.getPhoto());
        request.setAttribute("contact", fullContactEntity.getContact());
        request.setAttribute("address", fullContactEntity.getAddress());
        request.setAttribute("phones", fullContactEntity.getPhones());
        request.setAttribute("attachments", fullContactEntity.getAttachments());
        return "/jsp/contact.jsp";

    }
}
