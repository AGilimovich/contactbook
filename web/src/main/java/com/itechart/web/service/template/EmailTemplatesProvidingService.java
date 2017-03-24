package com.itechart.web.service.template;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 24.03.2017.
 */
public class EmailTemplatesProvidingService {
    public ArrayList<ST> getEmailTemplates() {
        ArrayList<ST> templates = new ArrayList<>();
        STGroup stGroup = new STGroupFile("templates/template.stg");
        ST commonEmail = stGroup.getInstanceOf("commonEmail");
        ST birthdayEmail = stGroup.getInstanceOf("birthdayEmail");
        templates.add(commonEmail);
        templates.add(birthdayEmail);
        return templates;

    }

    public ST getEmailListTemplate() {
        return new ST("<emails:{email | <email>}; separator=\", \">");
    }

}
