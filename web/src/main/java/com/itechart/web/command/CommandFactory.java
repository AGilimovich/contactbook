package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.data.db.JdbcDataSource;
import com.itechart.web.properties.PropertiesManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * Factory class for which produces command objects.
 */
public class CommandFactory {
    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;

    public CommandFactory() {

        DataSource ds = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/MySQLDatasource");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (ds != null) {
            contactDao = new JdbcContactDao(ds);
            phoneDao = new JdbcPhoneDao(ds);
            attachmentDao = new JdbcAttachmentDao(ds);
            addressDao = new JdbcAddressDao(ds);
        }

    }


    public Command getCommand(HttpServletRequest request) throws ServletException {

        String path = request.getServletPath();
        CommandType receivedCommand = CommandType.fromString(path);
        if (receivedCommand != null)
            switch (receivedCommand) {
                case ROOT:
                    return new ShowContacts(contactDao, addressDao);
                case EMAIL:
                    return new ShowEmail(contactDao);
                case SEND:
                    return new DoSendEmail(contactDao, addressDao);
                case SEARCH:
                    return new ShowSearch();
                case ADD:
                    return new ShowContactAdd(contactDao, addressDao, phoneDao, attachmentDao);
                case EDIT:
                    return new ShowContactEdit(contactDao, addressDao, phoneDao, attachmentDao);
                case SAVE:
                    return new DoCreateContact(contactDao, phoneDao, attachmentDao, addressDao);
                case UPDATE:
                    return new DoUpdateContact(contactDao, addressDao, phoneDao, attachmentDao);
                case FIND:
                    return new DoSearch(contactDao, addressDao);
                case DELETE:
                    return new DoDeleteContact(contactDao, phoneDao, attachmentDao, addressDao);
                case FILE:
                    return new DoSendFile();
                default:
                    throw new ServletException("Wrong path");
            }
        else throw new ServletException("Wrong path");

    }
}
