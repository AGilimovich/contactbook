package com.itechart.data.dto;

import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.File;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class FullAttachmentDTO {
    private Attachment attachment;
    private File file;

    public FullAttachmentDTO(Attachment attachment, File file) {
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
