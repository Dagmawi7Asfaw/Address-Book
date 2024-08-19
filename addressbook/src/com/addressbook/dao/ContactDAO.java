package com.addressbook.dao;

import com.addressbook.model.ContactDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactDAO {
    private final ConnectionFactory connectionFactory;
    private static final Logger LOGGER = Logger.getLogger(ContactDAO.class.getName());

    public ContactDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public boolean contactExists(String firstName, String lastName, String email) {
        String query = "SELECT COUNT(*) FROM Contacts WHERE firstName = ? AND lastName = ? AND email = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if count is greater than 0
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking if contact exists", e);
            throw new RuntimeException("Failed to check if contact exists", e);
        }
        return false;
    }

    public int addContact(ContactDTO contact) {
        String query = "INSERT INTO Contacts (firstName, lastName, location, phone, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, contact.getFirstName());
            pstmt.setString(2, contact.getLastName());
            pstmt.setString(3, contact.getLocation());
            pstmt.setString(4, contact.getPhone());
            pstmt.setString(5, contact.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding contact", e);
            throw new RuntimeException("Failed to add contact", e);
        }
        return 0;
    }



    public void updateContact(ContactDTO contact) {
        String query = "UPDATE Contacts SET firstName = ?, lastName = ?, location = ?, phone = ?, email = ?, updatedAt = GETDATE() WHERE CID = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, contact.getFirstName());
            pstmt.setString(2, contact.getLastName());
            pstmt.setString(3, contact.getLocation());
            pstmt.setString(4, contact.getPhone());
            pstmt.setString(5, contact.getEmail());
            pstmt.setInt(6, contact.getCid());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating contact", e);
            throw new RuntimeException("Failed to update contact", e);
        }
    }


    public void deleteContact(int cid) {
        String query = "DELETE FROM Contacts WHERE CID = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting contact", e);
            throw new RuntimeException("Failed to delete contact", e);
        }
    }

    public List<ContactDTO> getAllContacts() {
        List<ContactDTO> contacts = new ArrayList<>();
        String query = "SELECT * FROM Contacts";
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ContactDTO contact = new ContactDTO();
                contact.setCid(rs.getInt("CID"));
                contact.setFirstName(rs.getString("firstName"));
                contact.setLastName(rs.getString("lastName"));
                contact.setLocation(rs.getString("location"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting contacts", e);
            throw new RuntimeException("Failed to get contacts", e);
        }
        return contacts;
    }
}