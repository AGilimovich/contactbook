package com.itechart.web.service.data;

import com.itechart.data.dao.*;
import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.entity.FullAttachmentEntity;
import com.itechart.data.entity.FullContactEntity;
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
public class DataService implements IDataService{

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
        File photo = fileDao.getFileById(contact.getPhotoId());
        ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);

        for (Attachment attachment : attachments) {
            File file = fileDao.getFileById(attachment.getFileId());
            fileDao.delete(file.getId());
            fileService.deleteFile(file.getStoredName());
        }
        phoneDao.deleteForContact(contactId);
        attachmentDao.deleteForContact(contactId);

        fileDao.delete(contact.getPhotoId());
        fileService.deleteFile(photo.getStoredName());


        // TODO: 26.03.2017 getAddressId for contact method
        long addressId = contact.getAddressId();
        contactDao.delete(contactId);
        addressDao.delete(addressId);
        transaction.commitTransaction();
    }


    public void saveNewContact(FullContactEntity fullContactEntity) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);



        Address address = fullContactEntity.getAddress();
        ArrayList<Phone> phones = fullContactEntity.getNewPhones();
        ArrayList<FullAttachmentEntity> attachments = fullContactEntity.getNewAttachments();
        File photo = fullContactEntity.getPhoto();
        Contact contact = fullContactEntity.getContact();

//        if (photo != null) {
        long photoId = fileDao.save(photo);
        contact.setPhotoId(photoId);
//        }
        long contactId = contactDao.save(contact);
//        long addressId = addressDao.save(address, contactId);

        for (Phone phone : phones) {
            phone.setContactId(contactId);
            phoneDao.save(phone);
        }
        for (FullAttachmentEntity fullAttachmentEntity : attachments) {
            File file = fullAttachmentEntity.getFile();
            long fileId = fileDao.save(file);
            Attachment attachment = fullAttachmentEntity.getAttachment();
            attachment.setContact(contactId);
            attachment.setFileId(fileId);
            attachmentDao.save(attachment);
        }
        transaction.commitTransaction();
    }


    public void updateContact(FullContactEntity changedContact, FullContactEntity contactToUpdate) {
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
        phoneDao.deleteForContact(contactToUpdate.getContact().getContactId());
        //persist into db new phones
        for (Phone phone : changedContact.getNewPhones()) {
            phone.setContactId(contactToUpdate.getContact().getContactId());
            phoneDao.save(phone);
        }
        //delete attachments
        for (FullAttachmentEntity fullAttachmentEntity : changedContact.getDeletedAttachments()) {
            File file = fullAttachmentEntity.getFile();
            fileDao.delete(file.getId());
            attachmentDao.delete(fullAttachmentEntity.getAttachment().getId());
            // TODO: 23.03.2017 delete from disk
            ServiceFactory.getServiceFactory().getFileService().deleteFile(file.getStoredName());
        }
        //create new attachments
        for (FullAttachmentEntity fullAttachmentEntity : changedContact.getNewAttachments()) {
            File file = fullAttachmentEntity.getFile();
            long fileId = fileDao.save(file);
            Attachment attachment = fullAttachmentEntity.getAttachment();
            attachment.setFileId(fileId);
            attachment.setContact(contactToUpdate.getContact().getContactId());
            attachmentDao.save(attachment);
        }
        //update attachments
        for (FullAttachmentEntity fullAttachmentEntity : changedContact.getUpdatedAttachments()) {
            File file = fullAttachmentEntity.getFile();
            if (file != null)
                fileDao.update(file);
            attachmentDao.update(fullAttachmentEntity.getAttachment());
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

    public File getFileById(long photoId) {
        Transaction transaction = tm.getTransaction();
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        File photo = fileDao.getFileById(photoId);
        transaction.commitTransaction();
        return photo;
    }


    public ArrayList<MainPageContactDTO> getMainPageContactDTOs() {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        ArrayList<Contact> contacts = contactDao.getAll();
        ArrayList<MainPageContactDTO> mainPageContactDTOs = new ArrayList<>();
        for (Contact contact : contacts) {
            File photo = fileDao.getFileById(contact.getPhotoId());
            Address address = addressDao.getAddressById(contact.getAddressId());
            MainPageContactDTO mainPageContactDTO = new MainPageContactDTO(contact, address, photo);
            mainPageContactDTOs.add(mainPageContactDTO);
        }
        transaction.commitTransaction();
        return mainPageContactDTOs;
    }


    public FullContactEntity getFullContactEntityById(long contactId) {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);

        Contact contact = contactDao.getContactById(contactId);
        Address address = addressDao.getAddressById(contactId);
        File photo = fileDao.getFileById(contact.getPhotoId());
        ArrayList<Phone> phones = phoneDao.getAllForContact(contactId);

        ArrayList<FullAttachmentEntity> fullAttachmentEntities = new ArrayList<>();
        ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);
        for (Attachment attachment : attachments) {
            File file = fileDao.getFileById(attachment.getFileId());
            FullAttachmentEntity fullAttachmentEntity = new FullAttachmentEntity(attachment, file);
            fullAttachmentEntities.add(fullAttachmentEntity);
        }


        FullContactEntity fullContactEntity = new FullContactEntity(contact, address, photo);
        fullContactEntity.setPhones(phones);
        fullContactEntity.setAttachments(fullAttachmentEntities);


        transaction.commitTransaction();
        return fullContactEntity;
    }

}
