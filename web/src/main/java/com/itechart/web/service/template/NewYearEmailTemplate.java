package com.itechart.web.service.template;

import org.stringtemplate.v4.ST;

/**
 * Template for common email.
 */
public class NewYearEmailTemplate implements Template {
    private ST template;
    private final String description = "Поздравление с новым годом";

    public NewYearEmailTemplate(ST template) {
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