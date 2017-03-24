package com.itechart.web.service;

import java.text.*;
import java.util.Date;

/**
 * Contains method for parsing date from string using specified pattern.
 */
public class DateTimeParser {

    /**
     * Function for parsing date from String using provided pattern.
     *
     * @param date in String format.
     * @return Date object or null if parsing has failed.
     */
    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        if (date != null) {
            try {
                return format.parse(date);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
