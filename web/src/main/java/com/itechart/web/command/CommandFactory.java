package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.data.db.JdbcDataSource;
import com.itechart.web.properties.PropertiesManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

/**
 * Factory class for which produces command objects.
 */
public class CommandFactory {
    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;

    public CommandFactory() {

        String JDBC_DRIVER = PropertiesManager.JDBC_DRIVER();
        String DB_URL = PropertiesManager.DB_URL();
        String DB_USER = PropertiesManager.DB_USER();
        String DB_PASSWORD = PropertiesManager.DB_PASSWORD();
        try {
            JdbcDataSource ds = new JdbcDataSource(JDBC_DRIVER, DB_URL, DB_USER, DB_PASSWORD);
            contactDao = new JdbcContactDao(ds);
            phoneDao = new JdbcPhoneDao(ds);
            attachmentDao = new JdbcAttachmentDao(ds);
            addressDao = new JdbcAddressDao(ds);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Command getCommand(HttpServletRequest request) throws ServletException {

        String path = request.getServletPath();


        switch (path) {
            case "/":
                return new ShowContacts(contactDao, addressDao);
            case "/email":
                return new ShowEmail(contactDao);
            case "/send":
                return new DoSendEmail(contactDao, addressDao);
            case "/search":
                return new ShowSearch();
            case "/add":
                return new ShowContactAdd(contactDao, addressDao, phoneDao, attachmentDao);
            case "/edit":
                return new ShowContactEdit(contactDao, addressDao, phoneDao, attachmentDao);
            case "/save":
                return new DoCreateContact(contactDao, phoneDao, attachmentDao, addressDao);
            case "/update":
                return new DoUpdateContact(contactDao, addressDao, phoneDao, attachmentDao);
            case "/find":
                return new DoSearch(contactDao, addressDao);
            case "/delete":
                return new DoDeleteContact(contactDao, phoneDao, attachmentDao, addressDao);
            case "/file":
                return new DoSendFile();
            default:
                throw new ServletException("no such path");
        }

    }
}
