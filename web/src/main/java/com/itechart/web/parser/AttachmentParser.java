package com.itechart.web.parser;

import com.itechart.data.entity.Attachment;
import com.itechart.web.handler.Action;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aleksandr on 17.03.2017.
 */
public class AttachmentParser {
    private static final String regex = "(\\w+)=([\\w\\d\\s\\.\\-:]*)&?";
    private static final Pattern pattern = Pattern.compile(regex);

    public Map<Attachment, Action> parseAttachments(Map<String, String> formAttachmentParameters, Map<String, String> attachmentFiles) {


        Map<Attachment, Action> attachments = new HashMap<>();
        for (Map.Entry<String, String> formParameters : formAttachmentParameters.entrySet()) {
            try {
                Map<Attachment, Action> attachmentMap = parseRequest(formParameters.getValue());
                for (Map.Entry<Attachment, Action> entry : attachmentMap.entrySet()) {
                    //set the name of file
                    entry.getKey().setFile(attachmentFiles.get(formParameters.getKey()));
                    attachments.put(entry.getKey(), entry.getValue());
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return attachments;
    }

    private static Map<Attachment, Action> parseRequest(String request) throws ParseException {
        Attachment attachment = new Attachment();
        Matcher matcher = pattern.matcher(request);
        Map<String, String> parameters = new HashMap<>();
        while (matcher.find()) {
            parameters.put(matcher.group(1), matcher.group(2));
        }

        Map<Attachment, Action> map = new HashMap<>();
        Action action = null;
        String status = parameters.get("status");
        switch (status) {
            case "NEW":
                action = Action.ADD;
                break;
            case "EDITED":
                action = Action.UPDATE;
                break;
            case "NONE":
                action = Action.NONE;
                break;
            case "DELETED":
                action = Action.DELETE;
                break;
            default:
                action = Action.NONE;
        }
        String id = parameters.get("id");
        String name = parameters.get("name");
        String uploadDate = parameters.get("uploadDate");
        String comment = parameters.get("comment");


        attachment.setId(Long.valueOf(id));
        attachment.setName(name);
        attachment.setComment(comment);
        attachment.setUploadDate(DateTimeParser.parseDate(uploadDate, "dd.MM.yyyy HH:mm:ss"));

        map.put(attachment, action);
        return map;


    }

}
