package com.itechart.web.service.template;

import org.stringtemplate.v4.ST;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class CommonTemplate implements EmailTemplate {
    private ST template;
    private String description = "Стандартный email";

    public CommonTemplate(ST template) {
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