package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.FullContactDTO;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.AbstractDataService;

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
        FullContactDTO fullContactDTO = ServiceFactory.getServiceFactory().getRequestProcessingService().processContactRequest(request);
        AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();
        dataService.saveNewContact(fullContactDTO);
        ArrayList<MainPageContactDTO> contacts = dataService.getMainPageContactDTO();
        request.setAttribute("contacts", contacts);
        request.getSession().removeAttribute("action");
        return "/jsp/main.jsp";
    }


}
