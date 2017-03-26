package com.itechart.web.service.data;

import com.itechart.data.dao.*;
import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.FullAttachmentDTO;
import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.*;
import com.itechart.data.transaction.Transaction;
import com.itechart.data.transaction.TransactionManager;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.files.FileService;

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
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        FileService fileService = ServiceFactory.getServiceFactory().getFileService();

        Contact contact = contactDao.getContactById(contactId);
        File photo = fileDao.getFileById(contact.getPhoto());
        ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);

        for (Attachment attachment : attachments) {
            File file = fileDao.getFileById(attachment.getFile());
            fileDao.delete(file.getId());
            fileService.deleteFile(file.getStoredName());
        }
        phoneDao.deleteForUser(contactId);
        attachmentDao.deleteForUser(contactId);

        fileDao.delete(contact.getPhoto());
        fileService.deleteFile(photo.getStoredName());

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
        JdbcFileDao fileDao = new JdbcFileDao(transaction);



        Address address = fullContactDTO.getAddress();
        ArrayList<Phone> phones = fullContactDTO.getNewPhones();
        ArrayList<FullAttachmentDTO> attachments = fullContactDTO.getNewAttachments();
        File photo = fullContactDTO.getPhoto();
        Contact contact = fullContactDTO.getContact();

//        if (photo != null) {
        long photoId = fileDao.save(photo);
        contact.setPhoto(photoId);
//        }
        long contactId = contactDao.save(contact);
        long addressId = addressDao.save(address, contactId);

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


    public void updateContact(FullContactDTO changedContact, FullContactDTO contactToUpdate) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);



        contactToUpdate.getContact().update(changedContact.getContact());
        contactToUpdate.getAddress().update(changedContact.getAddress());
        contactToUpdate.getPhoto().update(changedContact.getPhoto());



        //update in db
        contactDao.update(contactToUpdate.getContact());
        addressDao.update(contactToUpdate.getAddress());


        //delete old phones
        phoneDao.deleteForUser(contactToUpdate.getContact().getContactId());
        //persist into db new phones
        for (Phone phone : changedContact.getNewPhones()) {
            phone.setContact(contactToUpdate.getContact().getContactId());
            phoneDao.save(phone);
        }
        //delete attachments
        for (FullAttachmentDTO fullAttachmentDTO : changedContact.getDeletedAttachments()) {
            File file = fullAttachmentDTO.getFile();
            fileDao.delete(file.getId());
            attachmentDao.delete(fullAttachmentDTO.getAttachment().getId());
            // TODO: 23.03.2017 delete from disk
            ServiceFactory.getServiceFactory().getFileService().deleteFile(file.getStoredName());
        }
        //create new attachments
        for (FullAttachmentDTO fullAttachmentDTO : changedContact.getNewAttachments()) {
            File file = fullAttachmentDTO.getFile();
            long fileId = fileDao.save(file);
            Attachment attachment = fullAttachmentDTO.getAttachment();
            attachment.setFile(fileId);
            attachment.setContact(contactToUpdate.getContact().getContactId());
            attachmentDao.save(attachment);
        }
        //update attachments
        for (FullAttachmentDTO fullAttachmentDTO : changedContact.getUpdatedAttachments()) {
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
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        File photo = fileDao.getFileById(photoId);
        transaction.commitTransaction();
        return photo;
    }


    public ArrayList<MainPageContactDTO> getContactsWithAddressDTO() {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
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
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
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
