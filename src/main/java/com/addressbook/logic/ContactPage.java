package com.addressbook.logic;

import com.addressbook.model.ContactDTO;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Main contact page that orchestrates the contact form and table panels
 */
public class ContactPage extends JPanel {
    // Professional color scheme
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(229, 231, 235);
    
    // UI Components
    private ContactFormPanel formPanel;
    private ContactTablePanel tablePanel;
    
    // Business logic
    private ContactService contactService;
    
    // State
    private int selectedCid = -1;
    
    public ContactPage() {
        this.contactService = new ContactService();
        initializePanel();
        loadContacts();
    }
    
    // Method to access the table for font adjustments (used by Dashboard)
    public JTable getContactTable() {
        return tablePanel.getContactTable();
    }
    
    private void initializePanel() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Create main content with professional layout
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(BACKGROUND_COLOR);
        mainContent.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Create table panel (left side)
        tablePanel = new ContactTablePanel(new ContactTablePanel.TableActionListener() {
            @Override
            public void onContactSelected(int cid, String firstName, String lastName, 
                                       String location, String phone, String email) {
                selectedCid = cid;
                formPanel.setContactData(firstName, lastName, location, phone, email);
            }
        });
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        tablePanel.setBackground(CARD_BACKGROUND);
        
        // Create form panel (right side)
        formPanel = new ContactFormPanel(new ContactFormPanel.FormActionListener() {
            @Override
            public void onAddContact() {
                handleAddContact();
            }
            
            @Override
            public void onUpdateContact() {
                handleEditContact();
            }
            
            @Override
            public void onDeleteContact() {
                handleDeleteContact();
            }
            
            @Override
            public void onClearFields() {
                clearFields();
            }
        });
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        formPanel.setBackground(CARD_BACKGROUND);
        
        // Create split pane with professional styling
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, formPanel);
        splitPane.setDividerLocation(1000);
        splitPane.setResizeWeight(0.65);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setBackground(BACKGROUND_COLOR);
        
        // Set minimum sizes to prevent panels from collapsing
        tablePanel.setMinimumSize(new Dimension(600, 600));
        formPanel.setMinimumSize(new Dimension(400, 600));
        
        mainContent.add(splitPane, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);
    }
    
    private void loadContacts() {
        List<ContactDTO> contacts = contactService.getAllContacts();
        tablePanel.loadContacts(contacts);
    }
    
    private void clearFields() {
        formPanel.clearFields();
        selectedCid = -1;
        tablePanel.clearSelection();
    }
    
    private void handleAddContact() {
        if (validateFields()) {
            return;
        }
        
        if (isDuplicateContact()) {
            contactService.showErrorMessage("Contact already exists! Duplicate contact is not allowed.", this);
            return;
        }
        
        ContactDTO contact = contactService.createContact(
            formPanel.getFirstName(),
            formPanel.getLastName(),
            formPanel.getLocationText(),
            formPanel.getPhone(),
            formPanel.getEmail()
        );
        
        int cid = contactService.addContact(contact);
        contact.setCid(cid);
        tablePanel.addContact(contact);
        clearFields();
        contactService.showSuccessMessage("Contact added successfully!", this);
    }
    
    private boolean isDuplicateContact() {
        ContactDTO newContact = contactService.createContact(
            formPanel.getFirstName(),
            formPanel.getLastName(),
            formPanel.getLocationText(),
            formPanel.getPhone(),
            formPanel.getEmail()
        );
        
        List<ContactDTO> existingContacts = contactService.getAllContacts();
        return contactService.isDuplicateContact(newContact, existingContacts);
    }
    
    private void handleEditContact() {
        if (selectedCid == -1) {
            contactService.showErrorMessage("Please select a contact to edit.", this);
            return;
        }
        
        if (validateFields()) {
            return;
        }
        
        ContactDTO contact = contactService.createContact(
            formPanel.getFirstName(),
            formPanel.getLastName(),
            formPanel.getLocationText(),
            formPanel.getPhone(),
            formPanel.getEmail()
        );
        contact.setCid(selectedCid);
        
        contactService.updateContact(contact);
        loadContacts();
        clearFields();
        contactService.showSuccessMessage("Contact updated successfully!", this);
    }
    
    private boolean validateFields() {
        return ContactValidator.validateFields(
            formPanel.getFirstName(),
            formPanel.getLastName(),
            formPanel.getLocationText(),
            formPanel.getPhone(),
            formPanel.getEmail(),
            this
        );
    }
    
    private void handleDeleteContact() {
        int[] selectedRows = tablePanel.getSelectedRows();
        if (selectedRows.length > 0) {
            if (contactService.confirmDelete(this)) {
                for (int rowIndex : selectedRows) {
                    int cid = tablePanel.getSelectedCid();
                    contactService.deleteContact(cid);
                }
                loadContacts();
                clearFields();
                contactService.showSuccessMessage("Selected contacts deleted successfully!", this);
            }
        } else {
            contactService.showErrorMessage("Please select at least one contact to delete.", this);
        }
    }
}