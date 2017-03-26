package com.itechart.data.dao;

import com.itechart.data.db.DBResourceManager;
import com.itechart.data.entity.Address;
import com.itechart.data.transaction.Transaction;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DAO class for address entity.
 */
public class JdbcAddressDao implements IAddressDao {
    private Transaction transaction;

    private final String SELECT_ADDRESS_BY_ID_QUERY = "SELECT * FROM address WHERE address_id = ?";
    private final String INSERT_ADDRESS_QUERY = "INSERT INTO address(country, city, street, house, apartment, zip_code) VALUES(?, ?, ?, ?, ?, ?)";
    private final String UPDATE_ADDRESS_QUERY = "UPDATE address SET country = ?, city = ?, street = ?, house = ?, apartment = ?, zip_code = ? WHERE address_id = ?";
    private final String DELETE_ADDRESS_QUERY = "DELETE FROM address WHERE address_id = ?";



    private String SELECT_ALL_FOR_CONTACT_QUERY = "SELECT  a.country, a.city, a.street, a.house, a.apartment, a.zip_code" +
            " FROM address AS a" +
            " INNER JOIN contact_address AS c_a ON c_a.address_id = a.address_id" +
            " WHERE c_a.contact_id = ?";

    private String DELETE_ADDRESSES_FOR_CONTACT_QUERY = "DELETE FROM address WHERE address_id IN (SELECT c_a.address_id FROM contact_address AS c_a WHERE c_a.contact_id = ?)";
    public JdbcAddressDao(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public long save(Address address) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        long id = 0;
        try {
            cn = transaction.getConnection();

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
            DBResourceManager.closeResources(cn, st, rs);
        }
        return id;
    }

    @Override
    public ArrayList<Address> getAllForContact(long contactId) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<Address> addresses = new ArrayList<>();
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_ALL_FOR_CONTACT_QUERY);
            st.setLong(1, contactId);
            rs = st.executeQuery();
            while (rs.next()) {
                int addressId = rs.getInt("address_id");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String street = rs.getString("street");
                String house = rs.getString("house");
                String apartment = rs.getString("apartment");
                String zipCode = rs.getString("zip_code");
                Address address = new Address(addressId, country, city, street, house, apartment, zipCode);
                addresses.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }
        return addresses;
    }

    @Override
    public void delete(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_ADDRESS_QUERY);
            st.setLong(1, id);
            st.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, null);
        }
    }

    @Override
    public void deleteForContact(long contactId) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_ADDRESSES_FOR_CONTACT_QUERY);
            st.setLong(1, contactId);
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, null);
        }
    }

    @Override
    public void update(Address address) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(UPDATE_ADDRESS_QUERY);
            st.setString(1, address.getCountry());
            st.setString(2, address.getCity());
            st.setString(3, address.getStreet());
            st.setString(4, address.getHouse());
            st.setString(5, address.getApartment());
            st.setString(6, address.getZipCode());
            st.setLong(7, address.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }
    }

    @Override
    public Address getAddressById(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Address a = null;

        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_ADDRESS_BY_ID_QUERY);
            st.setLong(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                int addressId = rs.getInt("address_id");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String street = rs.getString("street");
                String house = rs.getString("house");
                String apartment = rs.getString("apartment");
                String zipCode = rs.getString("zip_code");
                a = new Address(addressId, country, city, street, house, apartment, zipCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }


        return a;
    }

}
