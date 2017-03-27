package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.FullContactDTO;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 14.03.2017.
 */
public class DoUpdateContact implements Command {


    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        FullContactDTO reconstructedFullContactDTO = ServiceFactory.getServiceFactory().getRequestProcessingService().processContactRequest(request);
        //id of contacted retrieved from session
        long contactId = (long) request.getSession().getAttribute("id");
        reconstructedFullContactDTO.getContact().setContactId(contactId);
        AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();
        FullContactDTO contactToUpdate = (FullContactDTO) request.getSession().getAttribute("contactToUpdate");
        dataService.updateContact(reconstructedFullContactDTO, contactToUpdate);

        //remove session attributes
        request.getSession().removeAttribute("contactToUpdate");
        request.getSession().removeAttribute("action");
        request.getSession().removeAttribute("id");
        ArrayList<MainPageContactDTO> contacts = dataService.getMainPageContactDTO();
        request.setAttribute("contacts", contacts);
        return "/jsp/main.jsp";
    }

}

