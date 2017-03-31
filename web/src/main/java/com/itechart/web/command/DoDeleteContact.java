package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.web.service.data.AbstractDataService;
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
        AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();
        if (selectedContactsId != null) {
            for (String c : selectedContactsId) {
                long contactId = Long.valueOf(c);
                dataService.deleteContact(contactId);
            }
        }

//        int contactsOnPage = 10;
        // TODO: 31.03.2017 null check

        request.getSession().setAttribute("isSearch", false);
        return new ShowContactsView().execute(servlet, request, response);

//
//        int pageNumber = (int) request.getSession().getAttribute("page");
//
//        String contactsOnPageParam = request.getParameter("contactsOnPage");
//        String pageNumberParam = request.getParameter("pageNumber");
//        if (request.getSession().getAttribute("contactsOnPage") != null)
//            contactsOnPage = (int) request.getSession().getAttribute("contactsOnPage");
//
//
//        ArrayList<MainPageContactDTO> contacts = dataService.getMainPageContactDTO(pageNumber, contactsOnPage);
//        request.setAttribute("contacts", contacts);
//        return "/jsp/main.jsp";
    }
}
