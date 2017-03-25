package com.itechart.web.service.request.processing;

import com.itechart.data.dto.FullContact;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for parsing entity objects from request form fields.
 */
public class FullContactBuilder {


    private Contact contact;
    private Address address;
    private ArrayList<Phone> newPhones = new ArrayList<>();
    private ArrayList<Phone> updatedPhones = new ArrayList<>();
    private ArrayList<Phone> deletedPhones = new ArrayList<>();

    private ArrayList<Attachment> newAttachments = new ArrayList<>();
    private ArrayList<Attachment> updatedAttachments = new ArrayList<>();
    private ArrayList<Attachment> deletedAttachments = new ArrayList<>();


    public FullContactBuilder(Map<String, String> formFields, Map<String, String> storedFiles) {
        if (formFields != null && storedFiles != null) {
            buildContact(formFields, storedFiles);
            buildAddress(formFields);
            buildPhones(formFields);
            buildAttachments(formFields, storedFiles);
        }
    }


    private void buildContact(Map<String, String> formFields, Map<String, String> storedFiles) {
        if (formFields == null || storedFiles == null) return;
        ContactBuilder contactBuilder = new ContactBuilder();
        //set photo
        for (Map.Entry<String, String> entry : storedFiles.entrySet()) {
            if (entry.getKey().equals("photoFile")) {
                String name = entry.getValue();
                if (name != null)
                    formFields.put("photo", name);
            }
        }
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
        String fieldNameRegex = "attachMeta\\[(\\d+)\\]";
        Pattern fieldNamePattern = Pattern.compile(fieldNameRegex);
        AttachmentFormFieldParser parser = new AttachmentFormFieldParser();
        Matcher matcher = null;
        for (Map.Entry<String, String> formParameter : formFields.entrySet()) {
            //if it is attachment field
            if ((matcher = fieldNamePattern.matcher(formParameter.getKey())).matches()) {
                Map<String, String> parameters = parser.parse(formParameter.getValue());
                String fileFieldNumber = matcher.group(1);
                //add to parameters file name
                parameters.put("fileName", storedFiles.get("attachFile[" + fileFieldNumber + "]"));
                Attachment attachment = attachmentBuilder.buildAttachment(parameters);
                String status = parameters.get("status");
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

    public FullContact getFullContact() {
        FullContact fullContact = new FullContact(contact, address, newPhones, newAttachments);
        fullContact.setDeletedPhones(deletedPhones);
        fullContact.setUpdatedPhones(updatedPhones);
        fullContact.setDeletedAttachments(deletedAttachments);
        fullContact.setUpdatedAttachments(updatedAttachments);
        return fullContact;

    }


}
