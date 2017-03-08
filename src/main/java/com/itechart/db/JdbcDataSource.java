package com.itechart.db;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * This class provides access to MySQL database.
 */
public class JdbcDataSource implements IDataSource {


    private final String INIT_QUERY = "CREATE DATABASE IF NOT EXISTS contact_book;";
    private Connection cn;


    public JdbcDataSource() {
        ResourceBundle bundle = ResourceBundle.getBundle("db/database");
        String JDBC_DRIVER = bundle.getString("JDBC_DRIVER");
        String DB_URL = bundle.getString("DB_URL");
        String DB_USER = bundle.getString("DB_USER");
        String DB_PASSWORD = bundle.getString("DB_PASSWORD");

        try {
            Class.forName(JDBC_DRIVER);
            cn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement st = cn.createStatement();
            st.executeUpdate(INIT_QUERY);
            st.executeUpdate("USE contact_book;");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
