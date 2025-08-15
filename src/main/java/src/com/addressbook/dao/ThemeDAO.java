package com.addressbook.dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThemeDAO {
    private final ConnectionFactory connectionFactory;
    private static final Logger LOGGER = Logger.getLogger(ThemeDAO.class.getName());

    public ThemeDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public String getSavedTheme(String username) {
        String query = "SELECT TOP 1 theme FROM UserSettings WHERE username = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("theme");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting saved theme", e);
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
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, theme);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving theme", e);
        }
    }
}
