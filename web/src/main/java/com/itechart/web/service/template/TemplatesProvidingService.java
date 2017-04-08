package com.itechart.web.service.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of service for extracting templates from file.
 */
public class TemplatesProvidingService implements AbstractTemplateProvidingService {
    private Logger logger = LoggerFactory.getLogger(TemplatesProvidingService.class);
    private static Map<Class<? extends Template>, Template> emailBodyTemplates = new HashMap<>();

    static {
        STGroup stGroup = new STGroupFile("templates/template.stg");
        Template commonEmail = new CommonEmailTemplate(stGroup.getInstanceOf("commonEmail"));
        Template birthdayEmail = new BirthdayEmailTemplate(stGroup.getInstanceOf("birthdayEmail"));
        emailBodyTemplates.put(commonEmail.getClass(), commonEmail);
        emailBodyTemplates.put(birthdayEmail.getClass(), birthdayEmail);

    }


    public Map<Class<? extends Template>, Template>  getPredefinedEmailTemplates() {
        logger.info("Get predefined email templates");
        return emailBodyTemplates;

    }

    public Template getEmailListTemplate(ArrayList<String> emailList) {
        logger.info("Get template for email list: {}", emailList);
        Template emailListTemplate = new EmailListTemplate(new ST("<emails:{email | <email>}; separator=\", \">"));
        emailListTemplate.getTemplate().add("emails", emailList);
        return emailListTemplate;
    }

}
