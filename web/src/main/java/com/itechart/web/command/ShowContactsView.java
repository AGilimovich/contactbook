package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.ServiceFactory;
import org.apache.commons.lang3.StringUtils;

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
        if (request.getSession().getAttribute("isSearch") != null) {
            if ((boolean) request.getSession().getAttribute("isSearch")) {
                if (StringUtils.isNotEmpty(request.getParameter("search"))) {
                    if (!request.getParameter("search").equals("false"))
                        return new DoSearch().execute(servlet, request, response);
                } else {
                    return new DoSearch().execute(servlet, request, response);
                }
            }
        }

        request.getSession().setAttribute("isSearch", false);
        //default values
        int pageNumber = 0;
        int contactsOnPage = 10;

        String contactsOnPageParam = request.getParameter("contactsOnPage");
        String pageNumberParam = request.getParameter("pageNumber");
        if (StringUtils.isEmpty(contactsOnPageParam)) {
            if (request.getSession().getAttribute("contactsOnPage") != null)
                contactsOnPage = (int) request.getSession().getAttribute("contactsOnPage");
        } else {
            //if displaying contacts count was changed
            contactsOnPage = Integer.valueOf(contactsOnPageParam);
            request.getSession().setAttribute("contactsOnPage", contactsOnPage);
        }
        if (StringUtils.isNotEmpty(pageNumberParam)) {
            pageNumber = Integer.valueOf(pageNumberParam);
        }
        AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();

        ArrayList<MainPageContactDTO> mainPageContactDTOs = dataService.getMainPageContactDTO(pageNumber, contactsOnPage);
        int contactsInDBCount = dataService.getContactsCount();
        int pagesCount = (int) Math.ceil(contactsInDBCount / contactsOnPage);
        request.getSession().setAttribute("pageNumber", pageNumber);
        request.setAttribute("contacts", mainPageContactDTOs);
        request.setAttribute("pagesCount", pagesCount);


        return "/jsp/main.jsp";
    }


}
