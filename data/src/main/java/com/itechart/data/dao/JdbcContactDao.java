package com.itechart.data.dao;

import com.itechart.data.db.DBResourceManager;
import com.itechart.data.dto.SearchDTO;
import com.itechart.data.entity.Contact;
import com.itechart.data.exception.DaoException;
import com.itechart.data.query.DynamicQueryBuilder;
import com.itechart.data.transaction.Transaction;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class for persisting and modifying data in the database.
 */
public class JdbcContactDao implements IContactDao {
    private Transaction transaction;

    private final String SELECT_ALL_CONTACTS_QUERY = "SELECT c.*, g.gender_value, f_s.family_status_value " +
            " FROM contact AS c INNER JOIN gender AS g ON c.gender = g.gender_id" +
            " INNER JOIN family_status AS f_s ON c.family_status = f_s.family_status_id";

    private final String SELECT_BY_ID_QUERY = "SELECT c.*, g.gender_value, f_s.family_status_value" +
            " FROM contact AS c" +
            " INNER JOIN gender AS g ON c.gender = g.gender_id" +
            " INNER JOIN family_status AS f_s ON c.family_status = f_s.family_status_id" +
            " WHERE c.contact_id = ?";

    private final String UPDATE_CONTACT_QUERY = "UPDATE contact INNER JOIN gender ON gender.gender_value = ? INNER JOIN family_status ON family_status.family_status_value = ? SET surname = ?, name = ?, patronymic = ?, date_of_birth = ?, gender = gender.gender_id, citizenship = ?, family_status = family_status.family_status_id, website = ?, email = ?, place_of_work = ?, photo = ?  WHERE contact_id = ?";

    private final String INSERT_CONTACT_QUERY = "INSERT INTO contact(surname, name, patronymic, date_of_birth, gender, citizenship, family_status, website, email, place_of_work, photo)" +
            " SELECT ?,?,?,?,gender.gender_id,?,family_status.family_status_id,?,?,?,? FROM gender,family_status WHERE gender.gender_value = ? AND family_status.family_status_value = ?";

    private final String DELETE_CONTACT_QUERY = "DELETE FROM contact WHERE contact_id = ?";

    //todo delete
//    private final String SELECT_All_BY_FIELDS_QUERY = "SELECT c.*, gender.gender_value, family_status.family_status_value, a.* FROM contact AS c " +
//            "INNER JOIN address AS a ON c.contact_id = a.contact_id " +
//            "INNER JOIN family_status ON c.family_status = family_status.family_status_id " +
//            "INNER JOIN gender ON c.gender = gender.gender_id " +
//            "WHERE (c.surname LIKE ?) AND (c.name LIKE ?) AND (c.patronymic LIKE ?) AND ((c.date_of_birth BETWEEN ? AND ?) OR (COALESCE(c.date_of_birth,'NULL') LIKE ?)) AND (gender.gender_value LIKE ?) AND (family_status.family_status_value LIKE ?) " +
//            "AND (c.citizenship LIKE ?) AND (a.country LIKE ?) AND (a.city LIKE ?) AND (a.street LIKE ?) AND (a.house LIKE ?) AND (a.apartment LIKE ?) AND (a.zip_code LIKE ?)";
//
//    private final String SELECT_BY_FIELDS_LIMIT_QUERY = "SELECT c.*, gender.gender_value, family_status.family_status_value, a.* FROM contact AS c " +
//            "INNER JOIN address AS a ON c.contact_id = a.contact_id " +
//            "INNER JOIN family_status ON c.family_status = family_status.family_status_id " +
//            "INNER JOIN gender ON c.gender = gender.gender_id " +
//            "WHERE (c.surname LIKE ?) AND (c.name LIKE ?) AND (c.patronymic LIKE ?) AND ((c.date_of_birth BETWEEN ? AND ?) OR (COALESCE(c.date_of_birth,'NULL') LIKE ?)) AND (gender.gender_value LIKE ?) AND (family_status.family_status_value LIKE ?) " +
//            "AND (c.citizenship LIKE ?) AND (a.country LIKE ?) AND (a.city LIKE ?) AND (a.street LIKE ?) AND (a.house LIKE ?) AND (a.apartment LIKE ?) AND (a.zip_code LIKE ?)" +
//            "LIMIT ?,?";

