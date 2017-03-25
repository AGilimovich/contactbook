package com.itechart.web.service;

import com.itechart.data.dao.*;
import com.itechart.data.dto.ContactWithAddressDTO;
import com.itechart.data.dto.FullAttachment;
import com.itechart.data.dto.FullContact;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.*;
import com.itechart.data.transaction.Transaction;
import com.itechart.data.transaction.TransactionManager;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aleksandr on 22.03.2017.
 */
public class DataService {

    private TransactionManager tm;

    public DataService(TransactionManager tm) {
        this.tm = tm;


    }

    public void deleteContact(long contactId) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        phoneDao.deleteForUser(contactId);
        attachmentDao.deleteForUser(contactId);
        // TODO: 23.03.2017 delete from disk
        long addressId = contactDao.getContactById(contactId).getAddress();
        contactDao.delete(contactId);
        addressDao.delete(addressId);
        transaction.commitTransaction();
    }


    public void saveNewContact(FullContact fullContact) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcContactFileDao fileDao = new JdbcContactFileDao(transaction);

        Address address = fullContact.getAddress();
        ArrayList<Phone> phones = fullContact.getNewPhones();
        ArrayList<FullAttachment> attachments = fullContact.getNewAttachments();
        ContactFile photo = fullContact.getPhoto();
        Contact contact = fullContact.getContact();

//        if (photo != null) {
        long photoId = fileDao.save(photo);
        contact.setPhoto(photoId);
//        }
        long addressId = addressDao.save(address);
        contact.setAddress(addressId);
        long contactId = contactDao.save(contact);
        for (Phone phone : phones) {
            phone.setContact(contactId);
            phoneDao.save(phone);
        }
        for (FullAttachment fullAttachment : attachments) {
            ContactFile file = fullAttachment.getFile();
            long fileId = fileDao.save(file);
            Attachment attachment = fullAttachment.getAttachment();
            attachment.setContact(contactId);
            attachment.setFile(fileId);
            attachmentDao.save(attachment);
        }
        transaction.commitTransaction();
    }


    public void updateContact(FullContact fullContact) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcContactFileDao fileDao = new JdbcContactFileDao(transaction);
        Contact contactToUpdate = contactDao.getContactById(fullContact.getContact().getContactId());
        ContactFile photo = fullContact.getPhoto();
        photo.setId(contactToUpdate.getPhoto());
        //if received new photo file
        if (photo.getStoredName() != null && photo.getName() != null) {
            // TODO: 25.03.2017 remove old file from file system
            fileDao.update(photo);
        }

        Address addressToUpdate = addressDao.getAddressById(contactToUpdate.getAddress());
        //update fields with new data
        contactToUpdate.update(fullContact.getContact());
        addressToUpdate.update(fullContact.getAddress());
        //update in db
        addressDao.update(addressToUpdate);
        contactDao.update(contactToUpdate);

        //delete old phones
        phoneDao.deleteForUser(fullContact.getContact().getContactId());
        //persist into db new phones
        for (Phone phone : fullContact.getNewPhones()) {
            phone.setContact(fullContact.getContact().getContactId());
            phoneDao.save(phone);
        }

        for (FullAttachment fullAttachment : fullContact.getDeletedAttachments()) {
            fileDao.delete(fullAttachment.getFile().getId());
            attachmentDao.delete(fullAttachment.getAttachment().getId());
            // TODO: 23.03.2017 delete from disk
        }
        for (FullAttachment fullAttachment : fullContact.getNewAttachments()) {
            ContactFile file = fullAttachment.getFile();
            long fileId = fileDao.save(file);
            Attachment attachment = fullAttachment.getAttachment();
            attachment.setFile(fileId);
            attachment.setContact(fullContact.getContact().getContactId());
            attachmentDao.save(attachment);
        }
        for (FullAttachment fullAttachment : fullContact.getUpdatedAttachments()) {
            ContactFile file = fullAttachment.getFile();
            if (file != null)
                fileDao.update(file);
            attachmentDao.update(fullAttachment.getAttachment());
        }
        transaction.commitTransaction();

    }


    public ArrayList<Contact> getContactsWithBirthday(Date date) {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = contactDao.getByBirthDate(date);
        transaction.commitTransaction();
        return contacts;
    }

    public ArrayList<Contact> getContactsByFields(SearchDTO dto) {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = contactDao.findContactsByFields(dto);
        transaction.commitTransaction();
        return contacts;
    }

    public Contact getContactById(long contactId) {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        Contact contact = contactDao.getContactById(contactId);
        transaction.commitTransaction();
        return contact;
    }


    public Address getAddressById(long addressId) {
        Transaction transaction = tm.getTransaction();
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        Address address = addressDao.getAddressById(addressId);
        transaction.commitTransaction();
        return address;
    }
    public ContactFile getPhotoById(long photoId) {
        Transaction transaction = tm.getTransaction();
        JdbcContactFileDao fileDao = new JdbcContactFileDao(transaction);
        ContactFile photo = fileDao.getFileById(photoId);
        transaction.commitTransaction();
        return photo;
    }


    public ArrayList<ContactWithAddressDTO> getContactsWithAddressDTO() {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcContactFileDao fileDao = new JdbcContactFileDao(transaction);
        ArrayList<Contact> contacts = contactDao.getAll();
        ArrayList<ContactWithAddressDTO> contactWithAddressDTOs = new ArrayList<>();
        for (Contact contact : contacts) {
            ContactFile photo = fileDao.getFileById(contact.getPhoto());
            Address address = addressDao.getAddressById(contact.getAddress());
            ContactWithAddressDTO contactWithAddressDTO = new ContactWithAddressDTO(contact, address, photo);
            contactWithAddressDTOs.add(contactWithAddressDTO);
        }
        transaction.commitTransaction();
        return contactWithAddressDTOs;
    }


    public FullContact getFullContactById(long contactId) {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcContactFileDao fileDao = new JdbcContactFileDao(transaction);
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);

        Contact contact = contactDao.getContactById(contactId);
        Address address = addressDao.getAddressById(contactId);
        ContactFile photo = fileDao.getFileById(contact.getPhoto());
        ArrayList<Phone> phones = phoneDao.getAllForContact(contactId);

        ArrayList<FullAttachment> fullAttachments = new ArrayList<>();
        ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);
        for (Attachment attachment : attachments) {
            ContactFile file = fileDao.getFileById(attachment.getFile());
            FullAttachment fullAttachment = new FullAttachment(attachment, file);
            fullAttachments.add(fullAttachment);
        }


        FullContact fullContact = new FullContact(contact, address, photo);
        fullContact.setPhones(phones);
        fullContact.setAttachments(fullAttachments);


        transaction.commitTransaction();
        return fullContact;
    }

}
