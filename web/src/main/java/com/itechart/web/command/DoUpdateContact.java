package com.itechart.web.command;

import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.web.command.errors.ErrorDispatcher;
import com.itechart.web.command.view.formatter.DisplayingContactsListFormatter;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.data.exception.DataException;
import com.itechart.web.service.request.processing.exception.FileSizeException;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command executed on request to update contact.
 */
public class DoUpdateContact implements Command {
    private Logger logger = LoggerFactory.getLogger(DoUpdateContact.class);

    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");
        FullContactDTO reconstructedFullContactDTO = null;
        try {
            reconstructedFullContactDTO = ServiceFactory.getInstance().getRequestProcessingService().processMultipartContactRequest(request);
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
        if (request.getSession().getAttribute("id") != null) {
            try {
                long contactId = (long) request.getSession().getAttribute("id");
                FullContactDTO contactToUpdate = (FullContactDTO) request.getSession().getAttribute("contactToUpdate");
                reconstructedFullContactDTO.getContact().setContactId(contactId);
                AbstractDataService dataService = ServiceFactory.getInstance().getDataService();
                try {
                    dataService.updateContact(reconstructedFullContactDTO, contactToUpdate);
                } catch (DataException e) {
                    logger.error("Error during contact updating: {}", e.getMessage());
                    ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return null;
                }

            } catch (Exception e) {
                logger.error("Error retrieving session attribute:{}", e.getMessage());
                ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
        }
        //remove session attributes
        request.getSession().removeAttribute("contactToUpdate");
        request.getSession().removeAttribute("action");
        request.getSession().removeAttribute("id");

        SearchDTO searchDTO = null;
        if (StringUtils.isNotBlank(request.getParameter("pageNumber"))) {
            try {
                searchDTO = (SearchDTO) request.getSession().getAttribute("searchDTO");
            } catch (Exception e) {
                logger.error("Error getting attribute from session: {}", e);
            }
        }
        try {
            new DisplayingContactsListFormatter().formContactsList(request, searchDTO);
        } catch (DataException e) {
            logger.error("Error during fetching contacts: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return "/jsp/main.jsp";


    }

}

