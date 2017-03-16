package com.itechart.data.dao;

import com.itechart.data.db.JdbcDataSource;
import com.itechart.data.entity.Phone;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 13.03.2017.
 */
public class JdbcPhoneDao implements IPhoneDao {
    private final String SELECT_PHONES_FOR_CONTACT_QUERY = "SELECT p.phoneId, p.countryCode, p.operatorCode, p.phoneNumber, phone_type.phoneTypeValue, p.comment, p.contact" +
            " FROM phones AS p LEFT OUTER JOIN phone_type ON p.phoneType = phone_type.phoneTypeId" +
            " WHERE p.contact = ?;";

    private final String SELECT_PHONE_FOR_ID_QUERY = "SELECT * FROM phones WHERE contact = ?";

    private final String INSERT_PHONE_QUERY = "INSERT INTO phones(countryCode, operatorCode,phoneNumber, phoneType, comment, contact)" +
            " SELECT ?, ?, ?,  p_t.phoneTypeId, ?, ? FROM  phone_type AS p_t WHERE p_t.phoneTypeValue = ?";

    private final String UPDATE_PHONE_QUERY = "UPDATE phones INNER JOIN phone_type AS p_t ON p_t.phoneTypeValue = ?" +
            "  SET countryCode=?, operatorCode=?,phoneNumber=?,phoneType = p_t.phoneTypeId, comment=? WHERE phoneId = ?";

    private final String DELETE_PHONE_QUERY = "DELETE FROM phones WHERE phoneId = ?";
    private final String DELETE_FOR_USER_QUERY = "DELETE FROM phones WHERE contact = ?";

    public JdbcPhoneDao(JdbcDataSource ds) {
        this.ds = ds;
    }

    private JdbcDataSource ds;


    @Override
    public ArrayList<Phone> getAllForContact(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<Phone> phones = new ArrayList<Phone>();
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(SELECT_PHONES_FOR_CONTACT_QUERY);
            st.setLong(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                long phone_id = rs.getLong("phoneId");
                String countryCode = rs.getString("countryCode");
                String operatorCode = rs.getString("operatorCode");
                String phoneNumber = rs.getString("phoneNumber");
                Phone.PhoneType phoneType = Phone.PhoneType.valueOf(rs.getString("phoneTypeValue").toUpperCase());
                String phone_comment = rs.getString("comment");
                long contact = rs.getLong("contact");
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
    public Phone getPhoneById(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Phone phone = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(SELECT_PHONE_FOR_ID_QUERY);
            st.setLong(1, id);
            rs = st.executeQuery();
            rs.next();
            long phone_id = rs.getLong("phoneId");
            String countryCode = rs.getString("countryCode");
            String operatorCode = rs.getString("operatorCode");
            String phoneNumber = rs.getString("phoneNumber");
            Phone.PhoneType phoneType = Phone.PhoneType.valueOf(rs.getString("phoneTypeValue").toUpperCase());
            String phone_comment = rs.getString("comment");
            long contact = rs.getLong("contact");
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
    public long save(Phone phone) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(INSERT_PHONE_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, phone.getCountryCode());
            st.setString(2, phone.getOperatorCode());
            st.setString(3, phone.getPhoneNumber());
            st.setString(4, phone.getComment());
            st.setLong(5, phone.getContact());
            st.setString(6, phone.getPhoneType().name());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next())
                return rs.getLong(1);


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


        return 0;
    }

    @Override
    public void delete(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(DELETE_PHONE_QUERY);
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

    @Override
    public void update(Phone phone) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = ds.getConnection();
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
    public void deleteForUser(long userId) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(DELETE_FOR_USER_QUERY);
            st.setLong(1, userId);
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
}
