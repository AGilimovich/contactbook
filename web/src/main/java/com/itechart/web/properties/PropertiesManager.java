package com.itechart.web.properties;

import java.util.ResourceBundle;

/**
 * Class for access to the properties of the application.
 */
public class PropertiesManager {

    private static final ResourceBundle applicationProperties = ResourceBundle.getBundle("application");
    private static final String FILE_PATH = applicationProperties.getString("FILE_PATH");
    private static final String scheduledHours = applicationProperties.getString("SCHEDULED_HOURS");
    private static final String scheduledMinutes = applicationProperties.getString("SCHEDULED_MINUTES");


    public static String FILE_PATH() {
        return FILE_PATH;
    }

    public static String scheduledHours() {
        return scheduledHours;
    }

    public static String scheduledMinutes() {
        return scheduledMinutes;
    }

}
