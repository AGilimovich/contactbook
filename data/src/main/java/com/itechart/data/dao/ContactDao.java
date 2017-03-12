package com.itechart.data.dao;

import com.itechart.data.entity.Address;
import com.itechart.data.entity.Phone;
import com.itechart.data.db.JdbcDataSource;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * Class for persisting and modifying data in the database.
 */
public class ContactDao implements IContactDao {

    private final String SELECT_ALL_CONTACTS_QUERY = "SELECT c.*, gender.genderValue, family_status.familyStatusValue, a.*" +
            " FROM contacts AS c INNER JOIN gender ON c.gender = gender.genderId" +
            " INNER JOIN family_status ON c.familyStatus = family_status.familyStatusId" +
            " INNER JOIN addresses AS a ON c.address = a.addressId;";

    private final String SELECT_PHONES_FOR_CONTACT = "SELECT p.phoneId, p.countryCode, p.operatorCode, p.phoneNumber, phone_type.phoneTypeValue, p.comment" +
            " FROM phones AS p LEFT OUTER JOIN phone_type ON p.phoneType = phone_type.phoneTypeId" +
            " WHERE p.contact = 1;";

    private final String SELECT_ATTACHMENTS_FOR_CONTACT = "    SELECT \n" +
            "    attachId,\n" +
            "    attachName,\n" +
            "    uploadDate,\n" +
            "    comment,\n" +
            "    file\n" +
            "    FROM attachments\n" +
            "    WHERE contact = 1;";

//    private final String INSERT_CONTACT_STATEMENT = "INSERT INTO contacts(name,surname,patronymic,dateOfBirth,gender,citizenship, familyStatus,website,email,placeOfWork,address, phones,attachments, photo),address(country,city,street,house,apartment,zipCode),";


    private JdbcDataSource ds;

    //todo delete
    public ContactDao() {
    }

    public ContactDao(JdbcDataSource ds) {
        this.ds = ds;
    }

    public void save(Contact contact) {
        try {
            Connection cn = ds.getConnection();
            Statement st = cn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Contact contact) {

    }

    public void update(Contact contact) {

    }

    @Override
    public ArrayList<Contact> getAll() {
        ArrayList<Contact> contacts = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            cn = ds.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(SELECT_ALL_CONTACTS_QUERY);
            while (rs.next()) {

                //contact info
                int contact_id = rs.getInt("contactId");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                Contact.Gender gender = Contact.Gender.valueOf(rs.getString("genderValue").toUpperCase());
                String citizenship = rs.getString("citizenship");
                Contact.FamilyStatus familyStatus = Contact.FamilyStatus.valueOf(rs.getString("familyStatusValue").toUpperCase());
                String webSite = rs.getString("webSite");
                String email = rs.getString("email");
                String placeOfWork = rs.getString("placeOfWork");
                String photo = rs.getString("photo");
                //address
                int address_id = rs.getInt("addressId");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String street = rs.getString("street");
                String house = rs.getString("house");
                String apartment = rs.getString("apartment");
                String zipCode = rs.getString("zipCode");
                Address address = new Address(address_id, country, city, street, house, apartment, zipCode);


                //phones
                Statement st_phone = null;
                ResultSet rs_phone = null;
                ArrayList<Phone> phones = new ArrayList<Phone>();
                try {
                    st_phone = cn.createStatement();
                    rs_phone = st_phone.executeQuery(SELECT_PHONES_FOR_CONTACT);
                    while (rs_phone.next()) {
                        int phone_id = rs_phone.getInt("phoneId");
                        String countryCode = rs_phone.getString("countryCode");
                        String operatorCode = rs_phone.getString("operatorCode");
                        String phoneNumber = rs_phone.getString("phoneNumber");
                        Phone.PhoneType phoneType = Phone.PhoneType.valueOf(rs_phone.getString("phoneTypeValue").toUpperCase());
                        String phone_comment = rs_phone.getString("comment");
                        Phone phone = new Phone(phone_id, countryCode, operatorCode, phoneNumber, phoneType, phone_comment);
                        phones.add(phone);
                    }
                } finally {
                    if (rs_phone != null) rs_phone.close();
                    if (st_phone != null) st_phone.close();
                }
                //attachment
                Statement st_attach = null;
                ResultSet rs_attach = null;
                ArrayList<Attachment> attachments = new ArrayList<Attachment>();
                try {
                    st_attach = cn.createStatement();
                    rs_attach = st_attach.executeQuery(SELECT_ATTACHMENTS_FOR_CONTACT);
                    while (rs_attach.next()) {
                        Date uploadDate = new Date(rs_attach.getTimestamp("uploadDate").getTime());
                        String attach_name = rs_attach.getString("attachName");
                        int attach_id = rs_attach.getInt("attachId");
                        String comment = rs_attach.getString("comment");
                        String file = rs_attach.getString("file");
                        Attachment attachment = new Attachment(attach_id, attach_name, uploadDate, comment, file);
                        attachments.add(attachment);
                    }
                } finally {
                    if (rs_attach != null) rs_attach.close();
                    if (st_attach != null) st_attach.close();
                }


                contacts.add(new Contact(contact_id, name, surname, patronymic, dateOfBirth, gender, citizenship, familyStatus, webSite, email, placeOfWork, address, phones, attachments, photo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return contacts;
    }

    public Contact getContactById(int id) {
        return null;
    }

    public Contact getContactByCriteria(Properties criteria) {

        return null;
    }
}
