package com.itechart.web.service.template;

import org.stringtemplate.v4.ST;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class EmailListTemplate implements EmailTemplate {
    private ST template;
    private String description = "Перечень email адресов";

    public EmailListTemplate(ST template) {
        this.template = template;
    }


    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ST getTemplate() {
        return template;
    }
}