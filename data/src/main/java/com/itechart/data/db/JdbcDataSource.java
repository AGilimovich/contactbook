package com.itechart.data.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class provides access to MySQL database.
 */
public class JdbcDataSource implements IDataSource {


//    private final String INIT_QUERY = "CREATE DATABASE IF NOT EXISTS contact_book;";


    public JdbcDataSource(String driver) throws ClassNotFoundException {

        Class.forName(driver);

    }

    public Connection getConnection(String url, String name, String password) throws SQLException {
        return DriverManager.getConnection(url, name, password);
//        Statement st = cn.createStatement();


        //        st.executeUpdate(INIT_QUERY);
//        st.executeUpdate("USE contact_book;");
    }


}