    private final String SELECT_CONTACTS_BY_BIRTHDATE = "SELECT name, email FROM contact WHERE date_of_birth = ?";

    private final String SELECT_CONTACTS_LIMIT_QUERY = "SELECT c.*, g.gender_value, f_s.family_status_value " +
            " FROM contact AS c INNER JOIN gender AS g ON c.gender = g.gender_id" +
            " INNER JOIN family_status AS f_s ON c.family_status = f_s.family_status_id" +
            " LIMIT ?,?";

    private final String SELECT_CONTACTS_COUNT_QUERY = "SELECT count(*) FROM contact";

//    private final String SELECT_COUNT_BY_FIELDS_QUERY = "SELECT count(*) " +
//            "FROM contact AS c " +
//            "INNER JOIN address AS a ON c.contact_id = a.contact_id " +
//            "INNER JOIN family_status ON c.family_status = family_status.family_status_id " +
//            "INNER JOIN gender ON c.gender = gender.gender_id " +
//            "WHERE (c.surname LIKE ?) AND (c.name LIKE ?) AND (c.patronymic LIKE ?) AND ((c.date_of_birth BETWEEN ? AND ?) OR (COALESCE(c.date_of_birth,'NULL') LIKE ?)) AND (gender.gender_value LIKE ?) AND (family_status.family_status_value LIKE ?) " +
//            "AND (c.citizenship LIKE ?) AND (a.country LIKE ?) AND (a.city LIKE ?) AND (a.street LIKE ?) AND (a.house LIKE ?) AND (a.apartment LIKE ?) AND (a.zip_code LIKE ?)";


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
        if (contact == null) throw new DaoException("Contact is null value");
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
            else st.setString(10, null);
            if (contact.getFamilyStatus() != null)
                st.setString(11, contact.getFamilyStatus().name());
            else st.setString(11, null);
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
        if (contact == null) return;
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
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
    public ArrayList<Contact> getAll() throws DaoException {
        ArrayList<Contact> contacts = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            cn = transaction.getConnection();
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
            throw new DaoException("Exception during retrieving contacts from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);

        }
        return contacts;
    }

