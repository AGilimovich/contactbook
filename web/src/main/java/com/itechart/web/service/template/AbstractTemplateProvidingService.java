package com.itechart.web.service.template;

import java.util.ArrayList;
import java.util.Map;

/**
 * Service for extracting templates from files.
 */
public interface AbstractTemplateProvidingService {

    Map<String, Template> getPredefinedEmailTemplates();

    Template getTemplate(String name);

    Template getEmailListTemplate(ArrayList<String> emailList);
}
