package com.itechart.web.service.template;

import org.stringtemplate.v4.ST;

/**
 * Template for email list.
 */
public class EmailListTemplate implements Template {
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