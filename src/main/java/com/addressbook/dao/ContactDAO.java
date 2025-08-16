package com.addressbook.dao;

import com.addressbook.model.ContactDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Interface defining the contract for contact data access
interface ContactDAOInterface {
    int addContact(ContactDTO contact);

    void updateContact(ContactDTO contact);

    void deleteContact(int cid);

    List<ContactDTO> getAllContacts();
}

// Implementation of the ContactDAOInterface
public class ContactDAO implements ContactDAOInterface {
    private final ConnectionFactory connectionFactory;
    private static final Logger LOGGER = Logger.getLogger(ContactDAO.class.getName());

    public ContactDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    @Override
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
        return 0; // You may want to return the generated ID instead
    }

    @Override
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

    @Override
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

    @Override
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