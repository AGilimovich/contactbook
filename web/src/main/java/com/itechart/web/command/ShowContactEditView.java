package com.itechart.web.command;

import com.itechart.data.dto.FullContactDTO;
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

        FullContactDTO fullContactDTO = ServiceFactory.getServiceFactory().getDataService().getFullContactDTOById(id);

        request.getSession().setAttribute("action", "update");
        request.getSession().setAttribute("id", fullContactDTO.getContact().getContactId());
        request.getSession().setAttribute("contactToUpdate", fullContactDTO);
        request.setAttribute("photo", fullContactDTO.getPhoto());
        request.setAttribute("contact", fullContactDTO.getContact());
        request.setAttribute("address", fullContactDTO.getAddress());
        request.setAttribute("phones", fullContactDTO.getPhones());
        request.setAttribute("attachments", fullContactDTO.getAttachments());
        return "/jsp/contact.jsp";

    }
}
