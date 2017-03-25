package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.FullContactDTO;
import com.itechart.web.service.data.DataService;
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
        FullContactDTO receivedFullContactDTO = ServiceFactory.getServiceFactory().getRequestProcessingService().processContactRequest(request);
        //id of contacted retrieved from session
        long contactId = (long) request.getSession().getAttribute("id");
        receivedFullContactDTO.getContact().setContactId(contactId);
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
        dataService.updateContact(receivedFullContactDTO);

        //remove session attributes
        request.getSession().removeAttribute("action");
        request.getSession().removeAttribute("id");
        ArrayList<MainPageContactDTO> contacts = ServiceFactory.getServiceFactory().getDataService().getContactsWithAddressDTO();
        request.setAttribute("contacts", contacts);
        return "/jsp/main.jsp";
    }

}

