package com.itechart.data.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for creating transactions.
 */
public class TransactionManager {
    private Logger logger = LoggerFactory.getLogger(TransactionManager.class);
    private DataSource dataSource;


    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Transaction getTransaction() {
        logger.info("Create transaction");
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            return new Transaction(connection);
        } catch (SQLException e) {
            logger.error("Error creating transaction: {}", e.getMessage());
        }
        return null;
    }


}
