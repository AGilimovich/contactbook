package com.itechart.web.service.template;

import java.util.ArrayList;

/**
 * Factory of templates.
 */
public interface AbstractTemplateFactory {

    ArrayList<Template> getPredefinedEmailTemplates();

    Template getTemplate(String name);

    Template getEmailListTemplate(ArrayList<String> emailList);
}
