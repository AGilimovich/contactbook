package com.itechart.web.service.scheduler;

import com.itechart.data.entity.Contact;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.data.exception.DataException;
import com.itechart.web.service.email.AbstractEmailingService;
import com.itechart.web.service.template.AbstractTemplateFactory;
import com.itechart.web.service.template.BirthdayEmailTemplate;
import com.itechart.web.service.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;

/**
 * Job on sending happy birthday emails.
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
            contacts = ServiceFactory.getInstance().getDataService().getContactsWithBirthday(date);
        } catch (DataException e) {
            logger.error("Error during fetching contacts", e.getMessage());
        }
        AbstractEmailingService emailingService = ServiceFactory.getInstance().getEmailService();
        AbstractTemplateFactory templateService = ServiceFactory.getInstance().getEmailTemplateProvidingService();
        String subject = "C днем рождения!";
        if (contacts != null) {
            for (Contact con : contacts) {
                try {
                    Template template = templateService.getTemplate("/birthdayEmail");
                    String name = con.getName();
                    if (name != null) {
                        if (con.getPatronymic() != null) {
                            name = name.concat(" ").concat(con.getPatronymic());
                        }
                    }
                    template.getTemplate().add("name", name);
                    String body = template.getTemplate().render();
                    logger.info("Send email, email address {}, subject: {}, body: {}", con.getEmail(), subject, body);
                    emailingService.sendEmail(con.getEmail(), subject, body);
                } catch (EmailException e) {
                    logger.error("Error during sending email", e.getMessage());
                }
            }
        }
    }

}
