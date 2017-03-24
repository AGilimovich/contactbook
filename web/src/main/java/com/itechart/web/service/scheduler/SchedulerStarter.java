package com.itechart.web.service.scheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Class for initialization and starting scheduler.
 */
public class SchedulerStarter {

    public void startScheduler(int hours, int minutes) {
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            // define the job and tie it to ScheduledJob class
            JobDetail job = newJob(ScheduledJob.class)
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
            e.printStackTrace();
        }
    }


}
