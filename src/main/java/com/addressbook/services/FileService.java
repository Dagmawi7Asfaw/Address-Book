package com.addressbook.services;

import com.addressbook.dao.ContactDAO;
import com.addressbook.model.ContactDTO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.List;

public class FileService {
    private final ContactDAO contactDAO;
    
    public FileService() {
        this.contactDAO = new ContactDAO();
    }
    
    public void createNewContact(Component parent) {
        // This will be handled by the ContactService
        JOptionPane.showMessageDialog(parent, 
            "Use the 'Add Contact' button in the Contact panel to create a new contact.", 
            "New Contact", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void importContacts(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Import Contacts");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files (*.csv)", "csv"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                importFromFile(selectedFile);
                JOptionPane.showMessageDialog(parent, 
                    "Contacts imported successfully from " + selectedFile.getName(), 
                    "Import Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, 
                    "Error importing contacts: " + e.getMessage(), 
                    "Import Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void exportContacts(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Contacts");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files (*.csv)", "csv"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                exportToFile(selectedFile);
                JOptionPane.showMessageDialog(parent, 
                    "Contacts exported successfully to " + selectedFile.getName(), 
                    "Export Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, 
                    "Error exporting contacts: " + e.getMessage(), 
                    "Export Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void printContacts(Component parent) {
        List<ContactDTO> contacts = contactDAO.getAllContacts();
        if (contacts.isEmpty()) {
            JOptionPane.showMessageDialog(parent, 
                "No contacts to print.", 
                "Print", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Create a simple text representation for printing
        StringBuilder printContent = new StringBuilder();
        printContent.append("Address Book - Contact List\n");
        printContent.append("==========================\n\n");
        
        for (ContactDTO contact : contacts) {
            printContent.append("Name: ").append(contact.getFirstName()).append(" ").append(contact.getLastName()).append("\n");
            printContent.append("Location: ").append(contact.getLocation()).append("\n");
            printContent.append("Phone: ").append(contact.getPhone()).append("\n");
            printContent.append("Email: ").append(contact.getEmail()).append("\n");
            printContent.append("------------------------\n");
        }
        
        // Show print preview
        JTextArea textArea = new JTextArea(printContent.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        int option = JOptionPane.showConfirmDialog(parent, 
            scrollPane, 
            "Print Preview", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.INFORMATION_MESSAGE);
            
        if (option == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(parent, 
                "Print functionality would send this to the printer.\n" +
                "For now, the content is displayed above.", 
                "Print", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void importFromFile(File file) throws IOException {
        List<ContactDTO> contacts = contactDAO.getAllContacts();
        int importedCount = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Skip header if it exists
            line = reader.readLine();
            if (line != null && line.toLowerCase().contains("firstname")) {
                line = reader.readLine(); // Skip header
            }
            
            while (line != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    ContactDTO contact = new ContactDTO();
                    contact.setFirstName(parts[0].trim());
                    contact.setLastName(parts[1].trim());
                    contact.setLocation(parts[2].trim());
                    contact.setPhone(parts[3].trim());
                    if (parts.length > 4) {
                        contact.setEmail(parts[4].trim());
                    }
                    
                    contactDAO.addContact(contact);
                    importedCount++;
                }
                line = reader.readLine();
            }
        }
        
        System.out.println("Imported " + importedCount + " contacts from " + file.getName());
    }
    
    private void exportToFile(File file) throws IOException {
        List<ContactDTO> contacts = contactDAO.getAllContacts();
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            // Write header
            writer.println("FirstName,LastName,Location,Phone,Email");
            
            // Write contacts
            for (ContactDTO contact : contacts) {
                writer.printf("%s,%s,%s,%s,%s%n",
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getLocation(),
                    contact.getPhone(),
                    contact.getEmail());
            }
        }
        
        System.out.println("Exported " + contacts.size() + " contacts to " + file.getName());
    }
}