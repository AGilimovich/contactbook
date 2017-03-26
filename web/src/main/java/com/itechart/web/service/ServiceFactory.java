package com.itechart.web.service;

import com.itechart.data.transaction.TransactionManager;
import com.itechart.web.service.data.DataService;
import com.itechart.web.service.data.IDataService;
import com.itechart.web.service.data.TransactionDataService;
import com.itechart.web.service.email.EmailingService;
import com.itechart.web.service.files.FileService;
import com.itechart.web.service.request.processing.RequestProcessingService;
import com.itechart.web.service.scheduler.EmailCongratsJob;
import com.itechart.web.service.scheduler.SchedulingService;
import com.itechart.web.service.template.EmailTemplatesProvidingService;
import com.itechart.web.service.validation.ValidationService;

import java.util.ResourceBundle;

/**
 * Created by Aleksandr on 24.03.2017.
 */
public class ServiceFactory {


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


    public IDataService getDataService() {
        return new TransactionDataService(transactionManager);
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

    public EmailTemplatesProvidingService getEmailTemplateProvidingService() {
        return new EmailTemplatesProvidingService();
    }

    public SchedulingService getEmailCongratsService() {
        return new SchedulingService(EmailCongratsJob.class);
    }

    public FileService getFileService() {
        return new FileService();
    }


}
