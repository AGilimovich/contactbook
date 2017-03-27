package com.itechart.web.service.email;

import org.apache.commons.mail.EmailException;

/**
 * Created by Aleksandr on 27.03.2017.
 */
public interface AbstractEmailingService {

    void sendEmail(String emailAddress, String subject, String body) throws EmailException;
}
