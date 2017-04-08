package com.itechart.data.transaction;

import com.itechart.data.db.DBResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Transaction object which includes connection to database.
 * Posses methods for committing and rolling back transactions.
 */
public class Transaction {
    private Connection connection;
    private Logger logger = LoggerFactory.getLogger(Transaction.class);


    public Transaction(Connection connection) {
        this.connection = connection;

    }

    public Connection getConnection() {
        return connection;
    }

    public void commitTransaction() {
        logger.info("Commit transaction");
        try {
            connection.commit();
            DBResourceManager.closeResources(connection, null, null);
        } catch (SQLException e) {
            logger.error("Error committing transaction: {}", e.getMessage());
        }
    }

    public void rollbackTransaction() {
        logger.info("Rollback transaction");
        try {
            connection.rollback();
            DBResourceManager.closeResources(connection, null, null);
        } catch (SQLException e) {
            logger.error("Error rolling back transaction: {}", e.getMessage());
        }
    }
}
