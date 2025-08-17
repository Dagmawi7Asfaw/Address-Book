package com.addressbook.logic;

import com.addressbook.dao.ContactDAO;
import com.addressbook.model.ContactDTO;
import com.addressbook.utils.ThemeManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Abstract class for common UI elements and functionality
abstract class AbstractContactPanel extends JPanel {
    // Professional color scheme
    protected static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Professional blue
    protected static final Color SECONDARY_COLOR = new Color(52, 73, 94); // Dark slate
    protected static final Color SUCCESS_COLOR = new Color(46, 204, 113); // Success green
    protected static final Color WARNING_COLOR = new Color(241, 196, 15); // Warning yellow
    protected static final Color DANGER_COLOR = new Color(231, 76, 60); // Danger red
    protected static final Color INFO_COLOR = new Color(52, 152, 219); // Info blue
    protected static final Color BACKGROUND_COLOR = new Color(248, 249, 250); // Light gray
    protected static final Color CARD_BACKGROUND = Color.WHITE;
    protected static final Color BORDER_COLOR = new Color(229, 231, 235);
    protected static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    protected static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    protected static final Color TABLE_HEADER_BG = new Color(52, 73, 94);
    protected static final Color TABLE_ROW_ALT = new Color(248, 249, 250);
    
    // Professional fonts
    protected static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 13);
    protected static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    protected static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 13);
    protected static final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    protected static final Font TABLE_HEADER_FONT = new Font("Segoe UI", Font.BOLD, 13);
    
    public JTable contactTable;
    protected final ContactDAO contactDAO;
    protected JTextField firstNameText, lastNameText, locationText, phoneText, emailText, searchText;
    protected DefaultTableModel tableModel;
    protected TableRowSorter<DefaultTableModel> sorter;
    protected int selectedCid = -1;

    public AbstractContactPanel() {
        this.contactDAO = new ContactDAO();
    }

    // Common methods for both subclasses
    protected void configureLabel(JLabel label) {
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_PRIMARY);
    }

    protected JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        ThemeManager.styleButton(button);
        button.setPreferredSize(new Dimension(100, 36));
        
        return button;
    }

    protected void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}

// Concrete class extending the abstract class
public class ContactPage extends AbstractContactPanel {

    public ContactPage() {
        super();
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Create main content with professional layout
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(BACKGROUND_COLOR);
        mainContent.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Create table panel (left side)
        JPanel tablePanel = createTablePanel();
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        tablePanel.setBackground(CARD_BACKGROUND);

        // Create form panel (right side)
        JPanel formPanel = createFormPanel();
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
        
        loadContacts();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Form title
        JLabel formTitle = new JLabel("Contact Information");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        formTitle.setForeground(PRIMARY_COLOR);
        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        formTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(formTitle);

        // Input fields with modern styling
        panel.add(createInputField("First Name:", firstNameText = new JTextField()));
        panel.add(Box.createVerticalStrut(20));
        panel.add(createInputField("Last Name:", lastNameText = new JTextField()));
        panel.add(Box.createVerticalStrut(20));
        panel.add(createInputField("Location:", locationText = new JTextField()));
        panel.add(Box.createVerticalStrut(20));
        panel.add(createInputField("Phone:", phoneText = new JTextField()));
        panel.add(Box.createVerticalStrut(20));
        panel.add(createInputField("Email:", emailText = new JTextField()));
        panel.add(Box.createVerticalStrut(30));

        // Add FocusListeners for phone and email fields
        addFocusListeners();

        // Create button panel with professional layout
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createInputField(String labelText, JTextField textField) {
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(CARD_BACKGROUND);
        fieldPanel.setMaximumSize(new Dimension(350, 60));
        fieldPanel.setPreferredSize(new Dimension(350, 60));

        // Label
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_PRIMARY);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        // Apply FlatLaf text field styling
        ThemeManager.styleTextField(textField);
        textField.setPreferredSize(new Dimension(350, 40));
        textField.setMaximumSize(new Dimension(350, 40));

        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(textField, BorderLayout.CENTER);

        return fieldPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(CARD_BACKGROUND);
        buttonPanel.setMaximumSize(new Dimension(350, 90));
        buttonPanel.setPreferredSize(new Dimension(350, 90));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Create buttons with professional styling
        JButton addButton = createButton("Add", SUCCESS_COLOR);
        JButton updateButton = createButton("Update", INFO_COLOR);
        JButton deleteButton = createButton("Delete", DANGER_COLOR);
        JButton clearButton = createButton("Clear", SECONDARY_COLOR);

        // Add action listeners
        addButton.addActionListener(e -> handleAddContact());
        updateButton.addActionListener(e -> handleEditContact());
        deleteButton.addActionListener(e -> handleDeleteContact());
        clearButton.addActionListener(e -> clearFields());

        // Add buttons to panel in 2x2 grid
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        return buttonPanel;
    }

