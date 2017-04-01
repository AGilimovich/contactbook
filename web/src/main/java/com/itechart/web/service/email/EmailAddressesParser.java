package com.itechart.web.service.email;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for parsing list of emails from string.
 */
public class EmailAddressesParser {
    private final String emailRegex = "([\\w\\.]+@\\w+\\.\\w+),?\\s*";
    private Pattern pattern = Pattern.compile(emailRegex);

    public ArrayList<String> getEmailAddresses(String emailAddresses) {
        if (emailAddresses == null) return null;
        ArrayList<String> addresses = new ArrayList<>();
        Matcher matcher = pattern.matcher(emailAddresses);
        while (matcher.find()) {
            addresses.add(matcher.group(1));
        }
        return addresses;

    }
}
