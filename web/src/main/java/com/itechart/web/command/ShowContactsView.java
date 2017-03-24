package com.itechart.web.command;

import com.itechart.data.dto.ContactWithAddressDTO;
import com.itechart.web.service.DataService;
import com.itechart.web.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Implementation of command which shows contacts according to request.
 */
public class ShowContactsView implements Command {

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
        ArrayList<ContactWithAddressDTO> contactWithAddressDTOs = dataService.getContactsWithAddressDTO();
        request.setAttribute("contacts", contactWithAddressDTOs);

        return "/jsp/main.jsp";
    }


}
