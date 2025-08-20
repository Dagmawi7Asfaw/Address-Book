package com.addressbook.logic;

import com.addressbook.utils.ThemeManager;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.regex.Pattern;

/**
 * Handles the contact table UI components and search functionality
 */
public class ContactTablePanel extends JPanel {
    // Professional color scheme
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color TABLE_HEADER_BG = new Color(52, 73, 94);
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    private static final Color BORDER_COLOR = new Color(229, 231, 235);
    
    // Professional fonts
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TABLE_HEADER_FONT = new Font("Segoe UI", Font.BOLD, 13);
    
    // Table components
    private JTable contactTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField searchText;
    
    // Callback interface for table actions
    public interface TableActionListener {
        void onContactSelected(int cid, String firstName, String lastName, String location, String phone, String email);
    }
    
    private TableActionListener actionListener;
    
    public ContactTablePanel(TableActionListener actionListener) {
        this.actionListener = actionListener;
        initializePanel();
        setupSearch();
    }
    
    private void initializePanel() {
        setLayout(new BorderLayout());
        setBackground(CARD_BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Create table
        createTable();
        
        // Create search panel
        JPanel searchPanel = createSearchPanel();
        
        // Create table scroll pane
        JScrollPane tableScrollPane = new JScrollPane(contactTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tableScrollPane.getViewport().setBackground(CARD_BACKGROUND);
        
        add(searchPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }
    
    private void createTable() {
        // Create table model
        tableModel = new DefaultTableModel(
            new Object[]{"CID", "First Name", "Last Name", "Location", "Phone", "Email"}, 0);
        contactTable = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        contactTable.setRowSorter(sorter);
        
        // Apply FlatLaf table styling
        ThemeManager.styleTable(contactTable);
        
        // Set header styling
        JTableHeader header = contactTable.getTableHeader();
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setFont(TABLE_HEADER_FONT);
        header.setReorderingAllowed(false);
        
        // Hide the CID column
        TableColumn cidColumn = contactTable.getColumnModel().getColumn(0);
        cidColumn.setMinWidth(0);
        cidColumn.setMaxWidth(0);
        cidColumn.setPreferredWidth(0);
        
        // Set column widths
        int columnCount = contactTable.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = contactTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(180);
        }
        
        // Add selection listener
        contactTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && contactTable.getSelectedRow() != -1) {
                int selectedRow = contactTable.getSelectedRow();
                int cid = Integer.parseInt(contactTable.getValueAt(selectedRow, 0).toString());
                String firstName = contactTable.getValueAt(selectedRow, 1).toString();
                String lastName = contactTable.getValueAt(selectedRow, 2).toString();
                String location = contactTable.getValueAt(selectedRow, 3).toString();
                String phone = contactTable.getValueAt(selectedRow, 4).toString();
                String email = contactTable.getValueAt(selectedRow, 5).toString();
                
                actionListener.onContactSelected(cid, firstName, lastName, location, phone, email);
            }
        });
    }
    
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchPanel.setBackground(CARD_BACKGROUND);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Search label
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(LABEL_FONT);
        searchLabel.setForeground(TEXT_PRIMARY);
        
        // Search text field with modern styling
        searchText = new JTextField(25);
        searchText.setFont(INPUT_FONT);
        searchText.setPreferredSize(new Dimension(300, 36));
        searchText.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        searchText.setBackground(CARD_BACKGROUND);
        searchText.setForeground(TEXT_PRIMARY);
        
        // Search icon (placeholder)
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        searchIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        
        searchPanel.add(searchIcon);
        searchPanel.add(searchLabel);
        searchPanel.add(searchText);
        
        return searchPanel;
    }
    
    private void setupSearch() {
        searchText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filter(); }
        });
    }
    
    private void filter() {
        String searchQuery = searchText.getText();
        if (searchQuery.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            javax.swing.RowFilter<DefaultTableModel, Object> filter = 
                javax.swing.RowFilter.regexFilter("(?i)" + Pattern.quote(searchQuery), 1, 2, 3, 4, 5);
            sorter.setRowFilter(filter);
        }
    }
    
    // Public methods for external access
    public void loadContacts(java.util.List<com.addressbook.model.ContactDTO> contacts) {
        tableModel.setRowCount(0);
        for (com.addressbook.model.ContactDTO contact : contacts) {
            tableModel.addRow(new Object[]{
                contact.getCid(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getLocation(),
                contact.getPhone(),
                contact.getEmail()
            });
        }
    }
    
    public void addContact(com.addressbook.model.ContactDTO contact) {
        tableModel.addRow(new Object[]{
            contact.getCid(),
            contact.getFirstName(),
            contact.getLastName(),
            contact.getLocation(),
            contact.getPhone(),
            contact.getEmail()
        });
    }
    
    public void refreshTable() {
        tableModel.fireTableDataChanged();
    }
    
    public int[] getSelectedRows() {
        return contactTable.getSelectedRows();
    }
    
    public int getSelectedCid() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow != -1) {
            return Integer.parseInt(contactTable.getValueAt(selectedRow, 0).toString());
        }
        return -1;
    }
    
    public boolean hasSelection() {
        return contactTable.getSelectedRow() != -1;
    }
    
    public void clearSelection() {
        contactTable.clearSelection();
    }
    
    // Method to access the table for external operations (used by Dashboard)
    public JTable getContactTable() {
        return contactTable;
    }
} 