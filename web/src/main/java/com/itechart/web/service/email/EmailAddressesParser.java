package com.itechart.web.service.email;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for parsing list of emails from string.
 */
public class EmailAddressesParser {
    private final String emailRegex = "([\\w\\.]+@\\w+\\.\\w+),?\\s*";
    private Pattern pattern = Pattern.compile(emailRegex);
    private Logger logger = LoggerFactory.getLogger(EmailAddressesParser.class);

    public ArrayList<String> getEmailAddresses(String emailAddresses) {
        logger.info("Parsing emails string: {}", emailAddresses);
        if (StringUtils.isBlank(emailAddresses)) return null;
        ArrayList<String> addresses = new ArrayList<>();
        Matcher matcher = pattern.matcher(emailAddresses);
        while (matcher.find()) {
            addresses.add(matcher.group(1));
        }
        return addresses;

    }
}
