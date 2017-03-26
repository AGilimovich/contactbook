package com.itechart.web.service.data;

import com.itechart.data.dao.*;
import com.itechart.data.dto.FullAttachmentDTO;
import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.dto.MainPageContactDTO;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.*;
import com.itechart.data.transaction.Transaction;
import com.itechart.data.transaction.TransactionManager;
import com.itechart.web.service.ServiceFactory;
import com.itechart.web.service.files.FileService;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aleksandr on 26.03.2017.
 */
public class TransactionDataService implements IDataService {
    private TransactionManager tm;

    public TransactionDataService(TransactionManager tm) {
        this.tm = tm;
    }

    @Override
    public void deleteContact(long contactId) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        FileService fileService = ServiceFactory.getServiceFactory().getFileService();
        //todo deleting from disk

        phoneDao.deleteForContact(contactId);
        attachmentDao.deleteForContact(contactId);
        contactDao.delete(contactId);
        addressDao.deleteForContact(contactId);
        fileDao.deleteForContact(contactId);

        transaction.commitTransaction();
    }

    @Override
    public void saveNewContact(FullContactDTO fullContactDTO) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);

        long photoId = fileDao.save(fullContactDTO.getPhoto());
        long addressId = addressDao.save(fullContactDTO.getAddress());
        fullContactDTO.getContact().setPhotoId(photoId);
        fullContactDTO.getContact().setAddressId(addressId);
        long contactId = contactDao.save(fullContactDTO.getContact());

        for (Phone phone : fullContactDTO.getPhones()) {
            phone.setContactId(contactId);
            phoneDao.save(phone);
        }

        for (FullAttachmentDTO fullAttachmentDTO : fullContactDTO.getAttachments()) {
            long fileId = fileDao.save(fullAttachmentDTO.getFile());
            Attachment attachment = fullAttachmentDTO.getAttachment();
            attachment.setFileId(fileId);
            attachmentDao.save(attachment);
        }

        transaction.commitTransaction();
    }

    @Override
    public void updateContact(FullContactDTO constructedContact, FullContactDTO contactToUpdate) {
        Transaction transaction = tm.getTransaction();
        JdbcPhoneDao phoneDao = new JdbcPhoneDao(transaction);
        JdbcAttachmentDao attachmentDao = new JdbcAttachmentDao(transaction);
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        JdbcAddressDao addressDao = new JdbcAddressDao(transaction);
        JdbcFileDao fileDao = new JdbcFileDao(transaction);

        //update photo
        contactToUpdate.getPhoto().update(constructedContact.getPhoto());
        fileDao.update(contactToUpdate.getPhoto());

        //update address
        contactToUpdate.getAddress().update(constructedContact.getAddress());
        addressDao.update(contactToUpdate.getAddress());

        //update contact
        contactToUpdate.getContact().update(constructedContact.getContact());
        contactDao.update(contactToUpdate.getContact());

        //update phones
        for (Phone phone : contactToUpdate.getPhones()) {
            phoneDao.delete(phone.getId());
        }
        for (Phone phone : constructedContact.getPhones()) {
            phone.setId(contactToUpdate.getContact().getContactId());
            phoneDao.save(phone);
        }

        //update attachments
        //delete attachments
        for (FullAttachmentDTO attachmentDTO : constructedContact.getDeletedAttachments()) {
            fileDao.delete(attachmentDTO.getFile().getId());
            attachmentDao.delete(attachmentDTO.getAttachment().getId());
        }
        //create new attachments
        for (FullAttachmentDTO attachmentDTO : constructedContact.getNewAttachments()) {
            long fileId = fileDao.save(attachmentDTO.getFile());
            Attachment attachment = attachmentDTO.getAttachment();
            attachment.setFileId(fileId);
            attachmentDao.save(attachment);
        }
        //update attachments
        for (FullAttachmentDTO attachmentDTO : constructedContact.getUpdatedAttachments()) {
            File file = attachmentDTO.getFile();
            if (file != null)
                fileDao.update(file);
            attachmentDao.update(attachmentDTO.getAttachment());
        }

        transaction.commitTransaction();
    }

    @Override
    public ArrayList<Contact> getContactsWithBirthday(Date date) {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = contactDao.getByBirthDate(date);
        transaction.commitTransaction();
        return contacts;
    }

    @Override
    public ArrayList<Contact> getContactsByFields(SearchDTO dto) {
        Transaction transaction = tm.getTransaction();
        JdbcContactDao contactDao = new JdbcContactDao(transaction);
        ArrayList<Contact> contacts = contactDao.findContactsByFields(dto);
        transaction.commitTransaction();
        return contacts;
    }

    @Override
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

    public File getFileById(long fileId) {
        Transaction transaction = tm.getTransaction();
        JdbcFileDao fileDao = new JdbcFileDao(transaction);
        File file = fileDao.getFileById(fileId);
        transaction.commitTransaction();
        return file;
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

    @Override
    public FullContactDTO getFullContactDTOById(long contactId) {
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

        ArrayList<FullAttachmentDTO> fullAttachmentDTOs = new ArrayList<>();
        ArrayList<Attachment> attachments = attachmentDao.getAllForContact(contactId);
        for (Attachment attachment : attachments) {
            File file = fileDao.getFileById(attachment.getFileId());
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
