package com.itechart.data.transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for creating transactions.
 */
public class TransactionManager {
    private DataSource dataSource;


    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Transaction getTransaction() {
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            return new Transaction(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
