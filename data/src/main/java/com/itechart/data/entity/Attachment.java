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
    private String file;

    public long getContact() {
        return contact;
    }

    private long contact;

    public Attachment(int id, String name, Date uploadDate, String comment, String file, long contact) {
        this.id = id;
        this.name = name;
        this.uploadDate = uploadDate;
        this.comment = comment;
        this.file = file;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public String getComment() {
        return comment;
    }

    public String getFile() {
        return file;
    }
}
