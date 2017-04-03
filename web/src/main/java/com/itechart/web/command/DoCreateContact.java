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
public class DoCreateContact implements Command {


    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        FullContactDTO fullContactDTO = null;
        try {
            fullContactDTO = ServiceFactory.getServiceFactory().getRequestProcessingService().processMultipartContactRequest(request);
        } catch (FileSizeException e) {
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
            return null;
        } catch (ValidationException e) {
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        request.getSession().setAttribute("isSearch", false);

        AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();
        try {
            dataService.saveNewContact(fullContactDTO);
        } catch (DataException e) {
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        return (new ShowContactsView()).execute(servlet, request, response);

//
//        //todo
//        ArrayList<MainPageContactDTO> contacts = dataService.getMainPageContactDTO(0,10);
//        request.setAttribute("contacts", contacts);
//        request.getSession().removeAttribute("action");
//        return "/jsp/main.jsp";
    }


}
