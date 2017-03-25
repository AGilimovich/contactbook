package com.itechart.data.dto;

import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.ContactFile;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class FullAttachment {
    private Attachment attachment;
    private ContactFile file;

    public FullAttachment(Attachment attachment, ContactFile file) {
        this.attachment = attachment;
        this.file = file;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public ContactFile getFile() {
        return file;
    }

    public void setFile(ContactFile file) {
        this.file = file;
    }
}
