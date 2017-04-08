package com.itechart.web.service;

import com.itechart.data.transaction.TransactionManager;
import com.itechart.web.properties.PropertiesManager;
import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.data.TransactionalDataService;
import com.itechart.web.service.email.AbstractEmailingService;
import com.itechart.web.service.email.EmailingService;
import com.itechart.web.service.files.AbstractFileService;
import com.itechart.web.service.files.FileService;
import com.itechart.web.service.request.processing.AbstractRequestProcessingService;
import com.itechart.web.service.request.processing.RequestProcessingService;
import com.itechart.web.service.scheduler.AbstractSchedulingService;
import com.itechart.web.service.scheduler.EmailCongratsJob;
import com.itechart.web.service.scheduler.SchedulingService;
import com.itechart.web.service.template.AbstractTemplateProvidingService;
import com.itechart.web.service.template.TemplatesProvidingService;
import com.itechart.web.service.validation.AbstractValidationService;
import com.itechart.web.service.validation.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Singleton class.
 */
public class ServiceFactory {

    private Logger logger = LoggerFactory.getLogger(ServiceFactory.class);
    private static ServiceFactory instance;

    private String hostName;
    private int SMTPPort;
    private String userName;
    private String password;
    private String emailFrom;
    private TransactionManager transactionManager;
    private String FILE_PATH;

    private ServiceFactory() {
        FILE_PATH = PropertiesManager.FILE_PATH();
        hostName = PropertiesManager.HOST_NAME();
        SMTPPort = PropertiesManager.PORT();
        userName = PropertiesManager.USER_NAME();
        password = PropertiesManager.PASSWORD();
        emailFrom = PropertiesManager.EMAIL();
        DataSource dataSource = null;
        try {
            logger.info("Lookup datasource");
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/MySQLDatasource");
        } catch (NamingException e) {
            logger.error("Error during context lookup: {}", e.getMessage());
        }
        transactionManager = new TransactionManager(dataSource);


    }


    public static ServiceFactory getInstance() {
        if (instance == null) {
            synchronized (ServiceFactory.class) {
                if (instance == null) {
                    instance = new ServiceFactory();
                }
            }
        }
        return instance;
    }


    public AbstractDataService getDataService() {
        return new TransactionalDataService(transactionManager);
    }

    public AbstractValidationService getValidationService() {
        return new ValidationService();
    }

    public AbstractRequestProcessingService getRequestProcessingService() {
        return new RequestProcessingService();
    }

    public AbstractEmailingService getEmailService() {
        return new EmailingService(hostName, SMTPPort, userName, password, emailFrom);
    }

    public AbstractTemplateProvidingService getEmailTemplateProvidingService() {
        return new TemplatesProvidingService();
    }

    public AbstractSchedulingService getEmailCongratsService() {
        return new SchedulingService(EmailCongratsJob.class);
    }

    public AbstractFileService getFileService() {
        return new FileService(FILE_PATH);
    }


}
