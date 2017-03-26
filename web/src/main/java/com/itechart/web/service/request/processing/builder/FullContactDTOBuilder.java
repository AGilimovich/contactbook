package com.itechart.web.service.request.processing.builder;

import com.itechart.data.entity.FullAttachmentEntity;
import com.itechart.data.entity.FullContactEntity;
import com.itechart.data.entity.*;
import com.itechart.web.service.request.processing.parser.AttachmentFormFieldParser;
import com.itechart.web.service.request.processing.parser.PhoneFormFieldParser;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for building DTO object.
 */
public class FullContactDTOBuilder {

    private Contact contact;
    private Address address;
    private File photo;

    private ArrayList<Phone> newPhones = new ArrayList<>();
    private ArrayList<Phone> updatedPhones = new ArrayList<>();
    private ArrayList<Phone> deletedPhones = new ArrayList<>();

    private ArrayList<FullAttachmentEntity> newAttachments = new ArrayList<>();
    private ArrayList<FullAttachmentEntity> updatedAttachments = new ArrayList<>();
    private ArrayList<FullAttachmentEntity> deletedAttachments = new ArrayList<>();


    public FullContactDTOBuilder(Map<String, String> formFields, Map<String, String> storedFiles) {
        if (formFields != null && storedFiles != null) {
            buildPhoto(storedFiles);
            buildContact(formFields);
            buildAddress(formFields);
            buildPhones(formFields);
            buildAttachments(formFields, storedFiles);
        }
    }

    private void buildPhoto(Map<String, String> storedFiles) {
        if (storedFiles == null) return;
        PhotoFileBuilder builder = new PhotoFileBuilder();
        photo = builder.buildFile(storedFiles);
    }

    private void buildContact(Map<String, String> formFields) {
        if (formFields == null) return;
        ContactBuilder contactBuilder = new ContactBuilder();
        contact = contactBuilder.buildContact(formFields);
    }

    private void buildAddress(Map<String, String> formFields) {
        if (formFields == null) return;
        AddressBuilder addressBuilder = new AddressBuilder();
        address = addressBuilder.buildAddress(formFields);
    }

    private void buildPhones(Map<String, String> formFields) {
        if (formFields == null) return;

        PhoneFormFieldParser parser = new PhoneFormFieldParser();
        String fieldNameRegex = "phone\\[(\\d+)\\]";
        Pattern fieldNamePattern = Pattern.compile(fieldNameRegex);
        PhoneBuilder phoneBuilder = new PhoneBuilder();
        for (Map.Entry<String, String> requestParam : formFields.entrySet()) {
            //if phone parameters received in request
            if (fieldNamePattern.matcher(requestParam.getKey()).matches()) {
                Map<String, String> parameters = parser.parse(requestParam.getValue());
//                switch (status) {
//                    case "NEW":
//                        newPhones.add(phone);
//                        break;
//                    case "EDITED":
//                        updatedPhones.add(phone);
//                        break;
//                    case "NONE":
//                        break;
//                    case "DELETED":
//                        deletedPhones.add(phone);
//                        break;
//                    default:
//                }
                newPhones.add(phoneBuilder.buildPhone(parameters));
            }
        }
    }

    private void buildAttachments(Map<String, String> formFields, Map<String, String> storedFiles) {
        if (formFields == null || storedFiles == null) return;
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
                //add file name to parameters
                File file = fileBuilder.buildFile(storedFiles, fileFieldNumber);
                Attachment attachment = attachmentBuilder.buildAttachment(parameters);
                FullAttachmentEntity fullAttachmentEntity = new FullAttachmentEntity(attachment, file);
                String status = parameters.get("status");
                switch (status) {
                    case "NEW":
                        newAttachments.add(fullAttachmentEntity);
                        break;
                    case "EDITED":
                        updatedAttachments.add(fullAttachmentEntity);
                        break;
                    case "NONE":
                        break;
                    case "DELETED":
                        deletedAttachments.add(fullAttachmentEntity);
                        break;
                    default:
                }
            }
        }
    }


    public FullContactEntity getFullContactDTO() {
        FullContactEntity fullContactEntity = new FullContactEntity(contact, address, photo);
        fullContactEntity.setNewPhones(newPhones);
        fullContactEntity.setNewAttachments(newAttachments);
        fullContactEntity.setDeletedPhones(deletedPhones);
        fullContactEntity.setUpdatedPhones(updatedPhones);
        fullContactEntity.setDeletedAttachments(deletedAttachments);
        fullContactEntity.setUpdatedAttachments(updatedAttachments);
        return fullContactEntity;

    }


}
