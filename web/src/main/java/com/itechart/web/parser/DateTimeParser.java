package com.itechart.web.parser;

import java.text.*;
import java.util.Date;

/**
 * Contains method for parsing date from string using specified pattern.
 */
public class DateTimeParser {

    /**
     * function for parsing date from format in which it is received from jsp;
     *
     * @param date in String format.
     * @return Date object or null if parsing has failed
     */
    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        if (date != null && (!date.isEmpty())) {
            try {
                return format.parse(date);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
