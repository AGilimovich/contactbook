package com.itechart.web.service;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.web.service.email.EmailingService;
import com.itechart.web.service.request.processing.RequestProcessingService;
import com.itechart.web.service.template.EmailTemplatesProvidingService;
import com.itechart.web.service.validation.ValidationService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * Created by Aleksandr on 24.03.2017.
 */
public class ServiceFactory {

    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;
    private static ServiceFactory instance;

    private String hostName;
    private int SMTPPort;
    private String userName;
    private String password;
    private String emailFrom;


    private ServiceFactory() {
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
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        hostName = bundle.getString("HOST_NAME");
        SMTPPort = Integer.valueOf(bundle.getString("PORT"));
        userName = bundle.getString("USER_NAME");
        password = bundle.getString("PASSWORD");
        emailFrom = bundle.getString("EMAIL");
    }

    /**
     * Static factory method.
     *
     * @return
     */
    public static ServiceFactory getServiceFactory() {
        if (instance == null) {
            return instance = new ServiceFactory();
        } else return instance;
    }


    public DataService getDataService() {
        return new DataService(contactDao, phoneDao, attachmentDao, addressDao);
    }

    public ValidationService getValidationService() {
        return new ValidationService();
    }

    public RequestProcessingService getRequestProcessingService() {
        return new RequestProcessingService();
    }

    public EmailingService getEmailService() {
        return new EmailingService(hostName, SMTPPort, userName, password, emailFrom);
    }

    public EmailTemplatesProvidingService getEmailTemplateService() {
        return new EmailTemplatesProvidingService();
    }


}
