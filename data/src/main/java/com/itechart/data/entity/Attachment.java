package com.itechart.data.entity;

import java.util.Date;

/**
 * Represents attachments added by contact.
 */
public class Attachment {
    private int id;


    private String name;
    private Date uploadDate;
    private String comment;
    private byte[] file;
}
