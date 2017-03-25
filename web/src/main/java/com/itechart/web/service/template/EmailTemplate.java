package com.itechart.web.service.template;

import org.stringtemplate.v4.ST;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public interface EmailTemplate {
    String getDescription();

    ST getTemplate();
}
