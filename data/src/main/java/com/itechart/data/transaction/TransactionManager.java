package com.itechart.data.transaction;

import javax.sql.DataSource;
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
            return new Transaction(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
