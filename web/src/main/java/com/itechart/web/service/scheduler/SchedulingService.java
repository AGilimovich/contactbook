package com.itechart.web.service.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Class for initialization and starting scheduler.
 */
public class SchedulingService implements AbstractSchedulingService{
    private Class<? extends Job> scheduledJob;
    private Logger logger = LoggerFactory.getLogger(SchedulingService.class);

    public SchedulingService(Class<? extends Job> scheduledJob) {
        this.scheduledJob = scheduledJob;
    }

    public void startScheduler(int hours, int minutes) {
        logger.info("Start daily scheduler, hours: {}, minutes: {}", hours, minutes);
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            // define the job and tie it to EmailCongratsJob class
            JobDetail job = newJob(scheduledJob)
                    .withIdentity("sendEmails", "group1")
                    .build();

            // Trigger the job to run every day at 12:00
            Trigger trigger = newTrigger()
                    .withIdentity("dailyTrigger", "group1")
                    .startNow()
                    .withSchedule(dailyAtHourAndMinute(hours, minutes))
                    .build();

            // Tell quartz to schedule the job using trigger
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error("Error during starting scheduler: {}", e.getMessage());
        }
    }


}
