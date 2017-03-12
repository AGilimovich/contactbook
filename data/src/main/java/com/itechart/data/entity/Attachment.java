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

    public Attachment(int id, String name, Date uploadDate, String comment, String file) {
        this.id = id;
        this.name = name;
        this.uploadDate = uploadDate;
        this.comment = comment;
        this.file = file;
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
