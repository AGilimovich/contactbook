package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Attachment;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.request.processing.parser.AttachmentFormFieldParser;
import com.itechart.web.service.validation.AbstractValidationService;
import com.itechart.web.service.validation.ValidationException;
import com.itechart.web.service.validation.ValidationService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * Created by Aleksandr on 17.03.2017.
 */
public class AttachmentBuilder {
    private Logger logger = LoggerFactory.getLogger(AttachmentBuilder.class);

    public Attachment buildAttachment(Map<String, String> parameters) throws ValidationException {
        logger.info("Build attachment entity with parameters: {}", parameters);
        String idParam = StringUtils.trim(parameters.get("id"));
        String nameParam = StringUtils.trim(parameters.get("name"));
//        String uploadDateParam = StringUtils.trim(parameters.get("uploadDate"));
        String commentParam = StringUtils.trim(parameters.get("comment"));
        Attachment attachment = new Attachment();
        AbstractValidationService validationService = ServiceFactory.getServiceFactory().getValidationService();
//        DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");
//        if (StringUtils.isNotBlank(uploadDateParam)) {
//            DateTime dateTime = null;
//            try {
//                dateTime = format.parseDateTime(uploadDateParam);
//            } catch (Exception e) {
//                throw new ValidationException("Date has illegal format");
//            }
//            if (dateTime != null)
//                attachment.setUploadDate(dateTime.toDate());
//        }
        attachment.setUploadDate(new Date());

        if (StringUtils.isNotBlank(idParam)) {
            if (validationService.validateId(idParam))
                attachment.setId(Long.valueOf(idParam));
            else throw new ValidationException("Invalid id of phone");
        }
        if (StringUtils.isNotBlank(nameParam)) {
            attachment.setName(nameParam);
        }
        if (StringUtils.isNotBlank(commentParam)) {
            attachment.setComment(commentParam);
        }


        return attachment;
    }


}
