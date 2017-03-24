package com.itechart.web.service.email;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 24.03.2017.
 */
public class Email {
    private ArrayList<String> emailAddresses;
    private String subject;
    private String body;

    public Email(ArrayList<String> emailAddresses, String subject, String body) {
        this.emailAddresses = emailAddresses;
        this.subject = subject;
        this.body = body;
    }

    public ArrayList<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(ArrayList<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
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
