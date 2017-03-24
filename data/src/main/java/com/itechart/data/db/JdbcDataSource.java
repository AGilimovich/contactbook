package com.itechart.data.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class provides access to MySQL database.
 */
public class JdbcDataSource {

    private String url;
    private String password;
    private String name;


    public JdbcDataSource(String driver, String url, String name, String password) throws ClassNotFoundException {
        Class.forName(driver);
        this.url = url;
        this.name = name;
        this.password = password;

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, name, password);
    }


}
