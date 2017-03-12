package com.itechart.web.command;

import com.itechart.data.dao.ContactDao;
import com.itechart.data.db.JdbcDataSource;
import com.itechart.data.entity.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Factory class for which produces command objects.
 */
public class CommandFactory {
    private ContactDao dao;

    public CommandFactory() {
        ResourceBundle properties = ResourceBundle.getBundle("db/database");
        String JDBC_DRIVER = properties.getString("JDBC_DRIVER");
        String DB_URL = properties.getString("DB_URL");
        String DB_USER = properties.getString("DB_USER");
        String DB_PASSWORD = properties.getString("DB_PASSWORD");
        try {
            JdbcDataSource ds = new JdbcDataSource(JDBC_DRIVER, DB_URL, DB_USER, DB_PASSWORD);
            dao = new ContactDao(ds);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Command getCommand(HttpServletRequest request) throws ServletException {

        String path = request.getServletPath();


        if (path.isEmpty()) return new ShowContactsCommand(dao);
        switch (path) {
            case "/":
                return new ShowContactsCommand(dao);
            case "/email":
                return new ShowEmailViewCommand();
            case "/search":
                return new ShowSearchViewCommand();
            case "contact/add":
                return new CreateContactCommand();
            case "contact/edit":
                return new EditContactCommand();
            case "contact/delete":
                return new DeleteContactCommand();
            default:
                throw new ServletException("no such path");
        }

    }
}
