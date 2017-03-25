package com.itechart.web.service;

import com.itechart.data.dao.*;
import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.FullAttachmentDTO;
import com.itechart.data.dto.FullContactDTO;
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


    public void saveNewContact(FullContactDTO fullContactDTO) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcContactFileDao fileDao = new JdbcContactFileDao(transaction);

        Address address = fullContactDTO.getAddress();
        ArrayList<Phone> phones = fullContactDTO.getNewPhones();
        ArrayList<FullAttachmentDTO> attachments = fullContactDTO.getNewAttachments();
        File photo = fullContactDTO.getPhoto();
        Contact contact = fullContactDTO.getContact();

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
        for (FullAttachmentDTO fullAttachmentDTO : attachments) {
            File file = fullAttachmentDTO.getFile();
            long fileId = fileDao.save(file);
            Attachment attachment = fullAttachmentDTO.getAttachment();
            attachment.setContact(contactId);
            attachment.setFile(fileId);
            attachmentDao.save(attachment);
        }
        transaction.commitTransaction();
    }


    public void updateContact(FullContactDTO fullContactDTO) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcContactFileDao fileDao = new JdbcContactFileDao(transaction);
        Contact contactToUpdate = contactDao.getContactById(fullContactDTO.getContact().getContactId());
        File photo = fullContactDTO.getPhoto();
        photo.setId(contactToUpdate.getPhoto());
        //if received new photo file
        if (photo.getStoredName() != null && photo.getName() != null) {
            // TODO: 25.03.2017 remove old file from file system
            fileDao.update(photo);
        }

        Address addressToUpdate = addressDao.getAddressById(contactToUpdate.getAddress());
        //update fields with new data
        contactToUpdate.update(fullContactDTO.getContact());
        addressToUpdate.update(fullContactDTO.getAddress());
        //update in db
        addressDao.update(addressToUpdate);
        contactDao.update(contactToUpdate);

        //delete old phones
        phoneDao.deleteForUser(fullContactDTO.getContact().getContactId());
        //persist into db new phones
        for (Phone phone : fullContactDTO.getNewPhones()) {
            phone.setContact(fullContactDTO.getContact().getContactId());
            phoneDao.save(phone);
        }

        for (FullAttachmentDTO fullAttachmentDTO : fullContactDTO.getDeletedAttachments()) {
            fileDao.delete(fullAttachmentDTO.getFile().getId());
            attachmentDao.delete(fullAttachmentDTO.getAttachment().getId());
            // TODO: 23.03.2017 delete from disk
        }
        for (FullAttachmentDTO fullAttachmentDTO : fullContactDTO.getNewAttachments()) {
            File file = fullAttachmentDTO.getFile();
            long fileId = fileDao.save(file);
            Attachment attachment = fullAttachmentDTO.getAttachment();
            attachment.setFile(fileId);
            attachment.setContact(fullContactDTO.getContact().getContactId());
            attachmentDao.save(attachment);
        }
        for (FullAttachmentDTO fullAttachmentDTO : fullContactDTO.getUpdatedAttachments()) {
            File file = fullAttachmentDTO.getFile();
            if (file != null)
                fileDao.update(file);
            attachmentDao.update(fullAttachmentDTO.getAttachment());
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
    public File getPhotoById(long photoId) {
        Transaction transaction = tm.getTransaction();
        JdbcContactFileDao fileDao = new JdbcContactFileDao(transaction);
        File photo = fileDao.getFileById(photoId);
        transaction.commitTransaction();
        return photo;
    }


    public ArrayList<MainPageContactDTO> getContactsWithAddressDTO() {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcContactFileDao fileDao = new JdbcContactFileDao(transaction);
        ArrayList<Contact> contacts = contactDao.getAll();
        ArrayList<MainPageContactDTO> mainPageContactDTOs = new ArrayList<>();
        for (Contact contact : contacts) {
            File photo = fileDao.getFileById(contact.getPhoto());
            Address address = addressDao.getAddressById(contact.getAddress());
            MainPageContactDTO mainPageContactDTO = new MainPageContactDTO(contact, address, photo);
            mainPageContactDTOs.add(mainPageContactDTO);
        }
        transaction.commitTransaction();
        return mainPageContactDTOs;
    }


    public FullContactDTO getFullContactById(long contactId) {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcContactFileDao fileDao = new JdbcContactFileDao(transaction);
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);

        Contact contact = contactDao.getContactById(contactId);
        Address address = addressDao.getAddressById(contactId);
        File photo = fileDao.getFileById(contact.getPhoto());
        ArrayList<Phone> phones = phoneDao.getAllForContact(contactId);

        ArrayList<FullAttachmentDTO> fullAttachmentDTOs = new ArrayList<>();
        ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);
        for (Attachment attachment : attachments) {
            File file = fileDao.getFileById(attachment.getFile());
            FullAttachmentDTO fullAttachmentDTO = new FullAttachmentDTO(attachment, file);
            fullAttachmentDTOs.add(fullAttachmentDTO);
        }


        FullContactDTO fullContactDTO = new FullContactDTO(contact, address, photo);
        fullContactDTO.setPhones(phones);
        fullContactDTO.setAttachments(fullAttachmentDTOs);


        transaction.commitTransaction();
        return fullContactDTO;
    }

}
