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

        //default values
        int page = 0;
        int count = 10;
        String countParam = request.getParameter("count");
        String pageParam = request.getParameter("page");
        if (StringUtils.isEmpty(countParam)) {
            if (request.getSession().getAttribute("count") != null)
                count = (int) request.getSession().getAttribute("count");
        } else {
            //if displaying contacts count was changed
            count = Integer.valueOf(countParam);
            request.getSession().setAttribute("count", count);
        }
        if (StringUtils.isNotEmpty(pageParam)) {
            page = Integer.valueOf(pageParam);
        }


        AbstractDataService dataService = ServiceFactory.getServiceFactory().getDataService();
        ArrayList<MainPageContactDTO> mainPageContactDTOs = dataService.getMainPageContactDTO(page, count);
        int contactsInDBCount = dataService.getContactsCount();

        int pagesCount = (int) Math.ceil(contactsInDBCount / count);
        request.getSession().setAttribute("page", page);
        request.setAttribute("contacts", mainPageContactDTOs);
        request.setAttribute("pages", pagesCount);
        return "/jsp/main.jsp";
    }


}
