package com.itechart.web.service.email;

import org.apache.commons.mail.EmailException;

/**
 * Interface of service for sending emails.
 */
public interface AbstractEmailingService {

    void sendEmail(String emailAddress, String subject, String body) throws EmailException;
}
