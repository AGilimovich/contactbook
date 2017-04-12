package com.itechart.data.dao;

import com.itechart.data.db.DBResourceManager;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Contact;
import com.itechart.data.exception.DaoException;
import com.itechart.data.query.DynamicPreparedQueryBuilder;
import com.itechart.data.transaction.Transaction;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementation of contact DAO using jdbc.
 */
public class JdbcContactDao implements IContactDao {
    private Logger logger = LoggerFactory.getLogger(JdbcContactDao.class);

    private Transaction transaction;

    private final String SELECT_BY_ID_QUERY = "SELECT c.*, g.gender_value, f_s.family_status_value" +
            " FROM contact AS c" +
            " INNER JOIN gender AS g ON c.gender = g.gender_id" +
            " INNER JOIN family_status AS f_s ON c.family_status = f_s.family_status_id" +
            " WHERE c.contact_id = ?";

    private final String UPDATE_CONTACT_QUERY = "UPDATE contact INNER JOIN gender ON gender.gender_value = ? INNER JOIN family_status ON family_status.family_status_value = ? SET surname = ?, name = ?, patronymic = ?, date_of_birth = ?, gender = gender.gender_id, citizenship = ?, family_status = family_status.family_status_id, website = ?, email = ?, place_of_work = ?, photo = ?  WHERE contact_id = ?";

    private final String INSERT_CONTACT_QUERY = "INSERT INTO contact(surname, name, patronymic, date_of_birth, gender, citizenship, family_status, website, email, place_of_work, photo)" +
            " SELECT ?,?,?,?,gender.gender_id,?,family_status.family_status_id,?,?,?,? FROM gender,family_status WHERE gender.gender_value = ? AND family_status.family_status_value = ?";

    private final String DELETE_CONTACT_QUERY = "DELETE FROM contact WHERE contact_id = ?";


    private final String SELECT_CONTACTS_BY_BIRTHDATE = "SELECT name, patronymic, email FROM contact WHERE DAY(date_of_birth) = ? AND MONTH (date_of_birth) = ?";

    private final String SELECT_CONTACTS_LIMIT_QUERY = "SELECT c.*, g.gender_value, f_s.family_status_value " +
            " FROM contact AS c INNER JOIN gender AS g ON c.gender = g.gender_id" +
            " INNER JOIN family_status AS f_s ON c.family_status = f_s.family_status_id" +
            " LIMIT ?,?";

    private final String SELECT_CONTACTS_COUNT_QUERY = "SELECT count(*) FROM contact";


    private final String SELECT_BY_FIELDS_BASE_QUERY = "SELECT c.*, gender.gender_value, family_status.family_status_value, a.* FROM contact AS c " +
            "INNER JOIN address AS a ON c.contact_id = a.contact_id " +
            "INNER JOIN family_status ON c.family_status = family_status.family_status_id " +
            "INNER JOIN gender ON c.gender = gender.gender_id ";

    private final String SELECT_COUNT_BY_FIELDS_BASE_QUERY = "SELECT count(*) " +
            "FROM contact AS c " +
            "INNER JOIN address AS a ON c.contact_id = a.contact_id " +
            "INNER JOIN family_status ON c.family_status = family_status.family_status_id " +
            "INNER JOIN gender ON c.gender = gender.gender_id ";


    public JdbcContactDao(Transaction transaction) {
        this.transaction = transaction;
    }

    public long save(Contact contact) throws DaoException {
        logger.info("Save contact: {}", contact);

        if (contact == null) throw new DaoException("Contact is null");
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        long id = 0;
        try {
            cn = transaction.getConnection();
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

            st.setLong(9, contact.getPhoto());
            if (contact.getGender() != null)
                st.setString(10, contact.getGender().name());
            else st.setString(10, "none");
            if (contact.getFamilyStatus() != null)
                st.setString(11, contact.getFamilyStatus().name());
            else st.setString(11, "none");
            st.executeUpdate();

            rs = st.getGeneratedKeys();
            if (rs.next())
                id = rs.getLong(1);


        } catch (SQLException e) {
            throw new DaoException("Exception during saving contact in the database", e);

        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }

        return id;

    }

