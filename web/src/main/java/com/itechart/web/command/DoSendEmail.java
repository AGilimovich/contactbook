package com.itechart.web.command;

import com.itechart.data.dto.SearchDTO;
import com.itechart.web.command.dispatcher.ErrorDispatcher;
import com.itechart.web.command.view.formatter.DisplayingContactsListFormatter;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.exception.DataException;
import com.itechart.web.service.email.AbstractEmailingService;
import com.itechart.web.service.email.Email;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 18.03.2017.
 */
public class DoSendEmail implements Command {
    private static Logger logger = LoggerFactory.getLogger(DoSendEmail.class);

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        logger.info("Execute command");

        ArrayList<Email> emails = null;
        AbstractEmailingService emailingService = ServiceFactory.getInstance().getEmailService();
        try {
            emails = ServiceFactory.getInstance().getRequestProcessingService().processSendEmailRequest(request);
            for (Email email: emails){
                try {
                    emailingService.sendEmail(email.getEmailAddress(), email.getSubject(), email.getBody());
                } catch (EmailException e) {
                    logger.error("Error during sending email: {}", e.getMessage());
                }
            }
        } catch (ValidationException e) {
            logger.error("Error during request processing: {}", e.getMessage());
        }

        SearchDTO searchDTO = null;
        try {
            searchDTO = (SearchDTO) request.getSession().getAttribute("searchDTO");
        } catch (Exception e) {
            logger.error("Error getting attribute from session: {}", e);
        }
        try {
            new DisplayingContactsListFormatter().formContactsList(request, searchDTO);
        } catch (DataException e) {
            logger.error("Error during fetching contacts: {}", e.getMessage());
            ErrorDispatcher.dispatchError(response, HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return "/jsp/main.jsp";

    }
}
