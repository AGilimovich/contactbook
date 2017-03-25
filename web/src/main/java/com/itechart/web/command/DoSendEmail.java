package com.itechart.web.command;

import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.email.Email;
import com.itechart.web.service.email.EmailingService;
import org.apache.commons.mail.EmailException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 18.03.2017.
 */
public class DoSendEmail implements Command {


    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Email email = ServiceFactory.getServiceFactory().getRequestProcessingService().processSendEmailRequest(request);
        EmailingService emailingService = ServiceFactory.getServiceFactory().getEmailService();
        for (String emailAddress : email.getEmailAddresses()) {
            try {
                emailingService.sendEmail(emailAddress, email.getSubject(), email.getBody());
            } catch (EmailException e) {
                e.printStackTrace();
            }
        }
        ArrayList<MainPageContactDTO> contacts = ServiceFactory.getServiceFactory().getDataService().getContactsWithAddressDTO();
        request.setAttribute("contacts", contacts);
        return "/jsp/main.jsp";
    }
}
