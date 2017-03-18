package com.itechart.web.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Created by Aleksandr on 18.03.2017.
 */
public class EmailSender {
    private String hostName;
    private int SMTPPort;
    private String userName;
    private String password;
    private String emailFrom;

    public EmailSender(String hostName, int SMTPPort, String userName, String password, String emailFrom) {
        this.hostName = hostName;
        this.SMTPPort = SMTPPort;
        this.userName = userName;
        this.password = password;
        this.emailFrom = emailFrom;
    }

    public void sendEmail(String emailAddress, String subject, String body) throws EmailException {
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
