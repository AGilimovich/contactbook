package com.itechart.web.command;

import com.itechart.data.dto.FullContactDTO;
import com.itechart.web.command.dispatcher.ErrorDispatcher;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.exception.DataException;
import com.itechart.web.service.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for creating new contact.
 */
public class ShowContactEditView implements Command {

    private Logger logger = LoggerFactory.getLogger(ShowContactEditView.class);

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");
        long id = 0;
        try {
            id = ServiceFactory.getInstance().getRequestProcessingService().processFetchSingleContactRequest(request);
        } catch (ValidationException e) {
            logger.error("Error during request processing: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        FullContactDTO fullContactDTO = null;
        try {
            fullContactDTO = ServiceFactory.getInstance().getDataService().getFullContactById(id);
        } catch (DataException e) {
            logger.error("Error during fetching contact: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        request.getSession().setAttribute("contactToUpdate", fullContactDTO);
        request.getSession().setAttribute("action", "update");
        request.getSession().setAttribute("id", fullContactDTO.getContact().getContactId());
        request.setAttribute("photo", fullContactDTO.getPhoto());
        request.setAttribute("contact", fullContactDTO.getContact());
        request.setAttribute("address", fullContactDTO.getAddress());
        request.setAttribute("phones", fullContactDTO.getPhones());
        request.setAttribute("attachments", fullContactDTO.getAttachments());
        return "/jsp/contact.jsp";

    }
}