    public void delete(long id) throws DaoException {
        logger.info("Delete contact with id: {}", id);
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_CONTACT_QUERY);
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during deleting contact from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, null);
        }
    }

    public void update(Contact contact) throws DaoException {
        logger.info("Update contact: {}", contact);
        if (contact == null) return;
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(UPDATE_CONTACT_QUERY);
            if (contact.getGender() != null)
                st.setString(1, contact.getGender().name().toUpperCase());
            else {
                st.setString(1, "none");
            }
            if (contact.getFamilyStatus() != null)
                st.setString(2, contact.getFamilyStatus().name().toUpperCase());
            else {
                st.setString(2, "none");
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
            st.setLong(11, contact.getPhoto());

            st.setLong(12, contact.getContactId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Exception during updating contact in the database", e);
        } finally {

            DBResourceManager.closeResources(null, st, null);

        }
    }


    @Override
    public Contact getContactById(long id) throws DaoException {
        logger.info("Fetch contact with id: {}", id);
        Contact contact = null;
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_BY_ID_QUERY);
            st.setLong(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                long contactId = rs.getLong("contact_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                Date dateOfBirth = rs.getDate("date_of_birth");
                Contact.Gender gender = null;
                try {
                    gender = Contact.Gender.valueOf(rs.getString("gender_value").toUpperCase());
                } catch (Exception e) {
                    gender = null;
                }
                String citizenship = rs.getString("citizenship");
                Contact.FamilyStatus familyStatus = null;
                try {
                    familyStatus = Contact.FamilyStatus.valueOf(rs.getString("family_status_value").toUpperCase());
                } catch (Exception e) {
                    familyStatus = null;
                }
                String website = rs.getString("website");
                String email = rs.getString("email");
                String placeOfWork = rs.getString("place_of_work");
                long photo = rs.getLong("photo");
                contact = new Contact(contactId, name, surname);
                contact.setPatronymic(patronymic);
                contact.setDateOfBirth(dateOfBirth);
                contact.setGender(gender);
                contact.setCitizenship(citizenship);
                contact.setFamilyStatus(familyStatus);
                contact.setWebsite(website);
                contact.setEmail(email);
                contact.setPlaceOfWork(placeOfWork);
                contact.setPhoto(photo);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during contact retrieval from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }

        return contact;
    }


    @Override
    public ArrayList<Contact> findContactsByFieldsLimit(SearchDTO dto, int from, int count) throws DaoException {
        logger.info("Fetch contact by fields: {}, starting from index: {}, count: {}", dto, from, count);

        if (dto == null) throw new DaoException("Search DTO is null");
        DynamicPreparedQueryBuilder builder = new DynamicPreparedQueryBuilder(SELECT_BY_FIELDS_BASE_QUERY);
        ArrayList<Object> parameters = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        if (StringUtils.isNotBlank(dto.getSurname())) {
            parameters.add(appendPercents(dto.getSurname()));
            builder.appendWhereClause("c.surname");
        }

        if (StringUtils.isNotBlank(dto.getName())) {
            parameters.add(appendPercents(dto.getName()));
            builder.appendWhereClause("c.name");
        }
        if (StringUtils.isNotBlank(dto.getPatronymic())) {
            parameters.add(appendPercents(dto.getPatronymic()));
            builder.appendWhereClause("c.patronymic");
        }
        if (dto.getFromDate() != null || dto.getToDate() != null) {
            if (dto.getFromDate() != null) {
                parameters.add(new java.sql.Date(dto.getFromDate().getTime()));
            } else {
                DateTime date = formatter.parseDateTime("1000-01-01");
                parameters.add(new java.sql.Date(date.toDate().getTime()));
            }
            if (dto.getToDate() != null) {
                parameters.add(new java.sql.Date(dto.getToDate().getTime()));
            } else {
                DateTime date = formatter.parseDateTime("9999-12-31");
                parameters.add(new java.sql.Date(date.toDate().getTime()));
            }
            builder.appendBetween("c.date_of_birth");
        }
        if (dto.getGender() != null) {
            parameters.add(dto.getGender().name());
            builder.appendWhereClause("gender.gender_value");
        }
        if (dto.getFamilyStatus() != null) {
            parameters.add(dto.getFamilyStatus().name());
            builder.appendWhereClause("family_status.family_status_value");
        }
        if (StringUtils.isNotBlank(dto.getCitizenship())) {
            parameters.add(appendPercents(dto.getCitizenship()));
            builder.appendWhereClause("c.citizenship");
        }
        if (StringUtils.isNotBlank(dto.getCountry())) {
            parameters.add(appendPercents(dto.getCountry()));
            builder.appendWhereClause("a.country");
        }
        if (StringUtils.isNotBlank(dto.getCity())) {
            parameters.add(appendPercents(dto.getCity()));
            builder.appendWhereClause("a.city");
        }
        if (StringUtils.isNotBlank(dto.getStreet())) {
            parameters.add(appendPercents(dto.getStreet()));
            builder.appendWhereClause("a.street");
        }
        if (StringUtils.isNotBlank(dto.getHouse())) {
            parameters.add(appendPercents(dto.getHouse()));
            builder.appendWhereClause("a.house");
        }
        if (StringUtils.isNotBlank(dto.getApartment())) {
            parameters.add(appendPercents(dto.getApartment()));
            builder.appendWhereClause("a.apartment");
        }
        if (StringUtils.isNotBlank(dto.getZipCode())) {
            parameters.add(appendPercents(dto.getZipCode()));
            builder.appendWhereClause("a.zip_code");
        }
        builder.appendLimit(from, count);


        ArrayList<Contact> contacts = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(builder.getQuery().toString());

            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i) != null) {
                    if (parameters.get(i) instanceof String) {
                        st.setString(i + 1, (String) parameters.get(i));
                    } else if (parameters.get(i) instanceof java.sql.Date) {
                        st.setDate(i + 1, (java.sql.Date) parameters.get(i));
                    } else throw new DaoException("Exception during contacts retrieval from the database");
                }
            }


            rs = st.executeQuery();
            while (rs.next()) {
                long foundContactId = rs.getLong("contact_id");
                String foundName = rs.getString("name");
                String foundSurname = rs.getString("surname");
                String foundPatronymic = rs.getString("patronymic");
                Date foundDateOfBirth = rs.getDate("date_of_birth");
                Contact.Gender foundGender = null;
                try {
                    foundGender = Contact.Gender.valueOf(rs.getString("gender_value").toUpperCase());
                } catch (Exception e) {
//                    throw new DaoException("Illegal gender value", e);
                }
                String foundCitizenship = rs.getString("citizenship");
                Contact.FamilyStatus foundFamilyStatus = null;
                try {
                    foundFamilyStatus = Contact.FamilyStatus.valueOf(rs.getString("family_status_value").toUpperCase());
                } catch (Exception e) {
//                    throw new DaoException("Illegal famile status value", e);
                }
                String foundWebsite = rs.getString("website");
                String foundEmail = rs.getString("email");
                String foundPlaceOfWork = rs.getString("place_of_work");
                long foundPhoto = rs.getLong("photo");
                Contact contact = new Contact(foundContactId, foundName, foundSurname);
                contact.setPatronymic(foundPatronymic);
                contact.setDateOfBirth(foundDateOfBirth);
                contact.setGender(foundGender);
                contact.setCitizenship(foundCitizenship);
                contact.setFamilyStatus(foundFamilyStatus);
                contact.setWebsite(foundWebsite);
                contact.setEmail(foundEmail);
                contact.setPlaceOfWork(foundPlaceOfWork);
                contact.setPhoto(foundPhoto);

                contacts.add(contact);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during contacts retrieval from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }

        return contacts;
    }


    @Override
    public ArrayList<Contact> getByBirthDate(Date date) throws DaoException {
        logger.info("Fetch contact by birthday, day: {}, month: {}", new DateTime(date).getDayOfMonth(), new DateTime(date).getMonthOfYear());

        if (date == null) throw new DaoException("Date is null");
        ArrayList<Contact> contacts = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_CONTACTS_BY_BIRTHDATE);
            st.setInt(1, new DateTime(date).getDayOfMonth());
            st.setInt(2, new DateTime(date).getMonthOfYear());


            rs = st.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String patronymic = rs.getString("patronymic");
                String email = rs.getString("email");
                Contact contact = new Contact();
                contact.setName(name);
                contact.setPatronymic(patronymic);
                contact.setEmail(email);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during contact retrieval from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }

        return contacts;
    }



    @Override
    public ArrayList<Contact> getContactsLimit(int startingFrom, int count) throws DaoException {
        logger.info("Fetch contact starting from index: {}, count: {}", startingFrom, count);
        ArrayList<Contact> contacts = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_CONTACTS_LIMIT_QUERY);
            st.setInt(1, startingFrom);
            st.setInt(2, count);
            rs = st.executeQuery();
            while (rs.next()) {
                //contact info
                long contactId = rs.getLong("contact_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                Date dateOfBirth = rs.getDate("date_of_birth");
                Contact.Gender gender = null;
                try {
                    gender = Contact.Gender.valueOf(rs.getString("gender_value").toUpperCase());
                } catch (Exception e) {
                    gender = null;
                }
                String citizenship = rs.getString("citizenship");
                Contact.FamilyStatus familyStatus = null;
                try {
                    familyStatus = Contact.FamilyStatus.valueOf(rs.getString("family_status_value").toUpperCase());
                } catch (Exception e) {
                    familyStatus = null;
                }
                String website = rs.getString("website");
                String email = rs.getString("email");
                String placeOfWork = rs.getString("place_of_work");
                long photo = rs.getLong("photo");
                Contact contact = new Contact(contactId, name, surname);
                contact.setPatronymic(patronymic);
                contact.setDateOfBirth(dateOfBirth);
                contact.setGender(gender);
                contact.setCitizenship(citizenship);
                contact.setFamilyStatus(familyStatus);
                contact.setWebsite(website);
                contact.setEmail(email);
                contact.setPlaceOfWork(placeOfWork);
                contact.setPhoto(photo);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during contacts retrieval from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }
        return contacts;
    }

    @Override
    public int getContactsCount() throws DaoException {
        logger.info("Fetch contact's count");
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        int count = 0;
        try {
            cn = transaction.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(SELECT_CONTACTS_COUNT_QUERY);
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during contacts retrieval count from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }
        return count;
    }

    @Override
    public int getContactsSearchResultCount(SearchDTO dto) throws DaoException {
        logger.info("Fetch search result contacts count: {}", dto);
        if (dto == null) throw new DaoException("Search DTO is null");
        DynamicPreparedQueryBuilder builder = new DynamicPreparedQueryBuilder(SELECT_COUNT_BY_FIELDS_BASE_QUERY);
        ArrayList<Object> parameters = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        if (StringUtils.isNotBlank(dto.getSurname())) {
            parameters.add(appendPercents(dto.getSurname()));
            builder.appendWhereClause("c.surname");
        }

        if (StringUtils.isNotBlank(dto.getName())) {
            parameters.add(appendPercents(dto.getName()));
            builder.appendWhereClause("c.name");
        }
        if (StringUtils.isNotBlank(dto.getPatronymic())) {
            parameters.add(appendPercents(dto.getPatronymic()));
            builder.appendWhereClause("c.patronymic");
        }
        if (dto.getFromDate() != null || dto.getToDate() != null) {
            if (dto.getFromDate() != null) {
                parameters.add(new java.sql.Date(dto.getFromDate().getTime()));
            } else {
                DateTime date = formatter.parseDateTime("1000-01-01");
                parameters.add(new java.sql.Date(date.toDate().getTime()));
            }
            if (dto.getToDate() != null) {
                parameters.add(new java.sql.Date(dto.getToDate().getTime()));
            } else {
                DateTime date = formatter.parseDateTime("9999-12-31");
                parameters.add(new java.sql.Date(date.toDate().getTime()));
            }
            builder.appendBetween("c.date_of_birth");
        }
        if (dto.getGender() != null) {
            parameters.add(dto.getGender().name());
            builder.appendWhereClause("gender.gender_value");
        }
        if (dto.getFamilyStatus() != null) {
            parameters.add(dto.getFamilyStatus().name());
            builder.appendWhereClause("family_status.family_status_value");
        }
        if (StringUtils.isNotBlank(dto.getCitizenship())) {
            parameters.add(appendPercents(dto.getCitizenship()));
            builder.appendWhereClause("c.citizenship");
        }
        if (StringUtils.isNotBlank(dto.getCountry())) {
            parameters.add(appendPercents(dto.getCountry()));
            builder.appendWhereClause("a.country");
        }
        if (StringUtils.isNotBlank(dto.getCity())) {
            parameters.add(appendPercents(dto.getCity()));
            builder.appendWhereClause("a.city");
        }
        if (StringUtils.isNotBlank(dto.getStreet())) {
            parameters.add(appendPercents(dto.getStreet()));
            builder.appendWhereClause("a.street");
        }
        if (StringUtils.isNotBlank(dto.getHouse())) {
            parameters.add(appendPercents(dto.getHouse()));
            builder.appendWhereClause("a.house");
        }
        if (StringUtils.isNotBlank(dto.getApartment())) {
            parameters.add(appendPercents(dto.getApartment()));
            builder.appendWhereClause("a.apartment");
        }
        if (StringUtils.isNotBlank(dto.getZipCode())) {
            parameters.add(appendPercents(dto.getZipCode()));
            builder.appendWhereClause("a.zip_code");
        }

        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int count = 0;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(builder.getQuery().toString());
            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i) instanceof String) {
                    st.setString(i + 1, (String) parameters.get(i));
                } else if (parameters.get(i) instanceof java.sql.Date) {
                    st.setDate(i + 1, (java.sql.Date) parameters.get(i));
                } else throw new DaoException("Exception during contacts retrieval from the database");
            }
            rs = st.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during contacts retrieval from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }

        return count;
    }

    private String appendPercents(String value) {
        if (value == null) return null;
        return "%" + value + "%";
    }

}
