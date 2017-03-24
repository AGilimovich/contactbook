package com.itechart.web.service;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 22.03.2017.
 */
public class SessionManager {
    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;

    public SessionManager(JdbcContactDao contactDao, JdbcPhoneDao phoneDao, JdbcAttachmentDao attachmentDao, JdbcAddressDao addressDao) {
        this.contactDao = contactDao;
        this.phoneDao = phoneDao;
        this.attachmentDao = attachmentDao;
        this.addressDao = addressDao;

    }

    public void deleteContact(long contactId) {
        phoneDao.deleteForUser(contactId);
        attachmentDao.deleteForUser(contactId);
        // TODO: 23.03.2017 delete from disk
        long addressId = contactDao.getContactById(contactId).getAddress();
        contactDao.delete(contactId);
        addressDao.delete(addressId);
    }

    public void saveNewContact(Contact contact, Address address, ArrayList<Phone> phones, ArrayList<Attachment> attachments) {
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
    }

    public void updateContact(Contact contact, Address address, ArrayList<Phone> newPhones, ArrayList<Attachment> newAttachments, ArrayList<Phone> updatedPhones, ArrayList<Attachment> updatedAttachments, ArrayList<Phone> deletedPhones, ArrayList<Attachment> deletedAttachments) {


        addressDao.update(address);
        contactDao.update(contact);

        //delete old phones
        phoneDao.deleteForUser(contact.getId());
        //persist into db new phones
        for (Phone phone : newPhones) {
            phone.setContact(contact.getId());
            phoneDao.save(phone);
        }

        for (Attachment attachment : deletedAttachments) {
            attachmentDao.delete(attachment.getId());
            // TODO: 23.03.2017 delete from disk
        }
        for (Attachment attachment : newAttachments) {
            attachment.setContact(contact.getId());
            attachmentDao.save(attachment);
        }
        for (Attachment attachment : updatedAttachments) {
            attachmentDao.update(attachment);
        }

    }


}
