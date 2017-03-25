package com.itechart.web.service.template;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.*;

/**
 * Created by Aleksandr on 24.03.2017.
 */
public class EmailTemplatesProvidingService {
    private static Map<Class<? extends EmailTemplate>, EmailTemplate> emailBodyTemplates = new HashMap<>();

    static {
        STGroup stGroup = new STGroupFile("templates/template.stg");
        EmailTemplate commonEmail = new CommonTemplate(stGroup.getInstanceOf("commonEmail"));
        EmailTemplate birthdayEmail = new BirthdayTemplate(stGroup.getInstanceOf("birthdayEmail"));
        emailBodyTemplates.put(commonEmail.getClass(), commonEmail);
        emailBodyTemplates.put(birthdayEmail.getClass(), birthdayEmail);

    }


    public Map<Class<? extends EmailTemplate>, EmailTemplate>  getPredefinedEmailTemplates() {
        return emailBodyTemplates;

    }

    public EmailTemplate getEmailListTemplate(ArrayList<String> emailList) {
        EmailTemplate emailListTemplate = new EmailListTemplate(new ST("<emails:{email | <email>}; separator=\", \">"));
        emailListTemplate.getTemplate().add("emails", emailList);
        return emailListTemplate;
    }

}
