package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.FullContactDTO;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.DataService;
import com.itechart.web.service.data.IDataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class DoCreateContact implements Command {

    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        //process request: construct DTO object from request
        FullContactDTO fullContactDTO =  ServiceFactory.getServiceFactory().getRequestProcessingService().processContactRequest(request);
        //save constructed object
        IDataService dataService = ServiceFactory.getServiceFactory().getDataService();
        dataService.saveNewContact(fullContactDTO);
        //get contact DTO object for displaying on the main page
        ArrayList<MainPageContactDTO> contacts = dataService.getMainPageContactDTOs();
        request.setAttribute("contacts",contacts);
        request.getSession().removeAttribute("action");
        return "/jsp/main.jsp";
    }


}
