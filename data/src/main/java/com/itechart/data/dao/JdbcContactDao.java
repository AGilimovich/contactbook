package com.itechart.data.dao;

import com.itechart.data.db.JdbcDataSource;
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

    private final String SELECT_BY_ID_QUERY = "SELECT c.*,  gender.genderValue, family_status.familyStatusValue, a.* FROM contacts AS c " +
            "INNER JOIN gender ON c.gender = gender.genderId " +
            "INNER JOIN family_status ON c.familyStatus = family_status.familyStatusId " +
            "INNER JOIN addresses AS a ON c.address = a.addressId WHERE c.contactId = ?";

    private final String UPDATE_CONTACT_QUERY = "UPDATE contacts INNER JOIN gender ON gender.genderValue = ? INNER JOIN family_status ON family_status.familyStatusValue = ? SET surname = ?, name = ?, patronymic = ?, dateOfBirth = ?, gender = gender.genderId, citizenship = ?, familyStatus = family_status.familyStatusId, website = ?, email = ?, placeOfWork = ?, photo = ?  WHERE contactId = ?";// TODO: 14.03.2017 дополнить

    private final String INSERT_CONTACT_QUERY = "INSERT INTO contacts(surname, name, patronymic, dateOfBirth, gender, citizenship, familyStatus, website, email, placeOfWork, address, photo)" +
            " SELECT ?,?,?,?,gender.genderId,?,family_status.familyStatusId,?,?,?,?,? FROM gender,family_status WHERE gender.genderValue = ? AND family_status.familyStatusValue = ?";

    private final String DELETE_CONTACT_QUERY = "DELETE FROM contacts WHERE contactId = ?";

    public JdbcContactDao(JdbcDataSource ds) {
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
                st.setString(1, "NULL");
            }
            if (contact.getFamilyStatus() != null)
                st.setString(2, contact.getFamilyStatus().name().toUpperCase());
            else {
                st.setString(2, "NULL");
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

            st.setLong(12, contact.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

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
                long contactId = rs.getLong("contactId");
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
                int addressId = rs.getInt("addressId");

                contacts.add(new Contact(contactId, name, surname, patronymic, dateOfBirth, gender, citizenship, familyStatus, website, email, placeOfWork, addressId, photo));
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
            long contactId = rs.getLong("contactId");
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
            long addressId = rs.getLong("address");
            contact = new Contact(contactId, name, surname, patronymic, dateOfBirth, gender, citizenship, familyStatus, webSite, email, placeOfWork, addressId, photo);
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

        return contact;
    }


}
