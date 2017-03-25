package com.itechart.web.service.request.processing;

import com.itechart.data.entity.Attachment;

import java.util.Map;

/**
 * Created by Aleksandr on 17.03.2017.
 */
public class AttachmentBuilder {

    public Attachment buildAttachment(Map<String, String> parameters) {
        String id = parameters.get("id");
        String name = parameters.get("name");
        String uploadDate = parameters.get("uploadDate");
        String comment = parameters.get("comment");
        Attachment attachment = new Attachment();
        attachment.setId(Long.valueOf(id));
        attachment.setName(name);
        attachment.setComment(comment);
        attachment.setUploadDate(DateTimeParser.parseDate(uploadDate, "dd.MM.yyyy HH:mm:ss"));
        return attachment;
    }


}
