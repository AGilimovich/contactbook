package com.itechart.data.dto;

import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;

import java.util.ArrayList;

/**
 * Contact DTO with all relevant fields including address, phones (new, updated and deleted),
 * attachments (new, updated and deleted).
 *
 */
public class FullContact {

    private Contact contact;
    private Address address;
    private ArrayList<Phone> newPhones;
    private ArrayList<Attachment> newAttachments;
    private ArrayList<Phone> updatedPhones;
    private ArrayList<Attachment> updatedAttachments;
    private ArrayList<Phone> deletedPhones;
    private ArrayList<Attachment> deletedAttachments;

    public FullContact() {
    }

    public FullContact(Contact contact, Address address, ArrayList<Phone> newPhones, ArrayList<Attachment> newAttachments) {
        this.contact = contact;
        this.address = address;
        this.newPhones = newPhones;
        this.newAttachments = newAttachments;
    }

    public ArrayList<Phone> getPhones() {
        return newPhones;
    }
    public ArrayList<Attachment> getAttachments() {
        return newAttachments;
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

    public ArrayList<Phone> getNewPhones() {
        return newPhones;
    }

    public void setNewPhones(ArrayList<Phone> newPhones) {
        this.newPhones = newPhones;
    }

    public ArrayList<Attachment> getNewAttachments() {
        return newAttachments;
    }

    public void setNewAttachments(ArrayList<Attachment> newAttachments) {
        this.newAttachments = newAttachments;
    }

    public ArrayList<Phone> getUpdatedPhones() {
        return updatedPhones;
    }

    public void setUpdatedPhones(ArrayList<Phone> updatedPhones) {
        this.updatedPhones = updatedPhones;
    }

    public ArrayList<Attachment> getUpdatedAttachments() {
        return updatedAttachments;
    }

    public void setUpdatedAttachments(ArrayList<Attachment> updatedAttachments) {
        this.updatedAttachments = updatedAttachments;
    }

    public ArrayList<Phone> getDeletedPhones() {
        return deletedPhones;
    }

    public void setDeletedPhones(ArrayList<Phone> deletedPhones) {
        this.deletedPhones = deletedPhones;
    }

    public ArrayList<Attachment> getDeletedAttachments() {
        return deletedAttachments;
    }

    public void setDeletedAttachments(ArrayList<Attachment> deletedAttachments) {
        this.deletedAttachments = deletedAttachments;
    }
}