    @Override
    public Contact getContactById(long id) throws DaoException {
        Contact contact = null;
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = transaction.getConnection();
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
        } catch (SQLException e) {
            throw new DaoException("Exception during retrieving contact from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }

        return contact;
    }

    @Override
    public ArrayList<Contact> findContactsByFieldsLimit(SearchDTO dto, int from, int count) throws DaoException {
        if (dto == null) throw new DaoException("Search DTO is null value");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        DynamicQueryBuilder builder = new DynamicQueryBuilder(SELECT_BY_FIELDS_BASE_QUERY);

        if (StringUtils.isNotBlank(dto.getSurname()))
            builder.appendClause("c.surname", dto.getSurname());
        if (StringUtils.isNotBlank(dto.getName()))
            builder.appendClause("c.name", dto.getName());
        if (StringUtils.isNotBlank(dto.getPatronymic()))
            builder.appendClause("c.patronymic", dto.getPatronymic());
        if (dto.getFromDate() != null || dto.getToDate() != null) {
            builder.appendBetween("c.date_of_birth");
            if (dto.getFromDate() != null) {
                String date = fmt.print(new DateTime(dto.getFromDate()));
                if (date != null)
                    builder.appendBetweenFirstValue("'" + date + "'");
            } else
                builder.appendBetweenFirstValue("'1000-01-01'");
            if (dto.getFromDate() != null) {
                String date = fmt.print(new DateTime(dto.getToDate()));
                if (date != null)
                    builder.appendBetweenSecondValue("'" + date + "'");
            } else {
                builder.appendBetweenSecondValue("'9999-12-31'");
            }
        }
        if (dto.getGender() != null)
            builder.appendClause("gender.gender_value", dto.getGender().name());
        if (dto.getFamilyStatus() != null)
            builder.appendClause("family_status.family_status_value", dto.getFamilyStatus().name());
        if (StringUtils.isNotEmpty(dto.getCitizenship()))
            builder.appendClause("c.citizenship", dto.getCitizenship());
        if (StringUtils.isNotEmpty(dto.getCountry()))
            builder.appendClause("a.country", dto.getCountry());
        if (StringUtils.isNotEmpty(dto.getCity()))
            builder.appendClause("a.city", dto.getCity());
        if (StringUtils.isNotEmpty(dto.getStreet()))
            builder.appendClause("a.street", dto.getStreet());
        if (StringUtils.isNotEmpty(dto.getHouse()))
            builder.appendClause("a.house", dto.getHouse());
        if (StringUtils.isNotEmpty(dto.getApartment()))
            builder.appendClause("a.apartment", dto.getApartment());
        if (StringUtils.isNotEmpty(dto.getZipCOde()))
            builder.appendClause("a.zip_code", dto.getZipCOde());
        builder.appendLimit(from, count);


        ArrayList<Contact> contacts = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            cn = transaction.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(builder.getQuery().toString());
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
            throw new DaoException("Exception during retrieving contacts from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }

        return contacts;
    }



    @Override
    public ArrayList<Contact> getByBirthDate(Date date) throws DaoException {
        if (date == null) throw new DaoException("Date is null value");
        ;
        ArrayList<Contact> contacts = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_CONTACTS_BY_BIRTHDATE);
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
            throw new DaoException("Exception during retrieving contact from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }

        return contacts;
    }

    @Override
    public ArrayList<Contact> getContactsLimit(int startingFrom, int count) throws DaoException {
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
                Contact.Gender gender = Contact.Gender.valueOf(rs.getString("gender_value").toUpperCase());
                String citizenship = rs.getString("citizenship");
                Contact.FamilyStatus familyStatus = Contact.FamilyStatus.valueOf(rs.getString("family_status_value").toUpperCase());
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
            throw new DaoException("Exception during retrieving contacts from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }
        return contacts;
    }

    @Override
    public int getContactsCount() throws DaoException {
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
            throw new DaoException("Exception during retrieving contacts count from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }
        return count;
    }

    @Override
    public int getContactsSearchResultCount(SearchDTO dto) throws DaoException {
        if (dto == null) throw new DaoException("Search DTO is null value");

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        DynamicQueryBuilder builder = new DynamicQueryBuilder(SELECT_COUNT_BY_FIELDS_BASE_QUERY);

        if (StringUtils.isNotBlank(dto.getSurname()))
            builder.appendClause("c.surname", dto.getSurname());
        if (StringUtils.isNotBlank(dto.getName()))
            builder.appendClause("c.name", dto.getName());
        if (StringUtils.isNotBlank(dto.getPatronymic()))
            builder.appendClause("c.patronymic", dto.getPatronymic());
        if (dto.getFromDate() != null || dto.getToDate() != null) {
            builder.appendBetween("c.date_of_birth");
            if (dto.getFromDate() != null) {
                String date = fmt.print(new DateTime(dto.getFromDate()));
                if (date != null)
                    builder.appendBetweenFirstValue("'" + date + "'");
            } else
                builder.appendBetweenFirstValue("'1000-01-01'");


            if (dto.getFromDate() != null) {
                String date = fmt.print(new DateTime(dto.getToDate()));
                if (date != null)
                    builder.appendBetweenSecondValue("'" + date + "'");
            } else {
                builder.appendBetweenSecondValue("'9999-12-31'");
            }
        }
        if (dto.getGender() != null)
            builder.appendClause("gender.gender_value", dto.getGender().name());
        if (dto.getFamilyStatus() != null)
            builder.appendClause("family_status.family_status_value", dto.getFamilyStatus().name());
        if (StringUtils.isNotEmpty(dto.getCitizenship()))
            builder.appendClause("c.citizenship", dto.getCitizenship());
        if (StringUtils.isNotEmpty(dto.getCountry()))
            builder.appendClause("a.country", dto.getCountry());
        if (StringUtils.isNotEmpty(dto.getCity()))
            builder.appendClause("a.city", dto.getCity());
        if (StringUtils.isNotEmpty(dto.getStreet()))
            builder.appendClause("a.street", dto.getStreet());
        if (StringUtils.isNotEmpty(dto.getHouse()))
            builder.appendClause("a.house", dto.getHouse());
        if (StringUtils.isNotEmpty(dto.getApartment()))
            builder.appendClause("a.apartment", dto.getApartment());
        if (StringUtils.isNotEmpty(dto.getZipCOde()))
            builder.appendClause("a.zip_code", dto.getZipCOde());

        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        int count = 0;
        try {
            cn = transaction.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(builder.getQuery().toString());
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during retrieving contacts from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }

        return count;
    }


}
