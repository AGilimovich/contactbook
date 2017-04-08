package com.itechart.web.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

/**
 * Class for managing loading and access to the properties of the application.
 */
public class PropertiesManager {
    Logger logger = LoggerFactory.getLogger(PropertiesManager.class);

    private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");
    private static String FILE_PATH = applicationProperties.getString("FILE_PATH");
    private static String scheduledHours = applicationProperties.getString("SCHEDULED_HOURS");
    private static String scheduledMinutes = applicationProperties.getString("SCHEDULED_MINUTES");
    private static long MAX_FILE_SIZE = Long.valueOf(applicationProperties.getString("MAX_FILE_SIZE")) * 1024 * 1024;
    private static String HOST_NAME = applicationProperties.getString("HOST_NAME");
    private static int PORT = Integer.valueOf(applicationProperties.getString("PORT"));
    private static String USER_NAME = applicationProperties.getString("USER_NAME");
    private static String PASSWORD = applicationProperties.getString("PASSWORD");
    private static String EMAIL = applicationProperties.getString("EMAIL");


    public static long MAX_FILE_SIZE() {
        return MAX_FILE_SIZE;
    }

    public static String FILE_PATH() {
        return FILE_PATH;
    }

    public static String scheduledHours() {
        return scheduledHours;
    }

    public static String scheduledMinutes() {
        return scheduledMinutes;
    }

    public static String HOST_NAME() {
        return HOST_NAME;
    }

    public static int PORT() {
        return PORT;
    }

    public static String USER_NAME() {
        return USER_NAME;
    }

    public static String PASSWORD() {
        return PASSWORD;
    }

    public static String EMAIL() {
        return EMAIL;
    }
}
