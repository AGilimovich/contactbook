package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.web.service.data.DataService;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.IDataService;

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
        //get array of selected contact id's
        String[] selectedContactsId = ServiceFactory.getServiceFactory().getRequestProcessingService().processDeleteContactRequest(request);
        //delete all selected contacts
        IDataService dataService = ServiceFactory.getServiceFactory().getDataService();
        if (selectedContactsId != null) {
            for (String c : selectedContactsId) {
                long contactId = Long.valueOf(c);
                dataService.deleteContact(contactId);
            }
        }
        //get contact DTO object for displaying on the main page
        ArrayList<MainPageContactDTO> contacts = dataService.getMainPageContactDTOs();
        request.setAttribute("contacts",contacts);
        return "/jsp/main.jsp";
    }
}
