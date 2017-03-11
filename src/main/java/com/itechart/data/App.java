package com.itechart.data;

import com.itechart.data.dao.ContactDao;
import com.itechart.data.db.JdbcDataSource;

import java.util.ResourceBundle;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        ResourceBundle properties = ResourceBundle.getBundle("db/database");
        String JDBC_DRIVER = properties.getString("JDBC_DRIVER");
        String DB_URL = properties.getString("DB_URL");
        String DB_USER = properties.getString("DB_USER");
        String DB_PASSWORD = properties.getString("DB_PASSWORD");
        try {
            JdbcDataSource ds = new JdbcDataSource(JDBC_DRIVER, DB_URL, DB_USER, DB_PASSWORD);
            ContactDao dao = new ContactDao(ds);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
