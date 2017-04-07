package com.itechart.web.command;

import com.itechart.web.command.dispatcher.ErrorDispatcher;
import com.itechart.web.service.ServiceFactory;
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
public class DoChangeContactsCount implements Command {
    private Logger logger = LoggerFactory.getLogger(DoChangeContactsCount.class);

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");

        AbstractRequestProcessingService processingService = ServiceFactory.getServiceFactory().getRequestProcessingService();
        int contactsOnPage;
        try {
            contactsOnPage = processingService.processChangeContactsCountRequest(request);
        } catch (ValidationException e) {
            logger.error("Error during request processing: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }


        request.getSession().setAttribute("contactsOnPage", contactsOnPage);

        return null;
    }
}