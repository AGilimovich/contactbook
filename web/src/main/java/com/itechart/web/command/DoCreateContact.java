package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.FullContactDTO;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.request.processing.FileSizeException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class DoCreateContact implements Command {


    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
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
        }

        request.getSession().setAttribute("isSearch", false);


        return (new ShowContactsView()).execute(servlet, request, response);

//        AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();
//        dataService.saveNewContact(fullContactDTO);
//        //todo
//        ArrayList<MainPageContactDTO> contacts = dataService.getMainPageContactDTO(0,10);
//        request.setAttribute("contacts", contacts);
//        request.getSession().removeAttribute("action");
//        return "/jsp/main.jsp";
    }


}
