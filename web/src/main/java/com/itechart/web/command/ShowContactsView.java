package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.web.command.dispatcher.ErrorDispatcher;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.exception.DataException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Implementation of command which shows contacts according to request.
 */
public class ShowContactsView implements Command {
    private static final Logger log = LoggerFactory.getLogger(DoCreateContact.class);

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        // TODO: 03.04.2017 logging
        log.debug("111{}", request);
        log.info("1{}", request);


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

        int contactsInDBCount = 0;
        ArrayList<MainPageContactDTO> mainPageContactDTOs = null;
        try {
            mainPageContactDTOs = dataService.getMainPageContactDTO(pageNumber, contactsOnPage);
            contactsInDBCount = dataService.getContactsCount();
        } catch (DataException e) {
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }


        int pagesCount = 1;
        if (contactsOnPage != 0) {
            pagesCount = (int) Math.ceil((double) contactsInDBCount / contactsOnPage);
        }
        request.getSession().removeAttribute("searchDTO");
        request.getSession().setAttribute("pageNumber", pageNumber);
        request.setAttribute("contacts", mainPageContactDTOs);
        request.setAttribute("pagesCount", pagesCount);


        return "/jsp/main.jsp";
    }


}
