package com.itechart.web.listener;

import com.itechart.web.properties.PropertiesManager;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.scheduler.AbstractSchedulingService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;

/**
 * Initializes connection pool on startup and deregisters loaded driver on shutdown.
 */
public class ContextListener implements ServletContextListener {
    Logger logger = LoggerFactory.getLogger(ContextListener.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initialize quartz scheduler");
        int scheduledHours = Integer.valueOf(PropertiesManager.scheduledHours());
        int scheduledMinutes = Integer.valueOf(PropertiesManager.scheduledMinutes());
        AbstractSchedulingService schedulingService = ServiceFactory.getInstance().getEmailCongratsService();
        schedulingService.startScheduler(scheduledHours, scheduledMinutes);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                logger.info("Deregister jdbc driver");
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Could not deregister driver: {}", e.getMessage());
            }
        }
        ServletContext context = event.getServletContext();
        StdSchedulerFactory schedulerFactory = (StdSchedulerFactory) context
                .getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);
        if (schedulerFactory != null) {
            try {
                logger.info("Shutdown quartz scheduler");
                Collection<Scheduler> schedulers = null;
                try {
                    schedulers = schedulerFactory.getAllSchedulers();
                } catch (SchedulerException e) {
                    logger.error("Error getting schedulers: {}", e.getMessage());
                }
                for (Scheduler s : schedulers) {
                    s.shutdown();
                }
            } catch (SchedulerException e) {
                logger.error("Error shutting down schedulers: {}", e.getMessage());
            }
        }
    }


}

