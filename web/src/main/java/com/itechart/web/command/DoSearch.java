package com.itechart.web.command;

import com.itechart.data.dto.SearchDTO;
import com.itechart.web.command.errors.ErrorDispatcher;
import com.itechart.web.command.view.formatter.DisplayingContactsListFormatter;
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
 * Command executed on request to search contacts.
 */
public class DoSearch implements Command {
    private static Logger logger = LoggerFactory.getLogger(DoSearch.class);

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");
        SearchDTO dto = null;
        try {
            dto = ServiceFactory.getInstance().getRequestProcessingService().processSearchContactsRequest(request);
        } catch (ValidationException e) {
            logger.error("Error during request processing: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        request.getSession().setAttribute("searchDTO", dto);
        try {
            new DisplayingContactsListFormatter().formContactsList(request, dto);
        } catch (DataException e) {
            logger.error("Error during fetching contacts: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return "/jsp/main.jsp";
    }
}
