package com.itechart.data.dto;

import com.itechart.data.entity.*;

import java.util.ArrayList;

/**
 * Data transfer object which includes contact object and all objects related to contact.
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

      public FullContactDTO(Contact contact, Address address, File photo) {
        this.contact = contact;
        this.address = address;
        this.photo = photo;
    }

    public void update(FullContactDTO newFullContact) {
        if (newFullContact == null) return;
        this.contact.update(newFullContact.getContact());
        this.address.update(newFullContact.getAddress());
        if (newFullContact.getPhoto() == null)
            this.photo = null;
        else
            this.photo.update(newFullContact.getPhoto());
        this.newAttachments = newFullContact.getNewAttachments();
        this.updatedAttachments = newFullContact.getUpdatedAttachments();
        this.deletedAttachments = newFullContact.getDeletedAttachments();
        this.newPhones = newFullContact.getNewPhones();
        this.deletedPhones = newFullContact.getDeletedPhones();
        this.updatedPhones = newFullContact.getUpdatedPhones();
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
