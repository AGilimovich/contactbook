package com.itechart.web.service.template;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.*;

/**
 * Created by Aleksandr on 24.03.2017.
 */
public class TemplatesProvidingService implements AbstractTemplateProvidingService {
    private static Map<Class<? extends Template>, Template> emailBodyTemplates = new HashMap<>();

    static {
        STGroup stGroup = new STGroupFile("templates/template.stg");
        Template commonEmail = new CommonEmailTemplate(stGroup.getInstanceOf("commonEmail"));
        Template birthdayEmail = new BirthdayEmailTemplate(stGroup.getInstanceOf("birthdayEmail"));
        emailBodyTemplates.put(commonEmail.getClass(), commonEmail);
        emailBodyTemplates.put(birthdayEmail.getClass(), birthdayEmail);

    }


    public Map<Class<? extends Template>, Template>  getPredefinedEmailTemplates() {
        return emailBodyTemplates;

    }

    public Template getEmailListTemplate(ArrayList<String> emailList) {
        Template emailListTemplate = new EmailListTemplate(new ST("<emails:{email | <email>}; separator=\", \">"));
        emailListTemplate.getTemplate().add("emails", emailList);
        return emailListTemplate;
    }

}
