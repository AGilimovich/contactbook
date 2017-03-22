package com.itechart.web.handler;

import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for parsing entity objects from request form fields.
 */
public class FormFieldsParser {


    private Contact contact;
    private Address address;
    private ArrayList<Phone> newPhones = new ArrayList<>();
    private ArrayList<Phone> updatedPhones = new ArrayList<>();
    private ArrayList<Phone> deletedPhones = new ArrayList<>();

    private ArrayList<Attachment> newAttachments = new ArrayList<>();
    private ArrayList<Attachment> updatedAttachments = new ArrayList<>();
    private ArrayList<Attachment> deletedAttachments = new ArrayList<>();


    public FormFieldsParser(Map<String, String> formFields, Map<String, String> storedFiles) {
        parseContact(formFields, storedFiles);
        parseAddress(formFields);
        parsePhones(formFields);
        parseAttachments(formFields, storedFiles);

    }


    private void parseContact(Map<String, String> formFields, Map<String, String> storedFiles) {
        ContactBuilder contactBuilder = new ContactBuilder();
        contact = contactBuilder.buildContact(formFields, storedFiles);
    }

    private void parseAddress(Map<String, String> formFields) {
        AddressBuilder addressBuilder = new AddressBuilder();
        address = addressBuilder.buildAddress(formFields);
    }

    private void parsePhones(Map<String, String> formFields) {
        String formFieldRegex = "(\\w+)=(\\+*\\w*)&?";
        Pattern formFieldpattern = Pattern.compile(formFieldRegex);

        String fieldNameRegex = "phone\\[(\\d+)\\]";
        Pattern fieldNamePattern = Pattern.compile(fieldNameRegex);
        PhoneBuilder phoneBuilder = new PhoneBuilder();
        for (Map.Entry<String, String> requestParam : formFields.entrySet()) {
            //if phone parameters received in request
            Matcher matcher = fieldNamePattern.matcher(requestParam.getKey());
            if (matcher.matches()) {
                matcher = formFieldpattern.matcher(requestParam.getValue());
                Map<String, String> parameters = new HashMap<>();
                while (matcher.find()) {
                    parameters.put(matcher.group(1), matcher.group(2));
                }
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

    private void parseAttachments(Map<String, String> formFields, Map<String, String> storedFiles) {
        String formFieldRegex = "(\\w+)=([\\w\\d\\s\\.\\-:]*)&?";
        Pattern formFieldpattern = Pattern.compile(formFieldRegex);
        AttachmentBuilder attachmentBuilder = new AttachmentBuilder();
        String fieldNameRegex = "attachMeta\\[(\\d+)\\]";
        Pattern fieldNamePattern = Pattern.compile(fieldNameRegex);
        Matcher matcher = null;
        ArrayList<Attachment> attachments = new ArrayList<>();
        for (Map.Entry<String, String> formParameter : formFields.entrySet()) {
            //if it is attachment field
            if ((matcher = fieldNamePattern.matcher(formParameter.getKey())).matches()) {
                matcher = formFieldpattern.matcher(formParameter.getValue());
                Map<String, String> parameters = new HashMap<>();
                while (matcher.find()) {
                    parameters.put(matcher.group(1), matcher.group(2));
                }
                parameters.put("fileName", storedFiles.get(formParameter.getKey()));// TODO: 22.03.2017
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

    public Contact getContact() {
        return contact;
    }
    public Address getAddress() {
        return address;
    }

    public ArrayList<Phone> getNewPhones() {
        return newPhones;
    }

    public ArrayList<Attachment> getNewAttachments() {
        return newAttachments;
    }

    public ArrayList<Phone> getUpdatedPhones() {
        return updatedPhones;
    }

    public ArrayList<Attachment> getUpdatedAttachments() {
        return updatedAttachments;
    }

    public ArrayList<Phone> getDeletedPhones() {
        return deletedPhones;
    }

    public ArrayList<Attachment> getDeletedAttachments() {
        return deletedAttachments;
    }


}
