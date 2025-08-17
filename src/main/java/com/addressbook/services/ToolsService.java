package com.addressbook.services;

import com.addressbook.dao.ContactDAO;
import com.addressbook.model.ContactDTO;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ToolsService {
    private final ContactDAO contactDAO;
    
    public ToolsService() {
        this.contactDAO = new ContactDAO();
    }
    
    public void searchAction(Component parent) {
        String searchTerm = JOptionPane.showInputDialog(parent,
            "Enter search term:",
            "Search Contacts",
            JOptionPane.PLAIN_MESSAGE);
            
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            List<ContactDTO> contacts = contactDAO.getAllContacts();
            List<ContactDTO> results = contacts.stream()
                .filter(c -> c.getFirstName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                           c.getLastName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                           c.getEmail().toLowerCase().contains(searchTerm.toLowerCase()) ||
                           c.getPhone().contains(searchTerm) ||
                           c.getLocation().toLowerCase().contains(searchTerm.toLowerCase()))
                .toList();
                
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(parent,
                    "No contacts found matching '" + searchTerm + "'",
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                showSearchResults(parent, results, searchTerm);
            }
        }
    }
    
    public void sortAction(Component parent) {
        String[] sortOptions = {"First Name", "Last Name", "Location", "Phone", "Email"};
        String selected = (String) JOptionPane.showInputDialog(parent,
            "Sort contacts by:",
            "Sort Contacts",
            JOptionPane.QUESTION_MESSAGE,
            null,
            sortOptions,
            sortOptions[0]);
            
        if (selected != null) {
            List<ContactDTO> contacts = contactDAO.getAllContacts();
            List<ContactDTO> sorted = switch (selected) {
                case "First Name" -> contacts.stream()
                    .sorted((c1, c2) -> c1.getFirstName().compareToIgnoreCase(c2.getFirstName()))
                    .toList();
                case "Last Name" -> contacts.stream()
                    .sorted((c1, c2) -> c1.getLastName().compareToIgnoreCase(c2.getLastName()))
                    .toList();
                case "Location" -> contacts.stream()
                    .sorted((c1, c2) -> c1.getLocation().compareToIgnoreCase(c2.getLocation()))
                    .toList();
                case "Phone" -> contacts.stream()
                    .sorted((c1, c2) -> c1.getPhone().compareTo(c2.getPhone()))
                    .toList();
                case "Email" -> contacts.stream()
                    .sorted((c1, c2) -> c1.getEmail().compareToIgnoreCase(c2.getEmail()))
                    .toList();
                default -> contacts;
            };
            
            showSortResults(parent, sorted, selected);
        }
    }
    
    public void filterAction(Component parent) {
        String[] filterOptions = {"By Location", "By Email Domain", "By Phone Prefix"};
        String selected = (String) JOptionPane.showInputDialog(parent,
            "Filter contacts by:",
            "Filter Contacts",
            JOptionPane.QUESTION_MESSAGE,
            null,
            filterOptions,
            filterOptions[0]);
            
        if (selected != null) {
            String filterValue = JOptionPane.showInputDialog(parent,
                "Enter filter value:",
                "Filter Value",
                JOptionPane.PLAIN_MESSAGE);
                
            if (filterValue != null && !filterValue.trim().isEmpty()) {
                List<ContactDTO> contacts = contactDAO.getAllContacts();
                List<ContactDTO> filtered = switch (selected) {
                    case "By Location" -> contacts.stream()
                        .filter(c -> c.getLocation().toLowerCase().contains(filterValue.toLowerCase()))
                        .toList();
                    case "By Email Domain" -> contacts.stream()
                        .filter(c -> c.getEmail().toLowerCase().contains(filterValue.toLowerCase()))
                        .toList();
                    case "By Phone Prefix" -> contacts.stream()
                        .filter(c -> c.getPhone().startsWith(filterValue))
                        .toList();
                    default -> contacts;
                };
                
                if (filtered.isEmpty()) {
                    JOptionPane.showMessageDialog(parent,
                        "No contacts found matching the filter criteria.",
                        "Filter Results",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showFilterResults(parent, filtered, selected, filterValue);
                }
            }
        }
    }
    
    public void backupAction(Component parent) {
        List<ContactDTO> contacts = contactDAO.getAllContacts();
        if (contacts.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                "No contacts to backup.",
                "Backup",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(parent,
            "Backup functionality will be implemented in a future version.\n" +
            "This will create a backup file of all contacts.",
            "Backup",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void restoreAction(Component parent) {
        JOptionPane.showMessageDialog(parent,
            "Restore functionality will be implemented in a future version.\n" +
            "This will restore contacts from a backup file.",
            "Restore",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void settingsAction(Component parent) {
        JOptionPane.showMessageDialog(parent,
            "Settings functionality will be implemented in a future version.\n" +
            "This will allow you to configure application preferences.",
            "Settings",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showSearchResults(Component parent, List<ContactDTO> results, String searchTerm) {
        StringBuilder message = new StringBuilder();
        message.append("Found ").append(results.size()).append(" contact(s) matching '").append(searchTerm).append("':\n\n");
        
        for (ContactDTO contact : results) {
            message.append("• ").append(contact.getFirstName()).append(" ").append(contact.getLastName())
                   .append(" (").append(contact.getEmail()).append(")\n");
        }
        
        JTextArea textArea = new JTextArea(message.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(parent, scrollPane, "Search Results", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showSortResults(Component parent, List<ContactDTO> sorted, String sortBy) {
        StringBuilder message = new StringBuilder();
        message.append("Contacts sorted by ").append(sortBy).append(":\n\n");
        
        for (ContactDTO contact : sorted) {
            message.append("• ").append(contact.getFirstName()).append(" ").append(contact.getLastName())
                   .append(" (").append(contact.getEmail()).append(")\n");
        }
        
        JTextArea textArea = new JTextArea(message.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(parent, scrollPane, "Sort Results", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showFilterResults(Component parent, List<ContactDTO> filtered, String filterBy, String filterValue) {
        StringBuilder message = new StringBuilder();
        message.append("Contacts filtered by ").append(filterBy).append(" = '").append(filterValue).append("':\n\n");
        
        for (ContactDTO contact : filtered) {
            message.append("• ").append(contact.getFirstName()).append(" ").append(contact.getLastName())
                   .append(" (").append(contact.getEmail()).append(")\n");
        }
        
        JTextArea textArea = new JTextArea(message.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(parent, scrollPane, "Filter Results", JOptionPane.INFORMATION_MESSAGE);
    }
} 