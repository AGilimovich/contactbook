package com.itechart.web.command;

import com.itechart.web.service.ServiceFactory;
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
        AbstractEmailingService emailingService = ServiceFactory.getServiceFactory().getEmailService();
        try {
            emails = ServiceFactory.getServiceFactory().getRequestProcessingService().processSendEmailRequest(request);
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


        return new ShowContactsView().execute(servlet, request, response);
//        ArrayList<MainPageContactDTO> contacts = ServiceFactory.getServiceFactory().getDataService().getMainPageContactDTO();
//        request.setAttribute("contacts", contacts);
//        return "/jsp/main.jsp";
    }
}
