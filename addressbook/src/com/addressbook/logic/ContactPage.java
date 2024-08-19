package com.addressbook.logic;

import com.addressbook.dao.ContactDAO;
import com.addressbook.model.ContactDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactPage extends JPanel {
    private JTextField firstNameText, lastNameText, locationText, phoneText, emailText, searchText;
    public JTable contactTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private int selectedCid = -1;
    private final ContactDAO contactDAO;

    // Constants for font and color
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Color DARK_BLUE = new Color(0, 0, 128);
    private static final Color BUTTON_ADD_COLOR = new Color(0, 128, 0); // Green
    private static final Color BUTTON_EDIT_COLOR = new Color(0, 0, 255); // Blue
    private static final Color BUTTON_DELETE_COLOR = new Color(255, 0, 0); // Red
    private static final Color TABLE_BACKGROUND_COLOR = new Color(210, 210, 210); // Light gray
    private static final Color TABLE_GRID_COLOR = new Color(150, 0, 10); // Light gray
    private static final Color TABLE_HEADER_COLOR = new Color(0, 102, 204); // Dark blue for header
    private static final Color FORM_BACKGROUND_COLOR = new Color(210, 210, 210); // Light gray for form

    public ContactPage() {
        this.contactDAO = new ContactDAO(); // Initialize ContactDAO
        setLayout(new BorderLayout());
        setBackground(FORM_BACKGROUND_COLOR); // Light gray background

        JPanel formPanel = createFormPanel();
        JPanel tablePanel = createTablePanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, formPanel);
        splitPane.setDividerLocation(1300);
        splitPane.setResizeWeight(0.75);
        add(splitPane, BorderLayout.CENTER);
        loadContacts();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(null); // Use null layout for manual positioning
        panel.setBackground(FORM_BACKGROUND_COLOR); // Set form background color

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameText = new JTextField(20);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameText = new JTextField(20);
        JLabel locationLabel = new JLabel("Location:");
        locationText = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone:");
        phoneText = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailText = new JTextField(20);

        configureLabel(firstNameLabel);
        configureLabel(lastNameLabel);
        configureLabel(locationLabel);
        configureLabel(phoneLabel);
        configureLabel(emailLabel);

        // Position labels and text fields using setBounds
        firstNameLabel.setBounds(20, 40, 100, 25);
        firstNameText.setBounds(130, 40, 200, 25);
        lastNameLabel.setBounds(20, 80, 100, 25);
        lastNameText.setBounds(130, 80, 200, 25);
        locationLabel.setBounds(20, 120, 100, 25);
        locationText.setBounds(130, 120, 200, 25);
        phoneLabel.setBounds(20, 160, 100, 25);
        phoneText.setBounds(130, 160, 200, 25);
        emailLabel.setBounds(20, 200, 100, 25);
        emailText.setBounds(130, 200, 200, 25);

        // Add FocusListeners for phone and email fields
        addFocusListeners();

        // Create buttons with custom styles
        JButton addButton = createButton("Add", BUTTON_ADD_COLOR);
        JButton editButton = createButton("Edit", BUTTON_EDIT_COLOR);
        JButton deleteButton = createButton("Delete", BUTTON_DELETE_COLOR);

        addButton.addActionListener(e -> handleAddContact());
        editButton.addActionListener(e -> handleEditContact());
        deleteButton.addActionListener(e -> handleDeleteContact());

        // Create a panel for buttons to align them in a single row
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.setBackground(FORM_BACKGROUND_COLOR); // Match the form background
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Gap after Add button
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Gap after Edit button
        buttonPanel.add(deleteButton);

        buttonPanel.setBounds(20, 250, 310, 30); // Position button panel

        panel.add(firstNameLabel);
        panel.add(firstNameText);
        panel.add(lastNameLabel);
        panel.add(lastNameText);
        panel.add(locationLabel);
        panel.add(locationText);
        panel.add(phoneLabel);
        panel.add(phoneText);
        panel.add(emailLabel);
        panel.add(emailText);
        panel.add(buttonPanel);

        panel.setPreferredSize(new Dimension(500, getHeight()));
        return panel;
    }

    private JPanel createTablePanel() {
        tableModel = new DefaultTableModel(new Object[]{"CID", "First Name", "Last Name", "Location", "Phone", "Email"}, 0);
        contactTable = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        contactTable.setRowSorter(sorter);
        contactTable.setFont(new Font("Arial", Font.PLAIN, 17));
        contactTable.setRowHeight(30);
        contactTable.setBackground(TABLE_BACKGROUND_COLOR); // White background for the table
        contactTable.setGridColor(TABLE_GRID_COLOR); // Light gray grid color

        // Set header colors
        JTableHeader header = contactTable.getTableHeader();
        header.setBackground(TABLE_HEADER_COLOR); // Dark blue header background
        header.setForeground(Color.WHITE); // White text for header
        header.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font for header

        // Hide the CID column
        TableColumn cidColumn = contactTable.getColumnModel().getColumn(0);
        cidColumn.setMinWidth(0);
        cidColumn.setMaxWidth(0);
        cidColumn.setPreferredWidth(0);

        int columnCount = contactTable.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = contactTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(190);
        }

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

        // or we canuse Anonymous inner class,
        /*
                contactTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && contactTable.getSelectedRow() != -1) {
                    selectedCid = Integer.parseInt(contactTable.getValueAt(contactTable.getSelectedRow(), 0).toString());
                    firstNameText.setText(contactTable.getValueAt(contactTable.getSelectedRow(), 1).toString());
                    lastNameText.setText(contactTable.getValueAt(contactTable.getSelectedRow(), 2).toString());
                    locationText.setText(contactTable.getValueAt(contactTable.getSelectedRow(), 3).toString());
                    phoneText.setText(contactTable.getValueAt(contactTable.getSelectedRow(), 4).toString());
                    emailText.setText(contactTable.getValueAt(contactTable.getSelectedRow(), 5).toString());
                }
            }
        });
         */

        // Create search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(FORM_BACKGROUND_COLOR); // Light gray background for search panel
        JLabel searchLabel = new JLabel("Search:");
        searchText = new JTextField(20);
        searchPanel.add(searchLabel);
        searchPanel.add(searchText);

        searchText.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(contactTable);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(1400, getHeight() * 2));

        return panel;
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

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(LABEL_FONT);
        button.setBackground(color);
        button.setForeground(FORM_BACKGROUND_COLOR);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(80, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return button;
    }


    private void configureLabel(JLabel label) {
        label.setFont(LABEL_FONT);
        label.setForeground(DARK_BLUE);
    }

    private void addFocusListeners() {
        // Add FocusListeners for phone and email fields
        phoneText.addFocusListener(new FocusAdapter() {
            private boolean isValid = false;

            @Override
            public void focusGained(FocusEvent e) {
                isValid = isValidPhone(phoneText.getText());
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!isValid && !isValidPhone(phoneText.getText())) {
                    showMessage("Invalid phone number. Please use E.164 format (e.g., +15551234567).", "Error", JOptionPane.ERROR_MESSAGE);
                    phoneText.requestFocus(); // Set focus back to the phone field
                }
            }
        });

        emailText.addFocusListener(new FocusAdapter() {
            private boolean isValid = false;

            @Override
            public void focusGained(FocusEvent e) {
                isValid = isValidEmail(emailText.getText());
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!isValid && !isValidEmail(emailText.getText())) {
                    showMessage("Invalid email address. Please use example@gmail.com format.", "Error", JOptionPane.ERROR_MESSAGE);
                    emailText.requestFocus(); // Set focus back to the email field
                }
            }
        });
    }

    private boolean isValidPhone(String phone) {
        // Validate phone number (E.164 format)
        String regex = "^\\+[1-9]\\d{9,14}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private boolean isValidEmail(String email) {
        // Validate email address
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void loadContacts() {
        // Load contacts from the database
        List<ContactDTO> contacts = contactDAO.getAllContacts();
        tableModel.setRowCount(0); // Clear existing rows
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
        selectedCid = -1; // Reset selectedCid
    }

    private void handleAddContact() {
        if (validateFields()) {
            return;
        }

        // Check for duplicate contacts before adding
        if (isDuplicateContact()) {
            showMessage("Contact already exists! Duplicate contact is not allowed.", "Duplicate Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ContactDTO contact = createContactFromFields();
        int cid = contactDAO.addContact(contact); // Get the generated CID
        contact.setCid(cid); // Set CID to the generated value
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
                return true; // Duplicate found
            }
        }

        return false; // No duplicates found
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
        loadContacts(); // Refresh the table to reflect the changes
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
            return true;
        }
        if (!isValidEmail(emailText.getText())) {
            showMessage("Invalid email address. Please use example@gmail.com format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
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

    private boolean areFieldsEmpty() {
        return firstNameText.getText().trim().isEmpty() || lastNameText.getText().trim().isEmpty() ||
                locationText.getText().trim().isEmpty() || phoneText.getText().trim().isEmpty() ||
                emailText.getText().trim().isEmpty();
    }
}