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
 * Command for deleting selected contacts.
 */
public class DoDeleteContact implements Command {


    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String[] selectedContactsId = ServiceFactory.getServiceFactory().getRequestProcessingService().processDeleteContactRequest(request);
        DataService dataService = ServiceFactory.getServiceFactory().getDataService();
        if (selectedContactsId != null) {
            for (String c : selectedContactsId) {
                long contactId = Long.valueOf(c);
                dataService.deleteContact(contactId);
            }
        }
        ArrayList<ContactWithAddressDTO> contacts = ServiceFactory.getServiceFactory().getDataService().getContactsWithAddressDTO();
        request.setAttribute("contacts",contacts);
        return "/jsp/main.jsp";
    }
}
