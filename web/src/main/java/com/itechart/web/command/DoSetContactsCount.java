package com.itechart.web.command;

import com.itechart.data.dto.SearchDTO;
import com.itechart.web.command.dispatcher.ErrorDispatcher;
import com.itechart.web.command.view.formatter.DisplayingContactsListFormatter;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.exception.DataException;
import com.itechart.web.service.request.processing.AbstractRequestProcessingService;
import com.itechart.web.service.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Aleksandr on 06.04.2017.
 */
public class DoSetContactsCount implements Command {
    private Logger logger = LoggerFactory.getLogger(DoSetContactsCount.class);

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");

        AbstractRequestProcessingService processingService = ServiceFactory.getInstance().getRequestProcessingService();
        int contactsOnPage;
        try {
            contactsOnPage = processingService.processChangeContactsCountRequest(request);
        } catch (ValidationException e) {
            logger.error("Error during request processing: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        request.getSession().setAttribute("contactsOnPage", contactsOnPage);
        SearchDTO searchDTO = null;
        try {
            searchDTO = (SearchDTO) request.getSession().getAttribute("searchDTO");
        } catch (Exception e) {
            logger.error("Error getting attribute from session: {}", e);
        }
        try {
            new DisplayingContactsListFormatter().formContactsList(request, searchDTO);
        } catch (DataException e) {
            logger.error("Error fetching contacts: {}", e);
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return "/jsp/main.jsp";
    }
}
