package com.itechart.web.command.helper;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Aleksandr on 30.03.2017.
 */
public class ContactsViewHelper {

    public void display(HttpServletRequest request) {



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

    }
}
