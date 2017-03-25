package com.itechart.data.dto;

import com.itechart.data.entity.*;

import java.util.ArrayList;

/**
 * Contact DTO with all relevant fields including address, phones (new, updated and deleted),
 * attachments (new, updated and deleted).
 */
public class FullContact {

    private Contact contact;
    private Address address;
    private ContactFile photo;
    private ArrayList<Phone> newPhones;
    private ArrayList<FullAttachment> newAttachments;
    private ArrayList<Phone> updatedPhones;
    private ArrayList<FullAttachment> updatedAttachments;
    private ArrayList<Phone> deletedPhones;
    private ArrayList<FullAttachment> deletedAttachments;

    public FullContact() {
    }

    public FullContact(Contact contact, Address address, ContactFile photo) {
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

    public ContactFile getPhoto() {
        return photo;
    }

    public void setPhoto(ContactFile photo) {
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


    public ArrayList<FullAttachment> getNewAttachments() {
        return newAttachments;
    }

    public ArrayList<FullAttachment> getAttachments() {
        return newAttachments;
    }

    public void setNewAttachments(ArrayList<FullAttachment> newAttachments) {
        this.newAttachments = newAttachments;
    }

    public void setAttachments(ArrayList<FullAttachment> newAttachments) {
        this.newAttachments = newAttachments;
    }

    public ArrayList<Phone> getUpdatedPhones() {
        return updatedPhones;
    }

    public void setUpdatedPhones(ArrayList<Phone> updatedPhones) {
        this.updatedPhones = updatedPhones;
    }

    public ArrayList<FullAttachment> getUpdatedAttachments() {
        return updatedAttachments;
    }

    public void setUpdatedAttachments(ArrayList<FullAttachment> updatedAttachments) {
        this.updatedAttachments = updatedAttachments;
    }

    public ArrayList<Phone> getDeletedPhones() {
        return deletedPhones;
    }

    public void setDeletedPhones(ArrayList<Phone> deletedPhones) {
        this.deletedPhones = deletedPhones;
    }

    public ArrayList<FullAttachment> getDeletedAttachments() {
        return deletedAttachments;
    }

    public void setDeletedAttachments(ArrayList<FullAttachment> deletedAttachments) {
        this.deletedAttachments = deletedAttachments;
    }
}
