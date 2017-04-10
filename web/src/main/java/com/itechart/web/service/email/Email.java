package com.itechart.web.service.email;

/**
 * Base class of email to be sent.
 */
public class Email {
    private String subject;
    private String template;
    private String body;

    public Email(String subject, String template, String body) {
        this.subject = subject;
        this.template = template;
        this.body = body;
    }



    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
