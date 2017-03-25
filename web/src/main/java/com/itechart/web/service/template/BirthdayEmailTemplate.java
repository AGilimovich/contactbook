package com.itechart.web.service.template;

import org.stringtemplate.v4.ST;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class BirthdayEmailTemplate implements Template {
    private ST template;
    private String description = "Поздравление с днем рождения";

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
