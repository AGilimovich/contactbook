package com.itechart.data.dto;

import com.itechart.data.entity.*;

import java.util.ArrayList;

/**
 * Contact DTO with all relevant fields including address, phones (new, updated and deleted),
 * attachments (new, updated and deleted).
 */
public class FullContactDTO {

    private Contact contact;
    private Address address;
    private File photo;
    private ArrayList<Phone> newPhones;
    private ArrayList<FullAttachmentDTO> newAttachments;
    private ArrayList<Phone> updatedPhones;
    private ArrayList<FullAttachmentDTO> updatedAttachments;
    private ArrayList<Phone> deletedPhones;
    private ArrayList<FullAttachmentDTO> deletedAttachments;

    public FullContactDTO() {
    }

    public FullContactDTO(Contact contact, Address address, File photo) {
        this.contact = contact;
        this.address = address;
        this.photo = photo;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public ArrayList<Phone> getNewPhones() {
        return newPhones;
    }

    public ArrayList<Phone> getPhones() {
        return newPhones;
    }

    public void setNewPhones(ArrayList<Phone> newPhones) {
        this.newPhones = newPhones;
    }

    public void setPhones(ArrayList<Phone> newPhones) {
        this.newPhones = newPhones;
    }


    public ArrayList<FullAttachmentDTO> getNewAttachments() {
        return newAttachments;
    }

    public ArrayList<FullAttachmentDTO> getAttachments() {
        return newAttachments;
    }

    public void setNewAttachments(ArrayList<FullAttachmentDTO> newAttachments) {
        this.newAttachments = newAttachments;
    }

    public void setAttachments(ArrayList<FullAttachmentDTO> newAttachments) {
        this.newAttachments = newAttachments;
    }

    public ArrayList<Phone> getUpdatedPhones() {
        return updatedPhones;
    }

    public void setUpdatedPhones(ArrayList<Phone> updatedPhones) {
        this.updatedPhones = updatedPhones;
    }

    public ArrayList<FullAttachmentDTO> getUpdatedAttachments() {
        return updatedAttachments;
    }

    public void setUpdatedAttachments(ArrayList<FullAttachmentDTO> updatedAttachments) {
        this.updatedAttachments = updatedAttachments;
    }

    public ArrayList<Phone> getDeletedPhones() {
        return deletedPhones;
    }

    public void setDeletedPhones(ArrayList<Phone> deletedPhones) {
        this.deletedPhones = deletedPhones;
    }

    public ArrayList<FullAttachmentDTO> getDeletedAttachments() {
        return deletedAttachments;
    }

    public void setDeletedAttachments(ArrayList<FullAttachmentDTO> deletedAttachments) {
        this.deletedAttachments = deletedAttachments;
    }
}
