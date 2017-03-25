package com.itechart.data.transaction;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class TransactionManager {
    private DataSource dataSource;


    public TransactionManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/MySQLDatasource");
        } catch (NamingException e) {
            e.printStackTrace();
        }

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
