package com.addressbook.services;

import com.addressbook.dao.ContactDAO;
import com.addressbook.model.ContactDTO;
import com.addressbook.model.ContactStatistics;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



public class DateService {
    private final ContactDAO contactDAO;
    
    public DateService() {
        this.contactDAO = new ContactDAO();
    }
    
    public void showContactStatistics(Component parent) {
        try {
            ContactStatistics stats = contactDAO.getContactStatistics();
            
            StringBuilder message = new StringBuilder();
            message.append("📊 Contact Statistics\n");
            message.append("===================\n\n");
            message.append("📈 Total Contacts: ").append(stats.getTotalContacts()).append("\n");
            message.append("📅 Added This Week: ").append(stats.getContactsThisWeek()).append("\n");
            message.append("✏️ Modified This Week: ").append(stats.getModifiedThisWeek()).append("\n\n");
            
            if (stats.getOldestContact() != null) {
                message.append("📅 Oldest Contact: ").append(stats.getOldestContact().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))).append("\n");
            }
            if (stats.getNewestContact() != null) {
                message.append("📅 Newest Contact: ").append(stats.getNewestContact().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))).append("\n");
            }
            
            JOptionPane.showMessageDialog(parent, message.toString(), "Contact Statistics", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, 
                "Error getting statistics: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void showRecentlyModifiedContacts(Component parent) {
        String[] options = {"Last 7 days", "Last 30 days", "Last 90 days"};
        String selected = (String) JOptionPane.showInputDialog(parent,
            "Show contacts modified in:",
            "Recently Modified Contacts",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
            
        if (selected != null) {
            int days = switch (selected) {
                case "Last 7 days" -> 7;
                case "Last 30 days" -> 30;
                case "Last 90 days" -> 90;
                default -> 7;
            };
            
            try {
                List<ContactDTO> contacts = contactDAO.getRecentlyModifiedContacts(days);
                
                if (contacts.isEmpty()) {
                    JOptionPane.showMessageDialog(parent,
                        "No contacts were modified in the last " + days + " days.",
                        "Recently Modified Contacts",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showContactList(parent, contacts, "Recently Modified Contacts (Last " + days + " days)");
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, 
                    "Error getting recently modified contacts: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void showContactsByDateRange(Component parent) {
        // Create a simple date range dialog
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();
        JLabel startLabel = new JLabel("Start Date (YYYY-MM-DD):");
        JLabel endLabel = new JLabel("End Date (YYYY-MM-DD):");
        JLabel exampleLabel = new JLabel("Example: 2024-01-01");
        
        panel.add(startLabel);
        panel.add(startDateField);
        panel.add(endLabel);
        panel.add(endDateField);
        panel.add(new JLabel(""));
        panel.add(exampleLabel);
        
        int result = JOptionPane.showConfirmDialog(parent, panel, "Select Date Range", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
        if (result == JOptionPane.OK_OPTION) {
            try {
                LocalDateTime startDate = LocalDateTime.parse(startDateField.getText().trim() + "T00:00:00");
                LocalDateTime endDate = LocalDateTime.parse(endDateField.getText().trim() + "T23:59:59");
                
                List<ContactDTO> contacts = contactDAO.getContactsByDateRange(startDate, endDate);
                
                if (contacts.isEmpty()) {
                    JOptionPane.showMessageDialog(parent,
                        "No contacts were created between " + startDateField.getText() + " and " + endDateField.getText(),
                        "Contacts by Date Range",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showContactList(parent, contacts, "Contacts Created: " + startDateField.getText() + " to " + endDateField.getText());
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, 
                    "Error getting contacts by date range: " + e.getMessage() + "\n\nPlease use format: YYYY-MM-DD", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void showContactAgeAnalysis(Component parent) {
        try {
            List<ContactDTO> contacts = contactDAO.getAllContacts();
            
            if (contacts.isEmpty()) {
                JOptionPane.showMessageDialog(parent,
                    "No contacts to analyze.",
                    "Contact Age Analysis",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Analyze contact ages
            long totalAge = 0;
            long oldestAge = 0;
            long newestAge = Long.MAX_VALUE;
            int recentContacts = 0; // Less than 30 days
            int oldContacts = 0; // More than 1 year
            
            for (ContactDTO contact : contacts) {
                long age = contact.getAgeInDays();
                totalAge += age;
                
                if (age > oldestAge) oldestAge = age;
                if (age < newestAge) newestAge = age;
                
                if (age < 30) recentContacts++;
                if (age > 365) oldContacts++;
            }
            
            double averageAge = (double) totalAge / contacts.size();
            
            StringBuilder message = new StringBuilder();
            message.append("📊 Contact Age Analysis\n");
            message.append("=====================\n\n");
            message.append("📈 Total Contacts: ").append(contacts.size()).append("\n");
            message.append("📅 Average Age: ").append(String.format("%.1f", averageAge)).append(" days\n");
            message.append("📅 Oldest Contact: ").append(oldestAge).append(" days\n");
            message.append("📅 Newest Contact: ").append(newestAge).append(" days\n\n");
            message.append("🆕 Recent (< 30 days): ").append(recentContacts).append(" contacts\n");
            message.append("📚 Old (> 1 year): ").append(oldContacts).append(" contacts\n");
            
            JOptionPane.showMessageDialog(parent, message.toString(), "Contact Age Analysis", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, 
                "Error analyzing contact ages: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showContactList(Component parent, List<ContactDTO> contacts, String title) {
        StringBuilder message = new StringBuilder();
        message.append(title).append("\n");
        message.append("=".repeat(title.length())).append("\n\n");
        
        for (ContactDTO contact : contacts) {
            message.append("👤 ").append(contact.getFirstName()).append(" ").append(contact.getLastName()).append("\n");
            message.append("   📧 ").append(contact.getEmail()).append("\n");
            message.append("   📅 Created: ").append(contact.getCreatedAtDisplay()).append("\n");
            message.append("   ✏️ Modified: ").append(contact.getUpdatedAtDisplay()).append("\n");
            message.append("   📍 ").append(contact.getLocation()).append("\n");
            message.append("   📞 ").append(contact.getPhone()).append("\n");
            message.append("   ").append("-".repeat(40)).append("\n");
        }
        
        JTextArea textArea = new JTextArea(message.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(parent, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }
} 