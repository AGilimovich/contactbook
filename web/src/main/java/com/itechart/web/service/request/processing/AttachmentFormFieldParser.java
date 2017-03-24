package com.itechart.web.service.request.processing;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aleksandr on 24.03.2017.
 */
public class AttachmentFormFieldParser {
    private final String formFieldRegex = "(\\w+)=([\\w\\d\\s\\.\\-:]*)&?";
    private final Pattern formFieldPattern = Pattern.compile(formFieldRegex);

    public Map<String, String> parse(String fieldValue) {
        Matcher matcher = formFieldPattern.matcher(fieldValue);
        Map<String, String> parameters = new HashMap<>();
        while (matcher.find()) {
            parameters.put(matcher.group(1), matcher.group(2));
        }
        return parameters;
    }
}
