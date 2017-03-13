package com.itechart.web.command;

import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.data.db.JdbcDataSource;

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

    public CommandFactory() {
        ResourceBundle properties = ResourceBundle.getBundle("db/database");
        String JDBC_DRIVER = properties.getString("JDBC_DRIVER");
        String DB_URL = properties.getString("DB_URL");
        String DB_USER = properties.getString("DB_USER");
        String DB_PASSWORD = properties.getString("DB_PASSWORD");
        try {
            JdbcDataSource ds = new JdbcDataSource(JDBC_DRIVER, DB_URL, DB_USER, DB_PASSWORD);
            contactDao = new JdbcContactDao(ds);
            phoneDao = new JdbcPhoneDao(ds);
            attachmentDao = new JdbcAttachmentDao(ds);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Command getCommand(HttpServletRequest request) throws ServletException {

        String path = request.getServletPath();
        System.out.println(path);

        if (path.isEmpty()) return new ShowContactsCommand(contactDao);
        switch (path) {
            case "/":
                return new ShowContactsCommand(contactDao);
            case "/email":
                return new ShowEmailViewCommand();
            case "/search":
                return new ShowSearchViewCommand();
            case "/add":
                return new CreateContactCommand(contactDao);
            case "/edit":
                return new EditContactCommand(contactDao, phoneDao, attachmentDao);
            case "/delete":
                return new DeleteContactCommand(contactDao);
            default:
                throw new ServletException("no such path");
        }

    }
}
