package com.itechart.data.dao;

import com.itechart.data.db.DBResourceManager;
import com.itechart.data.entity.Phone;
import com.itechart.data.exception.DaoException;
import com.itechart.data.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implementation of phone DAO using jdbc.
 */
public class JdbcPhoneDao implements IPhoneDao {
    private Logger logger = LoggerFactory.getLogger(JdbcFileDao.class);

    private final String SELECT_PHONES_FOR_CONTACT_QUERY = "SELECT p.phone_id, p.country_code, p.operator_code, p.phone_number, phone_type.phone_type_value, p.comment, p.contact_id" +
            " FROM phone AS p LEFT OUTER JOIN phone_type ON p.phone_type = phone_type.phone_type_id" +
            " WHERE p.contact_id = ?;";

    private final String INSERT_PHONE_QUERY = "INSERT INTO phone(country_code, operator_code,phone_number, phone_type, comment, contact_id)" +
            " SELECT ?, ?, ?,  p_t.phone_type_id, ?, ? FROM  phone_type AS p_t WHERE p_t.phone_type_value = ?";

    private final String UPDATE_PHONE_QUERY = "UPDATE phone INNER JOIN phone_type AS p_t ON p_t.phone_type_value = ?" +
            "  SET country_code=?, operator_code=?,phone_number=?,phone_type = p_t.phone_type_id, comment=? WHERE phone_id = ?";

    private final String DELETE_PHONE_QUERY = "DELETE FROM phone WHERE phone_id = ?";

    public JdbcPhoneDao(Transaction transaction) {
        this.transaction = transaction;
    }

    private Transaction transaction;


    @Override
    public ArrayList<Phone> getAllForContact(long id) throws DaoException {
        logger.info("Fetch all phones for contact with id: {}", id);

        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<Phone> phones = new ArrayList<Phone>();
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_PHONES_FOR_CONTACT_QUERY);
            st.setLong(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                long phone_id = rs.getLong("phone_id");
                String countryCode = rs.getString("country_code");
                String operatorCode = rs.getString("operator_code");
                String phoneNumber = rs.getString("phone_number");
                Phone.PhoneType phoneType = null;
                try {
                    phoneType = Phone.PhoneType.valueOf(rs.getString("phone_type_value").toUpperCase());
                } catch (Exception e) {
                    phoneType = null;
                }
                String phone_comment = rs.getString("comment");
                long contact = rs.getLong("contact_id");
                Phone phone = new Phone(phone_id, countryCode, operatorCode, phoneNumber, phoneType, phone_comment, contact);
                phones.add(phone);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception during phones retrieval from the database", e);

        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }
        return phones;
    }


    @Override
    public long save(Phone phone) throws DaoException {
        logger.info("Save phone: {}", phone);
        if (phone == null) throw new DaoException("Phone is null");
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(INSERT_PHONE_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, phone.getCountryCode());
            st.setString(2, phone.getOperatorCode());
            st.setString(3, phone.getPhoneNumber());
            st.setString(4, phone.getComment());
            st.setLong(5, phone.getContact());
            if (phone.getPhoneType() != null) {
                st.setString(6, phone.getPhoneType().name());
            } else {
                st.setString(6, "none");
            }
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next())
                return rs.getLong(1);
        } catch (SQLException e) {
            throw new DaoException("Exception during saving phone in the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }
        return 0;
    }

    @Override
    public void delete(long id) throws DaoException {
        logger.info("Delete phone with id: {}", id);
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_PHONE_QUERY);
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during deleting phone from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, null);
        }
    }

    @Override
    public void update(Phone phone) throws DaoException {
        logger.info("Update phone: {}", phone);
        if (phone == null) throw new DaoException("Phone is null");
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(UPDATE_PHONE_QUERY);
            if (phone.getPhoneType() != null) {
                st.setString(1, phone.getPhoneType().name());
            } else {
                st.setString(1, "none");
            }
            st.setString(2, phone.getCountryCode());
            st.setString(3, phone.getOperatorCode());
            st.setString(4, phone.getPhoneNumber());
            st.setString(5, phone.getComment());
            st.setLong(6, phone.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Exception during updating phone in the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, null);
        }
    }

}
