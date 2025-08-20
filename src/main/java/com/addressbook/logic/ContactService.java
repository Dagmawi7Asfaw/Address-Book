package com.addressbook.logic;

import com.addressbook.dao.ContactDAO;
import com.addressbook.model.ContactDTO;
import javax.swing.*;
import java.util.List;

/**
 * Handles all contact business logic and operations
 */
public class ContactService {
    private final ContactDAO contactDAO;
    
    public ContactService() {
        this.contactDAO = new ContactDAO();
    }
    
    /**
     * Loads all contacts from the database
     */
    public List<ContactDTO> getAllContacts() {
        return contactDAO.getAllContacts();
    }
    
    /**
     * Adds a new contact
     */
    public int addContact(ContactDTO contact) {
        return contactDAO.addContact(contact);
    }
    
    /**
     * Updates an existing contact
     */
    public void updateContact(ContactDTO contact) {
        contactDAO.updateContact(contact);
    }
    
    /**
     * Deletes a contact by ID
     */
    public void deleteContact(int cid) {
        contactDAO.deleteContact(cid);
    }
    
    /**
     * Checks if a contact is a duplicate
     */
    public boolean isDuplicateContact(ContactDTO newContact, List<ContactDTO> existingContacts) {
        for (ContactDTO existing : existingContacts) {
            if (isSameContact(newContact, existing)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Creates a ContactDTO from form data
     */
    public ContactDTO createContact(String firstName, String lastName, String location, String phone, String email) {
        ContactDTO contact = new ContactDTO();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setLocation(location);
        contact.setPhone(phone);
        contact.setEmail(email);
        return contact;
    }
    
    /**
     * Shows success message
     */
    public void showSuccessMessage(String message, java.awt.Component parentComponent) {
        JOptionPane.showMessageDialog(parentComponent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Shows error message
     */
    public void showErrorMessage(String message, java.awt.Component parentComponent) {
        JOptionPane.showMessageDialog(parentComponent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Shows confirmation dialog for delete operation
     */
    public boolean confirmDelete(java.awt.Component parentComponent) {
        int confirm = JOptionPane.showConfirmDialog(
            parentComponent, 
            "Are you sure you want to delete the selected contacts?", 
            "Delete Confirmation", 
            JOptionPane.YES_NO_OPTION
        );
        return confirm == JOptionPane.YES_OPTION;
    }
    
    /**
     * Compares two contacts to check if they are the same
     */
    private boolean isSameContact(ContactDTO contact1, ContactDTO contact2) {
        return contact1.getFirstName().trim().equals(contact2.getFirstName().trim()) &&
               contact1.getLastName().trim().equals(contact2.getLastName().trim()) &&
               contact1.getLocation().trim().equals(contact2.getLocation().trim()) &&
               contact1.getPhone().trim().equals(contact2.getPhone().trim()) &&
               contact1.getEmail().trim().equals(contact2.getEmail().trim());
    }
} 