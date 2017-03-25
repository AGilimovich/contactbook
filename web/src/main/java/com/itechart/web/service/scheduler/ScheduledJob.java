package com.itechart.web.service.scheduler;

import com.itechart.data.entity.Contact;
import static com.itechart.web.service.template.EmailTemplatesProvidingService.TemplateType;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.email.EmailingService;
import com.itechart.web.service.template.EmailTemplatesProvidingService;
import org.apache.commons.mail.EmailException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Aleksandr on 18.03.2017.
 */
public class ScheduledJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ArrayList<Contact> contacts = ServiceFactory.getServiceFactory().getDataService().getContactsWithBirthday(new Date());
        EmailingService emailingService = ServiceFactory.getServiceFactory().getEmailService();
        EmailTemplatesProvidingService templateService = ServiceFactory.getServiceFactory().getEmailTemplateProvidingService();

        ST template = templateService.getPredefinedEmailTemplates().get(TemplateType.BIRTHDAY);
        String subject = "C днем рождения!";
        String body = template.render();

        if (contacts != null) {
            for (Contact con : contacts) {
                try {
                    emailingService.sendEmail(con.getEmail(), subject, body);
                } catch (EmailException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    /**
     * Method removes years, hours, minutes ... from date object.
     *
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
