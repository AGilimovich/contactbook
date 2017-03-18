package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.web.email.EmailSender;
import com.itechart.web.parser.EmailParser;
import org.apache.commons.mail.EmailException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Aleksandr on 18.03.2017.
 */
public class DoSendEmail implements Command {
    private JdbcContactDao contactDao;
    private JdbcAddressDao addressDao;

    public DoSendEmail(JdbcContactDao contactDao, JdbcAddressDao addressDao) {
        this.contactDao = contactDao;
        this.addressDao = addressDao;
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String emailAddressesString = request.getParameter("emailAddresses");
        String subject = request.getParameter("subject");
        String template = request.getParameter("template");
        String body = request.getParameter("body");
//        Email configuration
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String hostName = bundle.getString("HOST_NAME");
        int SMTPPort = Integer.valueOf(bundle.getString("PORT"));
        String userName = bundle.getString("USER_NAME");
        String password = bundle.getString("PASSWORD");
        String emailFrom = bundle.getString("EMAIL");


        EmailSender sender = new EmailSender(hostName, SMTPPort, userName, password, emailFrom);

        ArrayList<String> emailAddresses = new EmailParser().getEmailAddresses(emailAddressesString);
        for (String email : emailAddresses) {
            try {
                sender.sendEmail(email, subject, body);
            } catch (EmailException e) {
                e.printStackTrace();
            }
        }
        return (new ShowContacts(contactDao, addressDao)).execute(servlet, request, response);
    }
}
