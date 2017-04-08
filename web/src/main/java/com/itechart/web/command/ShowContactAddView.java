package com.itechart.web.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command executed on request to show contact creating view.
 */
public class ShowContactAddView implements Command {

    private Logger logger = LoggerFactory.getLogger(ShowContactAddView.class);

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");
        //current action: creating new contact (can have another value - edit)
        request.getSession().setAttribute("action", "save");

        return "/jsp/contact.jsp";

    }
}
