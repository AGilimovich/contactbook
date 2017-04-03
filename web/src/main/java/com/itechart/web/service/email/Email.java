package com.itechart.web.service.email;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 24.03.2017.
 */
public class Email {
    private String emailAddress;
    private String subject;
    private String body;

    public Email(String emailAddress, String subject, String body) {
        this.emailAddress = emailAddress;
        this.subject = subject;
        this.body = body;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
}
