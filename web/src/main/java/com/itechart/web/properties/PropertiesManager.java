package com.itechart.web.properties;

import java.util.ResourceBundle;

/**
 * Class for access to the properties of the application.
 */
public class PropertiesManager {

    private static final ResourceBundle dbProperties = ResourceBundle.getBundle("db/database");
    private static final ResourceBundle applicationProperties = ResourceBundle.getBundle("application");
    //    private static final String JDBC_DRIVER = dbProperties.getString("JDBC_DRIVER");
    //  private static final String DB_URL = dbProperties.getString("DB_URL");
    // private static final String DB_USER = dbProperties.getString("DB_USER");
    // private static final String DB_PASSWORD = dbProperties.getString("DB_PASSWORD");
    private static final String FILE_PATH = applicationProperties.getString("FILE_PATH");
    private static final String scheduledHours = applicationProperties.getString("SCHEDULED_HOURS");
    private static final String scheduledMinutes = applicationProperties.getString("SCHEDULED_MINUTES");


//    public static String JDBC_DRIVER() {
//        return JDBC_DRIVER;
//    }
//
//    public static String DB_URL() {
//        return DB_URL;
//    }
//
//    public static String DB_USER() {
//        return DB_USER;
//    }
//
//    public static String DB_PASSWORD() {
//        return DB_PASSWORD;
//    }

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
