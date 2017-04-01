package com.itechart.web.service.template;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Aleksandr on 27.03.2017.
 */
public interface AbstractTemplateProvidingService {

    Map<Class<? extends Template>, Template> getPredefinedEmailTemplates();

    Template getEmailListTemplate(ArrayList<String> emailList);
}
