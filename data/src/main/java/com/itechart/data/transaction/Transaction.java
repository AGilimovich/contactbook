package com.itechart.data.transaction;

import com.itechart.data.db.DBResourceManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Transaction.
 */
public class Transaction {
    private Connection connection;

    public Transaction(Connection connection) {
        this.connection = connection;

    }

    public Connection getConnection() {
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
