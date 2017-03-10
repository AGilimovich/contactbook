package com.itechart.data.dao;

import com.itechart.data.db.JdbcDataSource;
import com.itechart.data.entity.Contact;

import java.util.Properties;

/**
 * Class for persisting and modifying data in the database.
 */
public class ContactDao implements IContactDao {

    private final String SELECT_STATEMENT = "SELECT * FROM contacts,gender,attachments,phones,addresses,family_status,phone_type";

    private JdbcDataSource ds;

    public ContactDao(JdbcDataSource ds) {
        this.ds = ds;
    }

    public void save(Contact contact) {

    }

    public void delete(Contact contact) {

    }

    public void update(Contact contact) {

    }

    public Contact getContactById(int id) {
        return null;
    }

    public Contact getContactByCriteria(Properties criteria) {
//        Connection con = ds.getConnection();
        return null;
    }
}
