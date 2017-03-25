package com.itechart.web.service.template;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aleksandr on 24.03.2017.
 */
public class EmailTemplatesProvidingService {
    private static Map<TemplateType, ST> templates = new HashMap<>();

    static {
        STGroup stGroup = new STGroupFile("templates/template.stg");
        ST commonEmail = stGroup.getInstanceOf("commonEmail");
        ST birthdayEmail = stGroup.getInstanceOf("birthdayEmail");
        templates.put(TemplateType.COMMON, commonEmail);
        templates.put(TemplateType.BIRTHDAY, birthdayEmail);
    }

    public enum TemplateType {
        COMMON("Стандартный email"), BIRTHDAY("Поздравление с днем рождения");
        private String description;

        TemplateType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public Map<TemplateType, ST> getPredefinedEmailTemplates() {
        return templates;

    }

    public ST getEmailListTemplate(ArrayList<String> emailList) {
        ST emailListTemplate = new ST("<emails:{email | <email>}; separator=\", \">");
        emailListTemplate.add("emails", emailList);
        return emailListTemplate;
    }

}
