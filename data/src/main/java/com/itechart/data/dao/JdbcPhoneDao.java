package com.itechart.data.dao;

import com.itechart.data.db.JdbcDataSource;
import com.itechart.data.entity.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 13.03.2017.
 */
public class JdbcPhoneDao implements IPhoneDao {
    private final String SELECT_PHONES_FOR_CONTACT_QUERY = "SELECT p.phoneId, p.countryCode, p.operatorCode, p.phoneNumber, phone_type.phoneTypeValue, p.comment, p.contact" +
            " FROM phones AS p LEFT OUTER JOIN phone_type ON p.phoneType = phone_type.phoneTypeId" +
            " WHERE p.contact = ?;";

    private final String SELECT_PHONE_FOR_ID_QUERY = "SELECT * FROM phones WHERE contact = ?";

    public JdbcPhoneDao(JdbcDataSource ds) {
        this.ds = ds;
    }

    private JdbcDataSource ds;


    @Override
    public ArrayList<Phone> getAllForContact(int id) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<Phone> phones = new ArrayList<Phone>();
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(SELECT_PHONES_FOR_CONTACT_QUERY);
            st.setInt(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                int phone_id = rs.getInt("phoneId");
                String countryCode = rs.getString("countryCode");
                String operatorCode = rs.getString("operatorCode");
                String phoneNumber = rs.getString("phoneNumber");
                Phone.PhoneType phoneType = Phone.PhoneType.valueOf(rs.getString("phoneTypeValue").toUpperCase());
                String phone_comment = rs.getString("comment");
                int contact = rs.getInt("contact");
                Phone phone = new Phone(phone_id, countryCode, operatorCode, phoneNumber, phoneType, phone_comment, contact);
                phones.add(phone);
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
        return phones;
    }

    @Override
    public Phone getPhoneById(int id) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Phone phone = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(SELECT_PHONE_FOR_ID_QUERY);
            st.setInt(1, id);
            rs = st.executeQuery();
            rs.next();
            int phone_id = rs.getInt("phoneId");
            String countryCode = rs.getString("countryCode");
            String operatorCode = rs.getString("operatorCode");
            String phoneNumber = rs.getString("phoneNumber");
            Phone.PhoneType phoneType = Phone.PhoneType.valueOf(rs.getString("phoneTypeValue").toUpperCase());
            String phone_comment = rs.getString("comment");
            int contact = rs.getInt("contact");
            phone = new Phone(phone_id, countryCode, operatorCode, phoneNumber, phoneType, phone_comment, contact);

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

        return phone;
    }


    @Override
    public void save(Phone phone) {

    }

    @Override
    public void delete(Phone phone) {

    }

    @Override
    public void update(Phone phone) {

    }

}
