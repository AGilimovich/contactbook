package com.itechart.web.service;

import com.itechart.data.dao.*;
import com.itechart.data.dto.ContactWithAddressDTO;
import com.itechart.data.dto.FullContact;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;
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

        Address address = fullContact.getAddress();
        ArrayList<Phone> phones = fullContact.getNewPhones();
        ArrayList<Attachment> attachments = fullContact.getNewAttachments();
        Contact contact = fullContact.getContact();

        long addressId = addressDao.save(address);
        contact.setAddress(addressId);
        long contactId = contactDao.save(contact);
        for (Phone phone : phones) {
            phone.setContact(contactId);
            phoneDao.save(phone);
        }
        for (Attachment attachment : attachments) {
            attachment.setContact(contactId);
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

        Contact contactToUpdate = contactDao.getContactById(fullContact.getContact().getContactId());
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

        for (Attachment attachment : fullContact.getDeletedAttachments()) {
            attachmentDao.delete(attachment.getId());
            // TODO: 23.03.2017 delete from disk
        }
        for (Attachment attachment : fullContact.getNewAttachments()) {
            attachment.setContact(fullContact.getContact().getContactId());
            attachmentDao.save(attachment);
        }
        for (Attachment attachment : fullContact.getUpdatedAttachments()) {
            attachmentDao.update(attachment);
        }
        transaction.commitTransaction();

    }


    public ArrayList<Contact> getAllContacts() {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = contactDao.getAll();
        transaction.commitTransaction();
        return contacts;
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

    public ArrayList<Phone> getAllPhonesForContact(long contactId) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        ArrayList<Phone> phones = phoneDao.getAllForContact(contactId);
        transaction.commitTransaction();
        return phones;
    }

    public ArrayList<Attachment> getAllAttachmentsForContact(long contactId) {
        Transaction transaction = tm.getTransaction();
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);
        transaction.commitTransaction();
        return attachments;
    }

    public ArrayList<ContactWithAddressDTO> getContactsWithAddressDTO() {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        ArrayList<Contact> contacts = contactDao.getAll();
        ArrayList<ContactWithAddressDTO> contactWithAddressDTOs = new ArrayList<>();
        for (Contact contact : contacts) {
            Address address = addressDao.getAddressById(contact.getAddress());
            ContactWithAddressDTO contactWithAddressDTO = new ContactWithAddressDTO(contact, address);
            contactWithAddressDTOs.add(contactWithAddressDTO);
        }
        transaction.commitTransaction();
        return contactWithAddressDTOs;
    }

}
