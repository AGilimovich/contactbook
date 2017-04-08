package com.itechart.web.service.template;

import org.stringtemplate.v4.ST;

/**
 * Interface of template class.
 */
public interface Template {
    String getDescription();

    ST getTemplate();
}
