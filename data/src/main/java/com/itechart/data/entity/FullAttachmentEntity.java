package com.itechart.data.entity;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class FullAttachmentEntity {
    private Attachment attachment;
    private File file;

    public FullAttachmentEntity(Attachment attachment, File file) {
        this.attachment = attachment;
        this.file = file;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
