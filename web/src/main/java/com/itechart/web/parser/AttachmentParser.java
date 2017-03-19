package com.itechart.web.parser;

import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aleksandr on 17.03.2017.
 */
public class AttachmentParser {
    private static final String regex = "(\\w+)=([\\w\\d\\s:\\.]*)&?&?";
    private static final Pattern pattern = Pattern.compile(regex);

    public ArrayList<Attachment> parseAttachments(Map<Long, String> formAttachmentParameters, Map<Long, String> attachmentFiles) {


        ArrayList<Attachment> attachments = new ArrayList<>();
        for (Map.Entry<Long, String> formParameters : formAttachmentParameters.entrySet()) {
            try {
                Attachment attachment = parseRequest(formParameters.getValue());
                attachment.setFile(attachmentFiles.get(formParameters.getKey()));
                attachments.add(attachment);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return attachments;
    }

    private static Attachment parseRequest(String request) throws ParseException {
        Attachment attachment = new Attachment();
        Matcher matcher = pattern.matcher(request);
        Map<String, String> parameters = new HashMap<>();
        while (matcher.find()) {
            parameters.put(matcher.group(1), matcher.group(2));
        }
        String name = parameters.get("name");
        String uploadDate = parameters.get("uploadDate");
        String comment = parameters.get("comment");


        if (name == null || comment == null || uploadDate == null) {
            throw new ParseException();
        }


        attachment.setName(name);
        attachment.setComment(comment);
        attachment.setUploadDate(DateTimeParser.parseDate(uploadDate, "dd.MM.yyyy HH:mm:ss"));

        return attachment;


    }

}
