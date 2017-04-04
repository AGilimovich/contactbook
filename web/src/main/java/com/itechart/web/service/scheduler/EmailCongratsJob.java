package com.itechart.web.service.scheduler;

import com.itechart.data.entity.Contact;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.exception.DataException;
import com.itechart.web.service.email.AbstractEmailingService;
import com.itechart.web.service.email.EmailingService;
import com.itechart.web.service.template.AbstractTemplateProvidingService;
import com.itechart.web.service.template.BirthdayEmailTemplate;
import com.itechart.web.service.template.Template;
import com.itechart.web.service.template.TemplatesProvidingService;
import org.apache.commons.mail.EmailException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Aleksandr on 18.03.2017.
 */
public class EmailCongratsJob implements Job {
    private Logger logger = LoggerFactory.getLogger(SchedulingService.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Execute daily congratulation job");
        ArrayList<Contact> contacts = null;
        try {
            Date date = new Date();
            logger.info("Fetch contacts with birth day: {}", date.toString());
            contacts = ServiceFactory.getServiceFactory().getDataService().getContactsWithBirthday(date);
        } catch (DataException e) {
            logger.error("Error during fetching contacts", e.getMessage());
            e.printStackTrace();
        }
        AbstractEmailingService emailingService = ServiceFactory.getServiceFactory().getEmailService();
        AbstractTemplateProvidingService templateService = ServiceFactory.getServiceFactory().getEmailTemplateProvidingService();

        Template template = templateService.getPredefinedEmailTemplates().get(BirthdayEmailTemplate.class);
        String subject = "C днем рождения!";
        String body = template.getTemplate().render();

        if (contacts != null) {
            for (Contact con : contacts) {
                try {
                    logger.info("Send email, email address {}, subject: {}, body: {}", con.getEmail(), subject, body);
                    emailingService.sendEmail(con.getEmail(), subject, body);
                } catch (EmailException e) {
                    logger.error("Error during sending email", e.getMessage());
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
