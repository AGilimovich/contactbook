package com.itechart.data.dao;

import com.itechart.data.db.DBResourceManager;
import com.itechart.data.entity.Address;
import com.itechart.data.exception.DaoException;
import com.itechart.data.transaction.Transaction;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for address entity.
 */
public class JdbcAddressDao implements IAddressDao {
    private Transaction transaction;

    private final String INSERT_ADDRESS_QUERY = "INSERT INTO address(country, city, street, house, apartment, zip_code, contact_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final String SELECT_ADDRESS_BY_CONTACT_ID_QUERY = "SELECT * FROM address WHERE contact_id = ?";
    private final String DELETE_ADDRESS_FOR_CONTACT_QUERY = "DELETE FROM address WHERE contact_id = ?";
    private final String UPDATE_ADDRESS_QUERY = "UPDATE address SET country = ?, city = ?, street = ?, house = ?, apartment = ?, zip_code = ? WHERE contact_id = ?";


    public JdbcAddressDao(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public long save(Address address) throws DaoException {
        if (address == null) throw new DaoException("Address is null");
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
            st.setLong(7, address.getContactId());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next())
                id = rs.getLong(1);
        } catch (SQLException e) {
            throw new DaoException("Exception during saving address in database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }
        return id;
    }

    @Override
    public void deleteForContact(long contactId) throws DaoException {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_ADDRESS_FOR_CONTACT_QUERY);
            st.setLong(1, contactId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during deleting address from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, null);
        }
    }

    @Override
    public void update(Address address) throws DaoException {
        if (address == null) throw new DaoException("Address is null");
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
            st.setLong(7, address.getContactId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Exception during updating address in the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }
    }

    @Override
    public Address getAddressByContactId(long contactId) throws DaoException {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Address a = null;

        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_ADDRESS_BY_CONTACT_ID_QUERY);
            st.setLong(1, contactId);
            rs = st.executeQuery();
            rs.next();
            int addressId = rs.getInt("address_id");
            String country = rs.getString("country");
            String city = rs.getString("city");
            String street = rs.getString("street");
            String house = rs.getString("house");
            String apartment = rs.getString("apartment");
            String zipCode = rs.getString("zip_code");
            long extractedContactId = rs.getLong("contact_id");
            a = new Address(addressId, country, city, street, house, apartment, zipCode, extractedContactId);
        } catch (SQLException e) {
            throw new DaoException("Exception during retrieving address from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }


        return a;
    }

}
