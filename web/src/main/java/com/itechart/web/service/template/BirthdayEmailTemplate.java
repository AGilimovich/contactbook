package com.itechart.web.service.template;

import org.stringtemplate.v4.ST;

/**
 * Template for happy birthday email.
 */
public class BirthdayEmailTemplate implements Template {
    private ST template;
    private final String description = "Поздравление с днем рождения";

    public BirthdayEmailTemplate(ST template) {
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
