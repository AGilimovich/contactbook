package com.itechart.web.service.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service for sending emails.
 */
public class EmailService implements AbstractEmailingService {
    private Logger logger = LoggerFactory.getLogger(EmailService.class);
    private String hostName;
    private int SMTPPort;
    private String userName;
    private String password;
    private String emailFrom;

    public EmailService(String hostName, int SMTPPort, String userName, String password, String emailFrom) {
        this.hostName = hostName;
        this.SMTPPort = SMTPPort;
        this.userName = userName;
        this.password = password;
        this.emailFrom = emailFrom;
    }

    public void sendEmail(String emailAddress, String subject, String body) throws EmailException {
        logger.info("Sending email with parameters: address: {}, subject: {}, body: {}", emailAddress, subject, body);
        Email email = new SimpleEmail();
        email.setHostName(hostName);
        email.setSmtpPort(SMTPPort);
        email.setAuthenticator(new DefaultAuthenticator(userName, password));
        email.setSSLOnConnect(true);
        email.setFrom(emailFrom);
        email.setSubject(subject);
        email.setMsg(body);
        email.addTo(emailAddress);
        email.send();


    }

}
