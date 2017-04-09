package com.itechart.web.service.request.processing.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for parsing attachment's field values from request parameter.
 */
public class AttachmentFormFieldParser {
    private final String formFieldRegex = "(\\w+)=([[^/\\:?|*\"<>&]+)]*)&?";
    private final Pattern formFieldPattern = Pattern.compile(formFieldRegex);
    private Logger logger = LoggerFactory.getLogger(AttachmentFormFieldParser.class);

    public Map<String, String> parse(String fieldValue) {
        logger.info("Parse attachment form parameter: {}", fieldValue);
        Matcher matcher = formFieldPattern.matcher(fieldValue);
        Map<String, String> parameters = new HashMap<>();
        while (matcher.find()) {
            parameters.put(matcher.group(1), matcher.group(2));
        }
        return parameters;
    }
}
