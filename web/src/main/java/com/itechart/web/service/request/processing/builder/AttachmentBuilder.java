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
        String idParam = parameters.get("id");
        String nameParam = parameters.get("name");
        String uploadDateParam = parameters.get("uploadDate");
        String commentParam = parameters.get("comment");
        Attachment attachment = new Attachment();

        if (StringUtils.isNotEmpty(idParam)){
            attachment.setId(Long.valueOf(idParam));
        }
        if (StringUtils.isNotEmpty(nameParam)){
            attachment.setName(nameParam);
        }
        if (StringUtils.isNotEmpty(commentParam)){
            attachment.setComment(commentParam);
        }

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
