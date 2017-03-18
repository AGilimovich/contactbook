package com.itechart.data.dao;

import com.itechart.data.db.JdbcDataSource;
import com.itechart.data.entity.Contact;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
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

    private final String UPDATE_CONTACT_QUERY = "UPDATE contacts INNER JOIN gender ON gender.genderValue = ? INNER JOIN family_status ON family_status.familyStatusValue = ? SET surname = ?, name = ?, patronymic = ?, dateOfBirth = ?, gender = gender.genderId, citizenship = ?, familyStatus = family_status.familyStatusId, website = ?, email = ?, placeOfWork = ?, photo = ?  WHERE contactId = ?";

    private final String INSERT_CONTACT_QUERY = "INSERT INTO contacts(surname, name, patronymic, dateOfBirth, gender, citizenship, familyStatus, website, email, placeOfWork, address, photo)" +
            " SELECT ?,?,?,?,gender.genderId,?,family_status.familyStatusId,?,?,?,?,? FROM gender,family_status WHERE gender.genderValue = ? AND family_status.familyStatusValue = ?";

    private final String DELETE_CONTACT_QUERY = "DELETE FROM contacts WHERE contactId = ?";

    private final String SELECT_BY_FIELDS_QUERY = "SELECT c.*, gender.genderValue, family_status.familyStatusValue, a.* FROM contacts AS c " +
            "INNER JOIN addresses AS a ON c.address = a.addressId " +
            "INNER JOIN family_status ON c.familyStatus = family_status.familyStatusId " +
            "INNER JOIN gender ON c.gender = gender.genderId " +
            "WHERE (c.surname LIKE ?) AND (c.name LIKE ?) AND (c.patronymic LIKE ?) AND ((c.dateOfBirth BETWEEN ? AND ?) OR (COALESCE(c.dateOfBirth,'NULL') LIKE ?)) AND (gender.genderValue LIKE ?) AND (family_status.familyStatusValue LIKE ?) " +
            "AND (c.citizenship LIKE ?) AND (a.country LIKE ?) AND (a.city LIKE ?) AND (a.street LIKE ?) AND (a.house LIKE ?) AND (a.apartment LIKE ?) AND (a.zipCode LIKE ?)";

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
                String website = rs.getString("website");
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
            String website = rs.getString("website");
            String email = rs.getString("email");
            String placeOfWork = rs.getString("placeOfWork");
            String photo = rs.getString("photo");
            long addressId = rs.getLong("address");
            contact = new Contact(contactId, name, surname, patronymic, dateOfBirth, gender, citizenship, familyStatus, website, email, placeOfWork, addressId, photo);
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

    @Override
    public ArrayList<Contact> findContactsByFields(String surname, String name, String patronymic, Date fromDate, Date toDate, Contact.Gender gender, Contact.FamilyStatus familyStatus, String citizenship, String country, String city, String street, String house, String apartment, String zipCode) {
        ArrayList<Contact> contacts = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(SELECT_BY_FIELDS_QUERY);
            if (!surname.isEmpty())
                st.setString(1, surname);
            else st.setString(1, "%");

            if (!name.isEmpty())
                st.setString(2, name);
            else st.setString(2, "%");

            if (!patronymic.isEmpty())
                st.setString(3, patronymic);
            else st.setString(3, "%");

            if (fromDate != null)
                st.setDate(4, new java.sql.Date(fromDate.getTime()));
            else
                try {
                    st.setDate(4, new java.sql.Date(DateFormat.getDateInstance().parse("01.01.1900").getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            if (toDate != null)
                st.setDate(5, new java.sql.Date(toDate.getTime()));
            else st.setDate(5, new java.sql.Date(new Date().getTime()));
            // if dates are specified then do not look for rows with NULL dateOfBirth values
            if (toDate != null || fromDate != null) {
                st.setString(6, "NOT NULL");
            } else  st.setString(6, "NULL");

            if (gender != null)
                st.setString(7, gender.name());
            else st.setString(7, "%");
            if (familyStatus != null)
                st.setString(8, familyStatus.name());
            else st.setString(8, "%");
            if (!citizenship.isEmpty())
                st.setString(9, citizenship);
            else st.setString(9, "%");
            if (!country.isEmpty())
                st.setString(10, country);
            else st.setString(10, "%");

            if (!city.isEmpty())
                st.setString(11, city);
            else st.setString(11, "%");

            if (!street.isEmpty())
                st.setString(12, street);
            else st.setString(12, "%");

            if (!house.isEmpty())
                st.setString(13, house);
            else st.setString(13, "%");

            if (!apartment.isEmpty())
                st.setString(14, apartment);
            else st.setString(14, "%");

            if (!zipCode.isEmpty())
                st.setString(15, zipCode);
            else st.setString(15, "%");


            rs = st.executeQuery();
            while (rs.next()) {
                long foundContactId = rs.getLong("contactId");
                String foundName = rs.getString("name");
                String foundSurname = rs.getString("surname");
                String foundPatronymic = rs.getString("patronymic");
                Date foundDateOfBirth = rs.getDate("dateOfBirth");
                Contact.Gender foundGender = Contact.Gender.valueOf(rs.getString("genderValue").toUpperCase());
                String foundCitizenship = rs.getString("citizenship");
                Contact.FamilyStatus foundFamilyStatus = Contact.FamilyStatus.valueOf(rs.getString("familyStatusValue").toUpperCase());
                String foundWebsite = rs.getString("website");
                String foundEmail = rs.getString("email");
                String foundPlaceOfWork = rs.getString("placeOfWork");
                String foundPhoto = rs.getString("photo");
                long foundAddressId = rs.getLong("address");
                Contact contact = new Contact(foundContactId, foundName, foundSurname, foundPatronymic, foundDateOfBirth, foundGender, foundCitizenship, foundFamilyStatus, foundWebsite, foundEmail, foundPlaceOfWork, foundAddressId, foundPhoto);
                contacts.add(contact);
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


}
