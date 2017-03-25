package com.itechart.data.dao;

import com.itechart.data.db.DBResourceManager;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Contact;

import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class for persisting and modifying data in the database.
 */
public class JdbcContactDao implements IContactDao {
    private DataSource ds;

    private final String SELECT_ALL_CONTACTS_QUERY = "SELECT c.*, gender.gender_value, family_status.family_status_value, a.* " +
            " FROM contact AS c INNER JOIN gender ON c.gender = gender.gender_id" +
            " INNER JOIN family_status ON c.family_status = family_status.family_status_id" +
            " INNER JOIN address AS a ON c.address = a.address_id";

    private final String SELECT_BY_ID_QUERY = "SELECT c.*,  gender.gender_value, family_status.family_status_value, a.* FROM contact AS c " +
            "INNER JOIN gender ON c.gender = gender.gender_id " +
            "INNER JOIN family_status ON c.family_status = family_status.family_status_id " +
            "INNER JOIN address AS a ON c.address = a.address_id WHERE c.contact_id = ?";

    private final String UPDATE_CONTACT_QUERY = "UPDATE contact INNER JOIN gender ON gender.gender_value = ? INNER JOIN family_status ON family_status.family_status_value = ? SET surname = ?, name = ?, patronymic = ?, date_of_birth = ?, gender = gender.gender_id, citizenship = ?, family_status = family_status.family_status_id, website = ?, email = ?, place_of_work = ?, photo = ?  WHERE contact_id = ?";

    private final String INSERT_CONTACT_QUERY = "INSERT INTO contact(surname, name, patronymic, date_of_birth, gender, citizenship, family_status, website, email, place_of_work, address, photo)" +
            " SELECT ?,?,?,?,gender.gender_id,?,family_status.family_status_id,?,?,?,?,? FROM gender,family_status WHERE gender.gender_value = ? AND family_status.family_status_value = ?";

    private final String DELETE_CONTACT_QUERY = "DELETE FROM contact WHERE contact_id = ?";

    private final String SELECT_BY_FIELDS_QUERY = "SELECT c.*, gender.gender_value, family_status.family_status_value, a.* FROM contact AS c " +
            "INNER JOIN address AS a ON c.address = a.address_id " +
            "INNER JOIN family_status ON c.family_status = family_status.family_status_id " +
            "INNER JOIN gender ON c.gender = gender.gender_id " +
            "WHERE (c.surname LIKE ?) AND (c.name LIKE ?) AND (c.patronymic LIKE ?) AND ((c.date_of_birth BETWEEN ? AND ?) OR (COALESCE(c.date_of_birth,'NULL') LIKE ?)) AND (gender.gender_value LIKE ?) AND (family_status.family_status_value LIKE ?) " +
            "AND (c.citizenship LIKE ?) AND (a.country LIKE ?) AND (a.city LIKE ?) AND (a.street LIKE ?) AND (a.house LIKE ?) AND (a.apartment LIKE ?) AND (a.zip_code LIKE ?)";
    private final String SELECT_CONTACTS_BY_BIRTHDATE = "SELECT name, email FROM contact WHERE date_of_birth = ?";


    public JdbcContactDao(DataSource ds) {
        this.ds = ds;
    }

    public long save(Contact contact) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        long id = 0;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(INSERT_CONTACT_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, contact.getSurname());
            st.setString(2, contact.getName());
            st.setString(3, contact.getPatronymic());
            if (contact.getDateOfBirth() != null) {
                st.setDate(4, new java.sql.Date(contact.getDateOfBirth().getTime()));
            } else st.setDate(4, null);
            st.setString(5, contact.getCitizenship());
            st.setString(6, contact.getWebsite());
            st.setString(7, contact.getEmail());
            st.setString(8, contact.getPlaceOfWork());
            st.setLong(9, contact.getAddress());
            st.setString(10, contact.getPhoto());
            if (contact.getGender() != null)
                st.setString(11, contact.getGender().name());
            else st.setString(11, null);
            if (contact.getFamilyStatus() != null)
                st.setString(12, contact.getFamilyStatus().name());
            else st.setString(12, null);
            st.executeUpdate();

