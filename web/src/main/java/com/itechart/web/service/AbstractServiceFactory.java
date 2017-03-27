package com.itechart.web.service;

import com.itechart.web.service.data.AbstractDataService;
import com.itechart.web.service.email.AbstractEmailingService;
import com.itechart.web.service.files.AbstractFileService;
import com.itechart.web.service.request.processing.AbstractRequestProcessingService;
import com.itechart.web.service.template.AbstractTemplateProvidingService;
import com.itechart.web.service.validation.AbstractValidationService;

/**
 * Created by Aleksandr on 27.03.2017.
 */
public interface AbstractServiceFactory {
    AbstractDataService getDataService();

    AbstractValidationService getValidationService();

    AbstractRequestProcessingService getRequestProcessingService();

    AbstractEmailingService getEmailService();

    AbstractTemplateProvidingService getEmailTemplateProvidingService();


    AbstractFileService getFileService();
}
