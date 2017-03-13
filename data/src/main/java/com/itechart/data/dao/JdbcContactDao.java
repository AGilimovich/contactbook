package com.itechart.data.dao;

import com.itechart.data.db.JdbcDataSource;
import com.itechart.data.dto.ContactDTO;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class for persisting and modifying data in the database.
 */
public class JdbcContactDao implements IContactDao {
    private JdbcDataSource ds;

    private final String SELECT_ALL_CONTACTS_QUERY = "SELECT c.*, gender.genderValue, family_status.familyStatusValue, a.* " +
            " FROM contacts AS c INNER JOIN gender ON c.gender = gender.genderId" +
            " INNER JOIN family_status ON c.familyStatus = family_status.familyStatusId" +
            " INNER JOIN addresses AS a ON c.address = a.addressId";

    private final String SELECT_ALL_DTO_QUERY = "SELECT c.contactId, c.name, c.surname, c.patronymic, c.dateOfBirth, c.placeOfWork, c.photo, a.country, a.city, a.street, a.house, a.apartment " +
            " FROM contacts AS c" +
            " INNER JOIN addresses AS a ON c.address = a.addressId";

    private final String SELECT_BY_ID_QUERY = "SELECT c.*,  gender.genderValue, family_status.familyStatusValue, a.* FROM contacts AS c " +
            "INNER JOIN gender ON c.gender = gender.genderId " +
            "INNER JOIN family_status ON c.familyStatus = family_status.familyStatusId " +
            "INNER JOIN addresses AS a ON c.address = a.addressId WHERE c.contactId = ?";

    public JdbcContactDao(JdbcDataSource ds) {
        this.ds = ds;
    }

    public void save(Contact contact) {


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
                int contactId = rs.getInt("contactId");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                Contact.Gender gender = Contact.Gender.valueOf(rs.getString("genderValue").toUpperCase());
                String citizenship = rs.getString("citizenship");
                Contact.FamilyStatus familyStatus = Contact.FamilyStatus.valueOf(rs.getString("familyStatusValue").toUpperCase());
                String website = rs.getString("webSite");
                String email = rs.getString("email");
                String placeOfWork = rs.getString("placeOfWork");
                String photo = rs.getString("photo");

                //address
                int addressId = rs.getInt("addressId");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String street = rs.getString("street");
                String house = rs.getString("house");
                String apartment = rs.getString("apartment");
                String zipCode = rs.getString("zipCode");
                Address address = new Address(addressId, country, city, street, house, apartment, zipCode);


                contacts.add(new Contact(contactId, name, surname, patronymic, dateOfBirth, gender, citizenship, familyStatus, website, email, placeOfWork, address, photo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (st != null) try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (cn != null) try {
                cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return contacts;
    }

    public Contact getContactById(int id) {
        Contact contact = null;
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(SELECT_BY_ID_QUERY);
            st.setInt(1, id);
            rs = st.executeQuery();
            rs.next();
            int contactId = rs.getInt("contactId");
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
            int addressId = rs.getInt("addressId");
            String country = rs.getString("country");
            String city = rs.getString("city");
            String street = rs.getString("street");
            String house = rs.getString("house");
            String apartment = rs.getString("apartment");
            String zipCode = rs.getString("zipCode");
            Address address = new Address(addressId, country, city, street, house, apartment, zipCode);
            contact = new Contact(contactId, name, surname, patronymic, dateOfBirth, gender, citizenship, familyStatus, webSite, email, placeOfWork, address, photo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contact;
    }

    @Override
    public ArrayList<ContactDTO> getAllDto() {
        ArrayList<ContactDTO> dtos = new ArrayList<>();

        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            cn = ds.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(SELECT_ALL_DTO_QUERY);
            while (rs.next()) {
                int contactId = rs.getInt("contactId");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                String placeOfWork = rs.getString("placeOfWork");
                String photo = rs.getString("photo");

                //address
                String country = rs.getString("country");
                String city = rs.getString("city");
                String street = rs.getString("street");
                String house = rs.getString("house");
                String apartment = rs.getString("apartment");
                dtos.add(new ContactDTO(contactId, name, surname, patronymic, dateOfBirth, placeOfWork, country, city, street, house, apartment, photo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (st != null) try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (cn != null) try {
                cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        return dtos;
    }


}