    private JPanel createTablePanel() {
        // Create table model
        tableModel = new DefaultTableModel(new Object[]{"CID", "First Name", "Last Name", "Location", "Phone", "Email"}, 0);
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
                selectedCid = Integer.parseInt(contactTable.getValueAt(contactTable.getSelectedRow(), 0).toString());
                firstNameText.setText(contactTable.getValueAt(contactTable.getSelectedRow(), 1).toString());
                lastNameText.setText(contactTable.getValueAt(contactTable.getSelectedRow(), 2).toString());
                locationText.setText(contactTable.getValueAt(contactTable.getSelectedRow(), 3).toString());
                phoneText.setText(contactTable.getValueAt(contactTable.getSelectedRow(), 4).toString());
                emailText.setText(contactTable.getValueAt(contactTable.getSelectedRow(), 5).toString());
            }
        });

        // Create search panel with professional styling
        JPanel searchPanel = createSearchPanel();

        // Create table scroll pane
        JScrollPane tableScrollPane = new JScrollPane(contactTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tableScrollPane.getViewport().setBackground(CARD_BACKGROUND);

        // Create main table panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
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

        // Add search functionality
        searchText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filter(); }
        });

        return searchPanel;
    }

    private void filter() {
        String searchQuery = searchText.getText();
        if (searchQuery.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter("(?i)" + Pattern.quote(searchQuery), 1, 2, 3, 4, 5);
            sorter.setRowFilter(filter);
        }
    }

    private void addFocusListeners() {
        // Phone field validation - only validate when user finishes typing
        phoneText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // Only validate if user actually entered something
                String text = phoneText.getText().trim();
                if (!text.isEmpty() && !isValidPhone(text)) {
                    // Show warning but don't force focus back
                    showMessage("Invalid phone number. Please use E.164 format (e.g., +15551234567).", "Validation Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Email field validation - only validate when user finishes typing
        emailText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // Only validate if user actually entered something
                String text = emailText.getText().trim();
                if (!text.isEmpty() && !isValidEmail(text)) {
                    // Show warning but don't force focus back
                    showMessage("Invalid email address. Please use example@gmail.com format.", "Validation Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private boolean isValidPhone(String phone) {
        String regex = "^\\+[1-9]\\d{11,14}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void loadContacts() {
        List<ContactDTO> contacts = contactDAO.getAllContacts();
        tableModel.setRowCount(0);
        for (ContactDTO contact : contacts) {
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

    private void clearFields() {
        firstNameText.setText("");
        lastNameText.setText("");
        locationText.setText("");
        phoneText.setText("");
        emailText.setText("");
        selectedCid = -1;
    }

    private void handleAddContact() {
        if (validateFields()) {
            return;
        }

        if (isDuplicateContact()) {
            showMessage("Contact already exists! Duplicate contact is not allowed.", "Duplicate Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ContactDTO contact = createContactFromFields();
        int cid = contactDAO.addContact(contact);
        contact.setCid(cid);
        ((DefaultTableModel) contactTable.getModel()).addRow(new Object[]{
                contact.getCid(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getLocation(),
                contact.getPhone(),
                contact.getEmail()
        });
        clearFields();
        showMessage("Contact added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean isDuplicateContact() {
        String firstName = firstNameText.getText().trim();
        String lastName = lastNameText.getText().trim();
        String location = locationText.getText().trim();
        String phone = phoneText.getText().trim();
        String email = emailText.getText().trim();

        for (int i = 0; i < contactTable.getRowCount(); i++) {
            String existingFirstName = contactTable.getValueAt(i, 1).toString().trim();
            String existingLastName = contactTable.getValueAt(i, 2).toString().trim();
            String existingLocation = contactTable.getValueAt(i, 3).toString().trim();
            String existingPhone = contactTable.getValueAt(i, 4).toString().trim();
            String existingEmail = contactTable.getValueAt(i, 5).toString().trim();

            if (firstName.equals(existingFirstName) && lastName.equals(existingLastName) &&
                    location.equals(existingLocation) && phone.equals(existingPhone) && email.equals(existingEmail)) {
                return true;
            }
        }

        return false;
    }

    private void handleEditContact() {
        if (selectedCid == -1) {
            showMessage("Please select a contact to edit.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (validateFields()) {
            return;
        }
        ContactDTO contact = createContactFromFields();
        contact.setCid(selectedCid);
        contactDAO.updateContact(contact);
        loadContacts();
        clearFields();
        showMessage("Contact updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean validateFields() {
        if (areFieldsEmpty()) {
            showMessage("All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if (!isValidPhone(phoneText.getText())) {
            showMessage("Invalid phone number. Please use E.164 format (e.g., +15551234567).", "Input Error", JOptionPane.ERROR_MESSAGE);
            phoneText.requestFocus();
            return true;
        }
        if (!isValidEmail(emailText.getText())) {
            showMessage("Invalid email address. Please use example@gmail.com format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            emailText.requestFocus();
            return true;
        }
        return false;
    }

    private boolean areFieldsEmpty() {
        return firstNameText.getText().trim().isEmpty() || lastNameText.getText().trim().isEmpty() ||
                locationText.getText().trim().isEmpty() || phoneText.getText().trim().isEmpty() ||
                emailText.getText().trim().isEmpty();
    }

    private ContactDTO createContactFromFields() {
        ContactDTO contact = new ContactDTO();
        contact.setFirstName(firstNameText.getText());
        contact.setLastName(lastNameText.getText());
        contact.setLocation(locationText.getText());
        contact.setPhone(phoneText.getText());
        contact.setEmail(emailText.getText());
        return contact;
    }

    private void handleDeleteContact() {
        int[] selectedRows = contactTable.getSelectedRows();
        if (selectedRows.length > 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected contacts?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                for (int rowIndex : selectedRows) {
                    int cid = (int) contactTable.getValueAt(rowIndex, 0);
                    contactDAO.deleteContact(cid);
                }
                loadContacts();
                clearFields();
                showMessage("Selected contacts deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            showMessage("Please select at least one contact to delete.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}