            rs = st.getGeneratedKeys();
            if (rs.next())
                id = rs.getLong(1);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }

        return id;

    }

    public void delete(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(DELETE_CONTACT_QUERY);
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            DBResourceManager.closeResources(cn, st, null);

        }
    }

    public void update(Contact contact) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(UPDATE_CONTACT_QUERY);
            if (contact.getGender() != null)
                st.setString(1, contact.getGender().name().toUpperCase());
            else {
                st.setString(1, null);
            }
            if (contact.getFamilyStatus() != null)
                st.setString(2, contact.getFamilyStatus().name().toUpperCase());
            else {
                st.setString(2, null);
            }
            st.setString(3, contact.getSurname());
            st.setString(4, contact.getName());
            st.setString(5, contact.getPatronymic());
            if (contact.getDateOfBirth() != null) {
                st.setDate(6, new java.sql.Date(contact.getDateOfBirth().getTime()));
            } else st.setDate(6, null);
            st.setString(7, contact.getCitizenship());
            st.setString(8, contact.getWebsite());
            st.setString(9, contact.getEmail());
            st.setString(10, contact.getPlaceOfWork());
            st.setString(11, contact.getPhoto());

            st.setLong(12, contact.getContactId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            DBResourceManager.closeResources(cn, st, null);

        }
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
                long contactId = rs.getLong("contact_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                Date dateOfBirth = rs.getDate("date_of_birth");
                Contact.Gender gender = Contact.Gender.valueOf(rs.getString("gender_value").toUpperCase());
                String citizenship = rs.getString("citizenship");
                Contact.FamilyStatus familyStatus = Contact.FamilyStatus.valueOf(rs.getString("family_status_value").toUpperCase());
                String website = rs.getString("website");
                String email = rs.getString("email");
                String placeOfWork = rs.getString("place_of_work");
                String photo = rs.getString("photo");
                int addressId = rs.getInt("address_id");
                Contact contact = new Contact(contactId, name, surname);
                contact.setPatronymic(patronymic);
                contact.setDateOfBirth(dateOfBirth);
                contact.setGender(gender);
                contact.setCitizenship(citizenship);
                contact.setFamilyStatus(familyStatus);
                contact.setWebsite(website);
                contact.setEmail(email);
                contact.setPlaceOfWork(placeOfWork);
                contact.setAddress(addressId);
                contact.setPhoto(photo);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);

        }
        return contacts;
    }

    @Override
    public Contact getContactById(long id) {
        Contact contact = null;
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(SELECT_BY_ID_QUERY);
            st.setLong(1, id);
            rs = st.executeQuery();
            rs.next();
            long contactId = rs.getLong("contact_id");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String patronymic = rs.getString("patronymic");
            Date dateOfBirth = rs.getDate("date_of_birth");
            Contact.Gender gender = Contact.Gender.valueOf(rs.getString("gender_value").toUpperCase());
            String citizenship = rs.getString("citizenship");
            Contact.FamilyStatus familyStatus = Contact.FamilyStatus.valueOf(rs.getString("family_status_value").toUpperCase());
            String website = rs.getString("website");
            String email = rs.getString("email");
            String placeOfWork = rs.getString("place_of_work");
            String photo = rs.getString("photo");
            long addressId = rs.getLong("address");
            contact = new Contact(contactId, name, surname);
            contact.setPatronymic(patronymic);
            contact.setDateOfBirth(dateOfBirth);
            contact.setGender(gender);
            contact.setCitizenship(citizenship);
            contact.setFamilyStatus(familyStatus);
            contact.setWebsite(website);
            contact.setEmail(email);
            contact.setPlaceOfWork(placeOfWork);
            contact.setAddress(addressId);
            contact.setPhoto(photo);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }

        return contact;
    }

    @Override
    public ArrayList<Contact> findContactsByFields(SearchDTO dto) {

        ArrayList<Contact> contacts = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(SELECT_BY_FIELDS_QUERY);
            if (!dto.getSurname().isEmpty())
                st.setString(1, dto.getSurname());
            else st.setString(1, "%");

            if (!dto.getName().isEmpty())
                st.setString(2, dto.getName());
            else st.setString(2, "%");

            if (!dto.getPatronymic().isEmpty())
                st.setString(3, dto.getPatronymic());
            else st.setString(3, "%");

            if (dto.getFromDate() != null)
                st.setDate(4, new java.sql.Date(dto.getFromDate().getTime()));
            else
                try {
                    st.setDate(4, new java.sql.Date(DateFormat.getDateInstance().parse("01.01.1900").getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            if (dto.getToDate() != null)
                st.setDate(5, new java.sql.Date(dto.getToDate().getTime()));
            else st.setDate(5, new java.sql.Date(new Date().getTime()));
            // if dates are specified then do not look for rows with NULL dateOfBirth values
            if (dto.getToDate() != null || dto.getFromDate() != null) {
                st.setString(6, "NOT NULL");
            } else st.setString(6, "NULL");

            if (dto.getGender() != null)
                st.setString(7, dto.getGender().name());
            else st.setString(7, "%");
            if (dto.getFamilyStatus() != null)
                st.setString(8, dto.getFamilyStatus().name());
            else st.setString(8, "%");
            if (!dto.getCitizenship().isEmpty())
                st.setString(9, dto.getCitizenship());
            else st.setString(9, "%");
            if (!dto.getCountry().isEmpty())
                st.setString(10, dto.getCountry());
            else st.setString(10, "%");

            if (!dto.getCity().isEmpty())
                st.setString(11, dto.getCity());
            else st.setString(11, "%");

            if (!dto.getStreet().isEmpty())
                st.setString(12, dto.getStreet());
            else st.setString(12, "%");

            if (!dto.getHouse().isEmpty())
                st.setString(13, dto.getHouse());
            else st.setString(13, "%");

            if (!dto.getApartment().isEmpty())
                st.setString(14, dto.getApartment());
            else st.setString(14, "%");

            if (!dto.getZipCOde().isEmpty())
                st.setString(15, dto.getZipCOde());
            else st.setString(15, "%");


            rs = st.executeQuery();
            while (rs.next()) {
                long foundContactId = rs.getLong("contact_id");
                String foundName = rs.getString("name");
                String foundSurname = rs.getString("surname");
                String foundPatronymic = rs.getString("patronymic");
                Date foundDateOfBirth = rs.getDate("date_of_birth");
                Contact.Gender foundGender = Contact.Gender.valueOf(rs.getString("gender_value").toUpperCase());
                String foundCitizenship = rs.getString("citizenship");
                Contact.FamilyStatus foundFamilyStatus = Contact.FamilyStatus.valueOf(rs.getString("family_status_value").toUpperCase());
                String foundWebsite = rs.getString("website");
                String foundEmail = rs.getString("email");
                String foundPlaceOfWork = rs.getString("place_of_work");
                String foundPhoto = rs.getString("photo");
                long foundAddressId = rs.getLong("address");
                Contact contact = new Contact(foundContactId, foundName, foundSurname);
                contact.setPatronymic(foundPatronymic);
                contact.setDateOfBirth(foundDateOfBirth);
                contact.setGender(foundGender);
                contact.setCitizenship(foundCitizenship);
                contact.setFamilyStatus(foundFamilyStatus);
                contact.setWebsite(foundWebsite);
                contact.setEmail(foundEmail);
                contact.setPlaceOfWork(foundPlaceOfWork);
                contact.setAddress(foundAddressId);
                contact.setPhoto(foundPhoto);

                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }

        return contacts;
    }

    @Override
    public ArrayList<Contact> getByBirthDate(Date date) {
        ArrayList<Contact> contacts = null;
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(SELECT_BY_ID_QUERY);
            st.setDate(1, new java.sql.Date(date.getTime()));
            rs = st.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                Contact contact = new Contact();
                contact.setName(name);
                contact.setEmail(email);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }

        return contacts;
    }


}
