package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Attachment;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Map;

/**
 * Created by Aleksandr on 17.03.2017.
 */
public class AttachmentBuilder {

    public Attachment buildAttachment(Map<String, String> parameters) {
        String id = parameters.get("id");
        String name = parameters.get("name");
        String uploadDateParam = parameters.get("uploadDate");
        String comment = parameters.get("comment");
        Attachment attachment = new Attachment();
        attachment.setId(Long.valueOf(id));
        attachment.setName(name);
        attachment.setComment(comment);

        DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");
        Date uploadDate = null;
        if (StringUtils.isNotEmpty(uploadDateParam)) {
            DateTime dateTime = format.parseDateTime(uploadDateParam);
            if (dateTime != null)
                uploadDate = dateTime.toDate();
        }

        attachment.setUploadDate(uploadDate);
        return attachment;
    }


}
