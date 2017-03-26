package com.itechart.data.entity;

import java.util.Date;

/**
 * Represents attachments added by contact.
 */
public class Attachment {
    private long id;
    private String name;
    private Date uploadDate;
    private String comment;
    //file_id in file_storage table
    private long fileId;

    public long getContact() {
        return contact;
    }

    private long contact;

    public Attachment(long id, String name, Date uploadDate, String comment, long fileId, long contact) {
        this.id = id;
        this.name = name;
        this.uploadDate = uploadDate;
        this.comment = comment;
        this.fileId = fileId;
        this.contact = contact;
    }

    public Attachment() {
    }

    public void update(Attachment attachment){
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

    public long getFileId() {
        return fileId;
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

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }
}
