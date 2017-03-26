package com.itechart.data.dao;

import com.itechart.data.db.DBResourceManager;
import com.itechart.data.entity.Phone;
import com.itechart.data.transaction.Transaction;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 13.03.2017.
 */
public class JdbcPhoneDao implements IPhoneDao {
    private final String SELECT_PHONES_FOR_CONTACT_QUERY = "SELECT p.phone_id, p.country_code, p.operator_code, p.phone_number, phone_type.phone_type_value, p.comment, p.contact" +
            " FROM phone AS p LEFT OUTER JOIN phone_type ON p.phone_type = phone_type.phone_type_id" +
            " WHERE p.contact = ?;";

    private final String SELECT_PHONE_FOR_ID_QUERY = "SELECT * FROM phone WHERE contact = ?";

    private final String INSERT_PHONE_QUERY = "INSERT INTO phone(country_code, operator_code,phone_number, phone_type, comment, contact)" +
            " SELECT ?, ?, ?,  p_t.phone_type_id, ?, ? FROM  phone_type AS p_t WHERE p_t.phone_type_value = ?";

    private final String UPDATE_PHONE_QUERY = "UPDATE phone INNER JOIN phone_type AS p_t ON p_t.phone_type_value = ?" +
            "  SET country_code=?, operator_code=?,phone_number=?,phone_type = p_t.phone_type_id, comment=? WHERE phone_id = ?";

    private final String DELETE_PHONE_QUERY = "DELETE FROM phone WHERE phone_id = ?";
    private final String DELETE_FOR_USER_QUERY = "DELETE FROM phone WHERE contact = ?";

    public JdbcPhoneDao(Transaction transaction) {
        this.transaction = transaction;
    }

    private Transaction transaction;


    @Override
    public ArrayList<Phone> getAllForContact(long id) {
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
                Phone.PhoneType phoneType = Phone.PhoneType.valueOf(rs.getString("phone_type_value").toUpperCase());
                String phone_comment = rs.getString("comment");
                long contact = rs.getLong("contact");
                Phone phone = new Phone(phone_id, countryCode, operatorCode, phoneNumber, phoneType, phone_comment, contact);
                phones.add(phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }
        return phones;
    }

    @Override
    public Phone getPhoneById(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Phone phone = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_PHONE_FOR_ID_QUERY);
            st.setLong(1, id);
            rs = st.executeQuery();
            rs.next();
            long phone_id = rs.getLong("phone_id");
            String countryCode = rs.getString("country_code");
            String operatorCode = rs.getString("operator_code");
            String phoneNumber = rs.getString("phone_number");
            Phone.PhoneType phoneType = Phone.PhoneType.valueOf(rs.getString("phone_type_value").toUpperCase());
            String phone_comment = rs.getString("comment");
            long contact = rs.getLong("contact");
            phone = new Phone(phone_id, countryCode, operatorCode, phoneNumber, phoneType, phone_comment, contact);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }

        return phone;
    }


    @Override
    public long save(Phone phone) {
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
            st.setLong(5, phone.getContactId());
            st.setString(6, phone.getPhoneType().name());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next())
                return rs.getLong(1);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }


        return 0;
    }

    @Override
    public void delete(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_PHONE_QUERY);
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, null);
        }
    }

    @Override
    public void update(Phone phone) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(UPDATE_PHONE_QUERY);
            st.setString(1, phone.getPhoneType().name());
            st.setString(2, phone.getCountryCode());
            st.setString(3, phone.getOperatorCode());
            st.setString(4, phone.getPhoneNumber());
            st.setString(5, phone.getComment());
            st.setLong(6, phone.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            DBResourceManager.closeResources(cn, st, null);
        }

    }

    @Override
    public void deleteForContact(long userId) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_FOR_USER_QUERY);
            st.setLong(1, userId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, null);
        }
    }
}
