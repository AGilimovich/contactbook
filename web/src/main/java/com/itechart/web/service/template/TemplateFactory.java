package com.itechart.web.service.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of factory of templates.
 */
public class TemplateFactory implements AbstractTemplateFactory {
    private Logger logger = LoggerFactory.getLogger(TemplateFactory.class);
    private static ArrayList<String> STNames = new ArrayList<>();
    private static Map<String, Class<? extends Template>> templateClasses = new HashMap<>();
    private STGroup stGroup = new STGroupFile("templates/template.stg");

    static {
        STNames.add("/commonEmail");
        STNames.add("/birthdayEmail");
        templateClasses.put("/commonEmail", CommonEmailTemplate.class);
        templateClasses.put("/birthdayEmail", BirthdayEmailTemplate.class);
    }


    @Override
    public ArrayList<Template> getPredefinedEmailTemplates() {
        logger.info("Get predefined email templates");
        ArrayList<Template> templates = new ArrayList<>();
        for (String name : STNames) {
            ST st = stGroup.getInstanceOf(name);
            Class<? extends Template> templateClass = templateClasses.get(name);
            try {
                Constructor<? extends Template> cons = templateClass.getConstructor(ST.class);
                Template template = cons.newInstance(st);
                templates.add(template);
            } catch (NoSuchMethodException e) {
                logger.error("Error during template instantiation: {}", e.getMessage());
            } catch (IllegalAccessException e) {
                logger.error("Error during template instantiation: {}", e.getMessage());
            } catch (InstantiationException e) {
                logger.error("Error during template instantiation: {}", e.getMessage());
            } catch (InvocationTargetException e) {
                logger.error("Error during template instantiation: {}", e.getMessage());
            }
        }
        return templates;
    }

    @Override
    public Template getTemplate(String name) {
        logger.info("Get common email template for name: {}", name);
        ST st = stGroup.getInstanceOf(name);
        Class<? extends Template> templateClass = templateClasses.get(name);
        try {
            if (templateClass != null) {
                Constructor<? extends Template> cons = templateClass.getConstructor(ST.class);
                Template template = cons.newInstance(st);
                return template;
            }
        } catch (NoSuchMethodException e) {
            logger.error("Error during template instantiation: {}", e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("Error during template instantiation: {}", e.getMessage());
        } catch (InstantiationException e) {
            logger.error("Error during template instantiation: {}", e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error("Error during template instantiation: {}", e.getMessage());
        }
        return null;
    }


    @Override
    public Template getEmailListTemplate(ArrayList<String> emailList) {
        logger.info("Get template for email list: {}", emailList);
        Template emailListTemplate = new EmailListTemplate(new ST("<emails:{email | <email>}; separator=\", \">"));
        emailListTemplate.getTemplate().add("emails", emailList);
        return emailListTemplate;
    }

}
