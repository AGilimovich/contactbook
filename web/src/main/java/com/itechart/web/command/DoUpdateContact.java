package com.itechart.web.command;

import com.itechart.data.dto.FullContactDTO;
import com.itechart.web.command.dispatcher.ErrorDispatcher;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.data.exception.DataException;
import com.itechart.web.service.request.processing.exception.FileSizeException;
import com.itechart.web.service.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class DoUpdateContact implements Command {
    private Logger logger = LoggerFactory.getLogger(DoUpdateContact.class);

    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");
        FullContactDTO reconstructedFullContactDTO = null;
        try {
            reconstructedFullContactDTO = ServiceFactory.getServiceFactory().getRequestProcessingService().processMultipartContactRequest(request);
        } catch (FileSizeException e) {
            logger.error("Error during request processing: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
            return null;
        } catch (ValidationException e) {
            logger.error("Error during request processing: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        //id of contacted retrieved from session
        if(request.getSession().getAttribute("id")!=null) {
            long contactId = (long) request.getSession().getAttribute("id");
            reconstructedFullContactDTO.getContact().setContactId(contactId);
            AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();
            FullContactDTO contactToUpdate = (FullContactDTO) request.getSession().getAttribute("contactToUpdate");

            try {
                dataService.updateContact(reconstructedFullContactDTO, contactToUpdate);
            } catch (DataException e) {
                logger.error("Error during contact updating: {}", e.getMessage());
                ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
        }
        //remove session attributes
        request.getSession().removeAttribute("contactToUpdate");
        request.getSession().removeAttribute("action");
        request.getSession().removeAttribute("id");

        // TODO: 31.03.
        request.getSession().setAttribute("isSearch", false);


        return (new ShowContactsView()).execute(servlet, request, response);


        // request.setAttribute("contacts", contacts);
        // return "/jsp/main.jsp";
    }

}

