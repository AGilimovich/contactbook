package com.itechart.data.entity;

import java.util.Date;

/**
 * Attachment added by contact.
 */
public class Attachment {
    private long id;
    private String name;
    private Date uploadDate;
    private String comment;
    private long file;

    public long getContact() {
        return contact;
    }

    private long contact;

    public Attachment(long id, String name, Date uploadDate, String comment, long file, long contact) {
        this.id = id;
        this.name = name;
        this.uploadDate = uploadDate;
        this.comment = comment;
        this.file = file;
        this.contact = contact;
    }

    public Attachment() {
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uploadDate=" + uploadDate +
                ", comment='" + comment + '\'' +
                ", file=" + file +
                ", contact=" + contact +
                '}';
    }

    public void update(Attachment attachment){
        if (attachment == null) return;
        this.name = attachment.getName();
        this.uploadDate = attachment.getUploadDate();
        this.comment = attachment.getComment();
    }

    public long getId() {
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

    public long getFile() {
        return file;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setFile(long file) {
        this.file = file;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }
}
