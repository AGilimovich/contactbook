package com.itechart.web.command;

import com.itechart.data.dto.FullContactDTO;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.request.processing.FileSizeException;
import com.itechart.web.service.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class DoCreateContact implements Command {
    private static final Logger log = LoggerFactory.getLogger(DoCreateContact.class);


    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        log.info("execute: {}", request);

        FullContactDTO fullContactDTO = null;
        try {
            fullContactDTO = ServiceFactory.getServiceFactory().getRequestProcessingService().processMultipartContactRequest(request);
        } catch (FileSizeException e) {
            try {
                response.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
                return null;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ValidationException e) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        request.getSession().setAttribute("isSearch", false);

        AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();
        dataService.saveNewContact(fullContactDTO);
        return (new ShowContactsView()).execute(servlet, request, response);

//
//        //todo
//        ArrayList<MainPageContactDTO> contacts = dataService.getMainPageContactDTO(0,10);
//        request.setAttribute("contacts", contacts);
//        request.getSession().removeAttribute("action");
//        return "/jsp/main.jsp";
    }


}
