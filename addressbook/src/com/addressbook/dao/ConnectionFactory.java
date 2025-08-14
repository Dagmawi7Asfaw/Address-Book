package com.addressbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private static final String DEFAULT_URL = "jdbc:sqlserver://localhost:1433;databaseName=AddressBook;encrypt=false";
    private static final String ENV_URL = System.getenv("DB_URL");
    private static final String URL = (ENV_URL != null && !ENV_URL.isEmpty()) ? ENV_URL : DEFAULT_URL;

    private static final String DEFAULT_USERNAME = "sa";
    private static final String ENV_USERNAME = System.getenv("DB_USERNAME");
    private static final String USERNAME = (ENV_USERNAME != null && !ENV_USERNAME.isEmpty()) ? ENV_USERNAME : DEFAULT_USERNAME;

    private static final String DEFAULT_PASSWORD = "REDACTED";
    private static final String ENV_PASSWORD = System.getenv("DB_PASSWORD");
    private static final String PASSWORD = (ENV_PASSWORD != null && !ENV_PASSWORD.isEmpty()) ? ENV_PASSWORD : DEFAULT_PASSWORD;

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