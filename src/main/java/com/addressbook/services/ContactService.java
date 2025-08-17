package com.addressbook.services;

import com.addressbook.dao.ContactDAO;
import com.addressbook.model.ContactDTO;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ContactService {
    private final ContactDAO contactDAO;
    
    public ContactService() {
        this.contactDAO = new ContactDAO();
    }
    
    public void addContact(Component parent) {
        // Create a simple dialog for adding a contact
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Location:"));
        panel.add(locationField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        
        int result = JOptionPane.showConfirmDialog(parent, panel, "Add New Contact", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
        if (result == JOptionPane.OK_OPTION) {
            try {
                ContactDTO contact = new ContactDTO();
                contact.setFirstName(firstNameField.getText().trim());
                contact.setLastName(lastNameField.getText().trim());
                contact.setLocation(locationField.getText().trim());
                contact.setPhone(phoneField.getText().trim());
                contact.setEmail(emailField.getText().trim());
                
                if (contact.getFirstName().isEmpty() || contact.getLastName().isEmpty()) {
                    JOptionPane.showMessageDialog(parent, 
                        "First Name and Last Name are required!", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                contactDAO.addContact(contact);
                JOptionPane.showMessageDialog(parent, 
                    "Contact added successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, 
                    "Error adding contact: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void editContact(Component parent) {
        List<ContactDTO> contacts = contactDAO.getAllContacts();
        if (contacts.isEmpty()) {
            JOptionPane.showMessageDialog(parent, 
                "No contacts available to edit.", 
                "Edit Contact", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Create contact selection dialog
        String[] contactNames = contacts.stream()
            .map(c -> c.getFirstName() + " " + c.getLastName())
            .toArray(String[]::new);
            
        String selected = (String) JOptionPane.showInputDialog(parent,
            "Select a contact to edit:",
            "Edit Contact",
            JOptionPane.QUESTION_MESSAGE,
            null,
            contactNames,
            contactNames[0]);
            
        if (selected != null) {
            // Find the selected contact
            ContactDTO selectedContact = contacts.stream()
                .filter(c -> (c.getFirstName() + " " + c.getLastName()).equals(selected))
                .findFirst()
                .orElse(null);
                
            if (selectedContact != null) {
                showEditDialog(parent, selectedContact);
            }
        }
    }
    
    public void deleteContact(Component parent) {
        List<ContactDTO> contacts = contactDAO.getAllContacts();
        if (contacts.isEmpty()) {
            JOptionPane.showMessageDialog(parent, 
                "No contacts available to delete.", 
                "Delete Contact", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Create contact selection dialog
        String[] contactNames = contacts.stream()
            .map(c -> c.getFirstName() + " " + c.getLastName())
            .toArray(String[]::new);
            
        String selected = (String) JOptionPane.showInputDialog(parent,
            "Select a contact to delete:",
            "Delete Contact",
            JOptionPane.QUESTION_MESSAGE,
            null,
            contactNames,
            contactNames[0]);
            
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(parent,
                "Are you sure you want to delete '" + selected + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                
            if (confirm == JOptionPane.YES_OPTION) {
                // Find and delete the selected contact
                ContactDTO contactToDelete = contacts.stream()
                    .filter(c -> (c.getFirstName() + " " + c.getLastName()).equals(selected))
                    .findFirst()
                    .orElse(null);
                    
                if (contactToDelete != null) {
                    try {
                        contactDAO.deleteContact(contactToDelete.getCid());
                        JOptionPane.showMessageDialog(parent, 
                            "Contact deleted successfully!", 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(parent, 
                            "Error deleting contact: " + e.getMessage(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
    
    public void duplicateContact(Component parent) {
        List<ContactDTO> contacts = contactDAO.getAllContacts();
        if (contacts.isEmpty()) {
            JOptionPane.showMessageDialog(parent, 
                "No contacts available to duplicate.", 
                "Duplicate Contact", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Create contact selection dialog
        String[] contactNames = contacts.stream()
            .map(c -> c.getFirstName() + " " + c.getLastName())
            .toArray(String[]::new);
            
        String selected = (String) JOptionPane.showInputDialog(parent,
            "Select a contact to duplicate:",
            "Duplicate Contact",
            JOptionPane.QUESTION_MESSAGE,
            null,
            contactNames,
            contactNames[0]);
            
        if (selected != null) {
            // Find the selected contact
            ContactDTO selectedContact = contacts.stream()
                .filter(c -> (c.getFirstName() + " " + c.getLastName()).equals(selected))
                .findFirst()
                .orElse(null);
                
            if (selectedContact != null) {
                try {
                    ContactDTO duplicate = new ContactDTO();
                    duplicate.setFirstName(selectedContact.getFirstName() + " (Copy)");
                    duplicate.setLastName(selectedContact.getLastName());
                    duplicate.setLocation(selectedContact.getLocation());
                    duplicate.setPhone(selectedContact.getPhone());
                    duplicate.setEmail(selectedContact.getEmail());
                    
                    contactDAO.addContact(duplicate);
                    JOptionPane.showMessageDialog(parent, 
                        "Contact duplicated successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(parent, 
                        "Error duplicating contact: " + e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public void mergeContacts(Component parent) {
        List<ContactDTO> contacts = contactDAO.getAllContacts();
        if (contacts.size() < 2) {
            JOptionPane.showMessageDialog(parent, 
                "At least 2 contacts are needed for merging.", 
                "Merge Contacts", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(parent, 
            "Merge functionality will be implemented in a future version.\n" +
            "This feature will allow you to combine duplicate contacts.", 
            "Merge Contacts", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void contactGroups(Component parent) {
        JOptionPane.showMessageDialog(parent, 
            "Contact Groups functionality will be implemented in a future version.\n" +
            "This feature will allow you to organize contacts into categories.", 
            "Contact Groups", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showEditDialog(Component parent, ContactDTO contact) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        
        JTextField firstNameField = new JTextField(contact.getFirstName());
        JTextField lastNameField = new JTextField(contact.getLastName());
        JTextField locationField = new JTextField(contact.getLocation());
        JTextField phoneField = new JTextField(contact.getPhone());
        JTextField emailField = new JTextField(contact.getEmail());
        
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Location:"));
        panel.add(locationField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        
        int result = JOptionPane.showConfirmDialog(parent, panel, "Edit Contact", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
        if (result == JOptionPane.OK_OPTION) {
            try {
                contact.setFirstName(firstNameField.getText().trim());
                contact.setLastName(lastNameField.getText().trim());
                contact.setLocation(locationField.getText().trim());
                contact.setPhone(phoneField.getText().trim());
                contact.setEmail(emailField.getText().trim());
                
                if (contact.getFirstName().isEmpty() || contact.getLastName().isEmpty()) {
                    JOptionPane.showMessageDialog(parent, 
                        "First Name and Last Name are required!", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                contactDAO.updateContact(contact);
                JOptionPane.showMessageDialog(parent, 
                    "Contact updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, 
                    "Error updating contact: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 