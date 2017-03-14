package com.itechart.data.dao;

import com.itechart.data.db.JdbcDataSource;
import com.itechart.data.entity.Address;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for address entity.
 */
public class JdbcAddressDao implements IAddressDao {
    private JdbcDataSource ds;

    private final String SELECT_ADDRESS_BY_ID_QUERY = "SELECT * FROM addresses WHERE addressId = ?";
    private final String INSERT_ADDRESS_QUERY = "INSERT INTO addresses(country, city, street, house, apartment, zipCode) VALUES(?, ?, ?, ?, ?, ?)";
    private final String UPDATE_ADDRESS_QUERY = "UPDATE addresses SET country = ?, city = ?, street = ?, house = ?, apartment = ?, zipCode = ? WHERE addressId = ?";
    private final String DELETE_ADDRESS_QUERY = "DELETE FROM addresses WHERE addressId = ?";


    public JdbcAddressDao(JdbcDataSource ds) {
        this.ds = ds;
    }

    @Override
    public long save(Address address) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        long id = 0;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(INSERT_ADDRESS_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, address.getCountry());
            st.setString(2, address.getCity());
            st.setString(3, address.getStreet());
            st.setString(4, address.getHouse());
            st.setString(5, address.getApartment());
            st.setString(6, address.getZipCode());
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

    @Override
    public void delete(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(DELETE_ADDRESS_QUERY);
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
    public void update(Address address) {

    }

    @Override
    public Address getAddressById(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Address a = null;

        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(SELECT_ADDRESS_BY_ID_QUERY);
            st.setLong(1, id);
            rs = st.executeQuery();
            rs.next();
            int addressId = rs.getInt("addressId");
            String country = rs.getString("country");
            String city = rs.getString("city");
            String street = rs.getString("street");
            String house = rs.getString("house");
            String apartment = rs.getString("apartment");
            String zipCode = rs.getString("zipCode");
            a = new Address(addressId, country, city, street, house, apartment, zipCode);
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


        return a;
    }

}
