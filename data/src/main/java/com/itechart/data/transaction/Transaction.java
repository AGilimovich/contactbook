package com.itechart.data.transaction;

import com.itechart.data.db.DBResourceManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class Transaction {
    private Connection connection;

    public Transaction(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void commitTransaction() {
        try {
            connection.commit();
            DBResourceManager.closeResources(connection, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollbackTransaction() {
        try {
            connection.rollback();
            DBResourceManager.closeResources(connection, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
