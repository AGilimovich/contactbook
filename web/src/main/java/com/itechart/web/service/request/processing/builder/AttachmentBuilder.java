package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.Attachment;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.validation.AbstractValidationService;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
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
        AbstractValidationService validationService = ServiceFactory.getInstance().getValidationService();

        attachment.setUploadDate(new Date());

        if (StringUtils.isNotBlank(idParam)) {
            if (validationService.validateId(idParam))
                try {
                    attachment.setId(Long.valueOf(idParam));
                } catch (Exception e){
                    throw new ValidationException("Invalid attachment id", e);
                }
            else throw new ValidationException("Invalid attachment id");
        }
        if (StringUtils.isNotBlank(nameParam)) {
            if (validationService.validateField(nameParam)) {
                attachment.setName(nameParam);
            } else {
                throw new ValidationException("Invalid name field value");
            }
        }
        if (StringUtils.isNotBlank(commentParam)) {
            if (validationService.validateField(commentParam)) {
                attachment.setComment(commentParam);
            } else {
                throw new ValidationException("Invalid attachment comment field value");
            }
        }


        return attachment;
    }


}
