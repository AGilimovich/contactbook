package com.itechart.web.scheduler;

import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.db.JdbcDataSource;
import com.itechart.data.entity.Contact;
import com.itechart.web.email.EmailSender;
import org.apache.commons.mail.EmailException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Aleksandr on 18.03.2017.
 */
public class ScheduledJob implements Job {
    private JdbcContactDao contactDao;
    ResourceBundle bundle = ResourceBundle.getBundle("application");
    String hostName = bundle.getString("HOST_NAME");
    int SMTPPort = Integer.valueOf(bundle.getString("PORT"));
    String userName = bundle.getString("USER_NAME");
    String password = bundle.getString("PASSWORD");
    String emailFrom = bundle.getString("EMAIL");

    public ScheduledJob() {
        ResourceBundle properties = ResourceBundle.getBundle("db/database");
        String JDBC_DRIVER = properties.getString("JDBC_DRIVER");
        String DB_URL = properties.getString("DB_URL");
        String DB_USER = properties.getString("DB_USER");
        String DB_PASSWORD = properties.getString("DB_PASSWORD");
        try {
            JdbcDataSource ds = new JdbcDataSource(JDBC_DRIVER, DB_URL, DB_USER, DB_PASSWORD);
            contactDao = new JdbcContactDao(ds);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ArrayList<Contact> contacts = contactDao.getAll();
        EmailSender emailSender = new EmailSender(hostName, SMTPPort, userName, password, emailFrom);

        //todo template
        String subject = "C днем рождения!";
        String body = "C днем рождения!";

        if (contacts != null) {
            for (Contact con : contacts) {
                Date dateOfBirth = con.getDateOfBirth();
                if (removeTime(dateOfBirth).equals(removeTime(new Date()))) {
                    try {
                        emailSender.sendEmail(con.getEmail(),subject, body);
                    } catch (EmailException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * Method removes years, hours, minutes ... from date object.
     * @param date
     * @return
     */
    private Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
