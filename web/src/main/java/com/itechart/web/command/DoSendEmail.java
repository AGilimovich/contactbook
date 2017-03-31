package com.itechart.web.command;

import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.email.AbstractEmailingService;
import com.itechart.web.service.email.Email;
import org.apache.commons.mail.EmailException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Aleksandr on 18.03.2017.
 */
public class DoSendEmail implements Command {


    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Email email = ServiceFactory.getServiceFactory().getRequestProcessingService().processSendEmailRequest(request);
        AbstractEmailingService emailingService = ServiceFactory.getServiceFactory().getEmailService();
        for (String emailAddress : email.getEmailAddresses()) {
            try {
                emailingService.sendEmail(emailAddress, email.getSubject(), email.getBody());
            } catch (EmailException e) {
                e.printStackTrace();
            }
        }
        return new ShowContactsView().execute(servlet, request, response);
//        ArrayList<MainPageContactDTO> contacts = ServiceFactory.getServiceFactory().getDataService().getMainPageContactDTO();
//        request.setAttribute("contacts", contacts);
//        return "/jsp/main.jsp";
    }
}
