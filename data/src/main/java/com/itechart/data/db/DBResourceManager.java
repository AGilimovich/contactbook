package com.itechart.data.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for closing opened resources of database.
 */
public class DBResourceManager {
    private static Logger logger = LoggerFactory.getLogger(DBResourceManager.class);

    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        logger.info("Closing DB resources");
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Error closing result set: {}", e.getMessage());
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Error closing statement: {}", e.getMessage());
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Error closing connection: {}", e.getMessage());
            }
        }

    }
}
