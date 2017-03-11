package com.itechart.data.dao;

import com.itechart.data.db.JdbcDataSource;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Attachment;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.Phone;

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

    private final String SELECT_ALL_STATEMENT = "SELECT * FROM contacts,gender,attachments,phones,addresses,family_status,phone_type";
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
        try {
            Connection cn = ds.getConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_ALL_STATEMENT);
            ArrayList<Contact> contacts = new ArrayList<>();
            while (rs.next()) {

                //address
                int address_id = rs.getInt("id");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String street = rs.getString("street");
                String house = rs.getString("house");
                String apartment = rs.getString("apartment");
                String zipCode = rs.getString("zipCode");
                Address address = new Address(address_id, country, city, street, house, apartment, zipCode);

                //attachment
                int attach_id = rs.getInt("id");
                String attach_name = rs.getString("name");
                Date uploadDate = rs.getString("uploadDate");
                String comment = rs.getString("comment");
                String file = rs.getString("file");
                Attachment attachment = new Attachment(address_id, attach_name, uploadDate, comment, file);

                //phones
                int phone_id = rs.getInt("id");
                String countryCode = rs.getString("countryCode");
                String operatorCode = rs.getString("operatorCode");
                String phoneNumber = rs.getString("phoneNumber");
                Phone.PhoneType phoneType = Phone.PhoneType.valueOf(rs.getString("name"));
                String phone_comment = rs.getString("comment");
                Phone phone = new Phone(phone_id, countryCode, operatorCode, phoneNumber, phoneType, phone_comment);

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                Date dateOfBirth = rs.getString("dateOfBirth");
                Contact.Gender gender = Contact.Gender.valueOf(rs.getString("gender"));
                String citizenship = rs.getString("citizenship");
                Contact.FamilyStatus familyStatus = Contact.FamilyStatus.valueOf(rs.getString("familyStatus"));
                String webSite = rs.getString("webSite");
                String email = rs.getString("email");
                String placeOfWork = rs.getString("placeOfWork");
                String photo = rs.getString("photo");

                contacts.add(new Contact(id, name, surname, patronymic, dateOfBirth, gender, citizenship, familyStatus, webSite, email,placeOfWork, address, phones, attachments, photo));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Contact getContactById(int id) {
        return null;
    }

    public Contact getContactByCriteria(Properties criteria) {

        return null;
    }
}
