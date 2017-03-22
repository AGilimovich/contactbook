package com.itechart.web.handler;

import com.itechart.data.entity.Attachment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aleksandr on 17.03.2017.
 */
public class AttachmentBuilder {
    private static final String regex = "(\\w+)=([\\w\\d\\s\\.\\-:]*)&?";
    private static final Pattern pattern = Pattern.compile(regex);


    private ArrayList<Attachment> newAttachments = new ArrayList<>();
    private ArrayList<Attachment> updatedAttachments = new ArrayList<>();
    private ArrayList<Attachment> deletedAttachments = new ArrayList<>();

    public void buildAttachments(Map<String, String> formParameters, Map<String, String> storedFiles) {
        String attachMetaRegex = "attachMeta\\[(\\d+)\\]";
        Pattern attachMetaPattern = Pattern.compile(attachMetaRegex);
        Matcher matcher = null;
        ArrayList<Attachment> attachments = new ArrayList<>();
        for (Map.Entry<String, String> formParameter : formParameters.entrySet()) {
            //if it is attachment field
            if ((matcher = attachMetaPattern.matcher(formParameter.getKey())).matches()) {
                matcher = pattern.matcher(formParameter.getValue());
                Map<String, String> parameters = new HashMap<>();
                while (matcher.find()) {
                    parameters.put(matcher.group(1), matcher.group(2));
                }
                String id = parameters.get("id");
                String name = parameters.get("name");
                String uploadDate = parameters.get("uploadDate");
                String comment = parameters.get("comment");
                String fileName = storedFiles.get(formParameter.getKey());
                String status = parameters.get("status");

                Attachment attachment = new Attachment();
                attachment.setId(Long.valueOf(id));
                attachment.setName(name);
                attachment.setComment(comment);
                attachment.setUploadDate(DateTimeParser.parseDate(uploadDate, "dd.MM.yyyy HH:mm:ss"));
                attachment.setFile(fileName);
                switch (status) {
                    case "NEW":
                        newAttachments.add(attachment);
                        break;
                    case "EDITED":
                        updatedAttachments.add(attachment);
                        break;
                    case "NONE":
                        break;
                    case "DELETED":
                        deletedAttachments.add(attachment);
                        break;
                    default:
                }
            }


        }
    }

    public Attachment buildAttachment(Map<String, String> parameters) {
        String id = parameters.get("id");
        String name = parameters.get("name");
        String uploadDate = parameters.get("uploadDate");
        String comment = parameters.get("comment");
        String fileName = parameters.get("fileName");
        String status = parameters.get("status");

        Attachment attachment = new Attachment();
        attachment.setId(Long.valueOf(id));
        attachment.setName(name);
        attachment.setComment(comment);
        attachment.setUploadDate(DateTimeParser.parseDate(uploadDate, "dd.MM.yyyy HH:mm:ss"));
        attachment.setFile(fileName);
        return attachment;
    }

    public ArrayList<Attachment> getNewAttachments() {
        return newAttachments;
    }

    public ArrayList<Attachment> getUpdatedAttachments() {
        return updatedAttachments;
    }

    public ArrayList<Attachment> getDeletedAttachments() {
        return deletedAttachments;
    }

}
