package com.addressbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Interface for ConnectionFactory
interface IConnectionFactory {
    Connection getConnection();
}

public class ConnectionFactory implements IConnectionFactory {
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=AddressBook;encrypt=false";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "REDACTED"; // Set your SQL Server password

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

    private Connection connection;

    public ConnectionFactory() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error initializing ConnectionFactory", e);
            throw new RuntimeException("Failed to initialize ConnectionFactory", e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                LOGGER.info("Connected successfully.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting connection", e);
            throw new RuntimeException("Failed to get connection", e);
        }
        return connection;
    }
}