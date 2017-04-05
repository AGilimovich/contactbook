package com.itechart.web.service.request.processing.builder;

import com.itechart.data.dto.FullAttachmentDTO;
import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.entity.*;
import com.itechart.web.properties.PropertiesManager;
import com.itechart.web.service.request.processing.FilePartWriter;
import com.itechart.web.service.request.processing.parser.AttachmentFormFieldParser;
import com.itechart.web.service.request.processing.parser.PhoneFormFieldParser;
import com.itechart.web.service.validation.ValidationException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for building DTO object.
 */
public class FullContactDTOBuilder {
    private Logger logger = LoggerFactory.getLogger(FullContactDTOBuilder.class);

    private Contact contact;
    private Address address;
    private File photo;

    private ArrayList<Phone> newPhones = new ArrayList<>();
    private ArrayList<Phone> updatedPhones = new ArrayList<>();
    private ArrayList<Phone> deletedPhones = new ArrayList<>();

    private ArrayList<FullAttachmentDTO> newAttachments = new ArrayList<>();
    private ArrayList<FullAttachmentDTO> updatedAttachments = new ArrayList<>();
    private ArrayList<FullAttachmentDTO> deletedAttachments = new ArrayList<>();
    private FilePartWriter writer = new FilePartWriter(PropertiesManager.FILE_PATH());
    private ArrayList<String> storedFiles = new ArrayList<>();

    public void build(Map<String, String> formFields, Map<String, FileItem> fileParts) throws ValidationException {
        if (formFields != null) {
            buildContact(formFields);
            buildAddress(formFields);
            buildPhones(formFields);
            buildPhoto(fileParts);
            buildAttachments(formFields, fileParts);
        }
    }


    private void buildPhoto(Map<String, FileItem> fileParts) {
        logger.info("Build photo");
        if (fileParts == null) return;
        FileItem photoFileItem = fileParts.get("photoFile");
        if (photoFileItem != null && photoFileItem.getSize() != 0) {
            String storedName = writer.writeFilePart(photoFileItem);
            storedFiles.add(storedName);
            Map<String, String> parameters = new HashMap() {{
                put("realName", photoFileItem.getName());
                put("storedName", storedName);
            }};
            PhotoFileBuilder builder = new PhotoFileBuilder();
            photo = builder.buildFile(parameters);
        }
    }

    private void buildContact(Map<String, String> formFields) throws ValidationException {
        logger.info("Build contact from form fields: {}", formFields);
        if (formFields == null) return;
        ContactBuilder contactBuilder = new ContactBuilder();
        contact = contactBuilder.buildContact(formFields);
    }

    private void buildAddress(Map<String, String> formFields) {
        logger.info("Build address from form fields: {}", formFields);
        if (formFields == null) return;
        AddressBuilder addressBuilder = new AddressBuilder();
        address = addressBuilder.buildAddress(formFields);
    }

    private void buildPhones(Map<String, String> formFields) throws ValidationException {
        logger.info("Build phones from form fields: {}", formFields);

        if (formFields == null) return;

        PhoneFormFieldParser parser = new PhoneFormFieldParser();
        String fieldNameRegex = "phone\\[(\\d+)\\]";
        Pattern fieldNamePattern = Pattern.compile(fieldNameRegex);
        PhoneBuilder phoneBuilder = new PhoneBuilder();
        for (Map.Entry<String, String> requestParam : formFields.entrySet()) {
            //if phone parameters received in request
            if (fieldNamePattern.matcher(requestParam.getKey()).matches()) {
                Map<String, String> parameters = parser.parse(requestParam.getValue());
                Phone phone = phoneBuilder.buildPhone(parameters);
                String status = parameters.get("status");
                if (StringUtils.isNotBlank(status)) {
                    switch (status) {
                        case "NEW":
                            newPhones.add(phone);
                            break;
                        case "EDITED":
                            updatedPhones.add(phone);
                            break;
                        case "NONE":
                            break;
                        case "DELETED":
                            deletedPhones.add(phone);
                            break;
                        default:
                    }
                }

            }
        }
    }

    private void buildAttachments(Map<String, String> formFields, Map<String, FileItem> fileParts) throws ValidationException {
        logger.info("Build attachments from form fields: {}", formFields);
        if (formFields == null || fileParts == null) return;
        AttachmentBuilder attachmentBuilder = new AttachmentBuilder();
        AttachFileBuilder fileBuilder = new AttachFileBuilder();
        String fieldNameRegex = "attachMeta\\[(\\d+)\\]";
        Pattern fieldNamePattern = Pattern.compile(fieldNameRegex);
        AttachmentFormFieldParser parser = new AttachmentFormFieldParser();
        Matcher matcher = null;
        for (Map.Entry<String, String> formParameter : formFields.entrySet()) {
            //if it is attachment field
            if ((matcher = fieldNamePattern.matcher(formParameter.getKey())).matches()) {
                Map<String, String> parameters = parser.parse(formParameter.getValue());
                String fileFieldNumber = matcher.group(1);
                Attachment attachment = attachmentBuilder.buildAttachment(parameters);
                String status = parameters.get("status");
                if (StringUtils.isNotBlank(status)) {
                    switch (status) {
                        case "NEW":
                            File file = null;
                            if (fileFieldNumber != null) {
                                FileItem attachFileItem = fileParts.get("attachFile[" + fileFieldNumber + "]");
                                if (attachFileItem != null) {
                                    String storedName = writer.writeFilePart(attachFileItem);
                                    storedFiles.add(storedName);
                                    Map<String, String> filePartsParameters = new HashMap() {{
                                        put("realName", attachFileItem.getName());
                                        put("storedName", storedName);
                                    }};
                                    file = fileBuilder.buildFile(filePartsParameters);
                                }
                            }
                            if (file == null) return;
                            newAttachments.add(new FullAttachmentDTO(attachment, file));
                            break;
                        case "EDITED":
                            updatedAttachments.add(new FullAttachmentDTO(attachment, null));
                            break;
                        case "NONE":
                            break;
                        case "DELETED":
                            deletedAttachments.add(new FullAttachmentDTO(attachment, null));
                            break;
                        default:
                    }
                }
            }
        }
    }


    public FullContactDTO getFullContact() {
        FullContactDTO fullContactDTO = new FullContactDTO(contact, address, photo);
        fullContactDTO.setNewPhones(newPhones);
        fullContactDTO.setDeletedPhones(deletedPhones);
        fullContactDTO.setUpdatedPhones(updatedPhones);
        fullContactDTO.setNewAttachments(newAttachments);
        fullContactDTO.setDeletedAttachments(deletedAttachments);
        fullContactDTO.setUpdatedAttachments(updatedAttachments);
        return fullContactDTO;

    }

    public Collection<String> getStoredFiles() {
        return storedFiles;
    }


}
