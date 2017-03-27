package com.itechart.web.service;

import com.itechart.data.transaction.TransactionManager;
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

import java.util.ResourceBundle;

/**
 * Created by Aleksandr on 24.03.2017.
 */
public class ServiceFactory implements AbstractServiceFactory{


    private static ServiceFactory instance;

    private String hostName;
    private int SMTPPort;
    private String userName;
    private String password;
    private String emailFrom;
    private TransactionManager transactionManager;


    private ServiceFactory() {

        ResourceBundle bundle = ResourceBundle.getBundle("application");
        hostName = bundle.getString("HOST_NAME");
        SMTPPort = Integer.valueOf(bundle.getString("PORT"));
        userName = bundle.getString("USER_NAME");
        password = bundle.getString("PASSWORD");
        emailFrom = bundle.getString("EMAIL");
        transactionManager = new TransactionManager();
    }

    /**
     * Static factory method.
     *
     * @return
     */
    public static ServiceFactory getServiceFactory() {
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
        return new FileService();
    }


}
