package com.itechart.web.command;

import com.itechart.web.command.dispatcher.ErrorDispatcher;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.data.exception.DataException;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Command for deleting selected contacts.
 */
public class DoDeleteContact implements Command {
    private static Logger logger = LoggerFactory.getLogger(DoDeleteContact.class);
    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");

        ArrayList<Long> selectedContactsId = null;
        try {
            selectedContactsId = ServiceFactory.getServiceFactory().getRequestProcessingService().processDeleteContactRequest(request);
        } catch (ValidationException e) {
            logger.error("Error during request processing: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();
        try {
            dataService.deleteContacts(selectedContactsId);
        } catch (DataException e) {
            logger.error("Error during deleting contacts: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_NOT_FOUND);
            return null;
        }


//        int contactsOnPage = 10;
        // TODO: 31.03.2017 null check

        request.getSession().setAttribute("isSearch", false);
        return new ShowContactsView().execute(servlet, request, response);

//
//        int pageNumber = (int) request.getSession().getAttribute("page");
//
//        String contactsOnPageParam = request.getParameter("contactsOnPage");
//        String pageNumberParam = request.getParameter("pageNumber");
//        if (request.getSession().getAttribute("contactsOnPage") != null)
//            contactsOnPage = (int) request.getSession().getAttribute("contactsOnPage");
//
//
//        ArrayList<MainPageContactDTO> contacts = dataService.getMainPageContactDTO(pageNumber, contactsOnPage);
//        request.setAttribute("contacts", contacts);
//        return "/jsp/main.jsp";
    }
}
