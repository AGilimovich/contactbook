package com.itechart.web.parser;

import com.itechart.data.entity.Attachment;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 17.03.2017.
 */
public class AttachmentParser {
    public ArrayList<Attachment> parseAttachments(ArrayList<String> attachmentRequestParameters) {
        ArrayList<Attachment> attachments = new ArrayList<>();

//        if (!attachmentRequestParameters.isEmpty()) {
//            for (String attachmentParameter : attachmentRequestParameters) {
//                try {
//                    Attachment attachment = AttachmentRequestParamParser.parseRequest(attachmentParameter);
//                    attachments.add(attachment);
//                } catch (com.itechart.web.parser.ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return attachments;
    }
}
