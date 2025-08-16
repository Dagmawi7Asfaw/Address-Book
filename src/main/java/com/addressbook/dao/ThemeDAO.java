package com.addressbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThemeDAO {
    private static final Logger LOGGER = Logger.getLogger(ThemeDAO.class.getName());
    private final ConnectionFactory connectionFactory;

    public ThemeDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public String getSavedTheme(String username) {
        String query = "SELECT TOP 1 theme FROM UserSettings WHERE username = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("theme");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving saved theme", e);
        }
        return null;
    }

    public void saveTheme(String username, String theme) {
        String query = "MERGE INTO UserSettings AS target " +
                      "USING (SELECT ? AS username, ? AS theme) AS source " +
                      "ON target.username = source.username " +
                      "WHEN MATCHED THEN UPDATE SET theme = source.theme " +
                      "WHEN NOT MATCHED THEN INSERT (username, theme) VALUES (source.username, source.theme);";
        
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            stmt.setString(2, theme);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving theme", e);
        }
    }
} 