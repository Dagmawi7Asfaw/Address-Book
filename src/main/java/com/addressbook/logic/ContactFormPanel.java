package com.addressbook.logic;

import com.addressbook.utils.ThemeManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.regex.Pattern;

/**
 * Handles the contact form UI components and validation
 */
public class ContactFormPanel extends JPanel {
    // Professional color scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color INFO_COLOR = new Color(52, 152, 219);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    
    // Professional fonts
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    
    // Form fields
    private JTextField firstNameText, lastNameText, locationText, phoneText, emailText;
    
    // Validation patterns
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+[1-9]\\d{11,14}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    
    // Callback interface for form actions
    public interface FormActionListener {
        void onAddContact();
        void onUpdateContact();
        void onDeleteContact();
        void onClearFields();
    }
    
    private FormActionListener actionListener;
    
    public ContactFormPanel(FormActionListener actionListener) {
        this.actionListener = actionListener;
        initializePanel();
        setupValidation();
    }
    
    private void initializePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(CARD_BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Form title
        JLabel formTitle = new JLabel("Contact Information");
        formTitle.setFont(TITLE_FONT);
        formTitle.setForeground(PRIMARY_COLOR);
        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        formTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(formTitle);
        
        // Input fields
        add(createInputField("First Name:", firstNameText = new JTextField()));
        add(Box.createVerticalStrut(20));
        add(createInputField("Last Name:", lastNameText = new JTextField()));
        add(Box.createVerticalStrut(20));
        add(createInputField("Location:", locationText = new JTextField()));
        add(Box.createVerticalStrut(20));
        add(createInputField("Phone:", phoneText = new JTextField()));
        add(Box.createVerticalStrut(20));
        add(createInputField("Email:", emailText = new JTextField()));
        add(Box.createVerticalStrut(30));
        
        // Button panel
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(buttonPanel);
    }
    
    private JPanel createInputField(String labelText, JTextField textField) {
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(CARD_BACKGROUND);
        fieldPanel.setMaximumSize(new Dimension(350, 60));
        fieldPanel.setPreferredSize(new Dimension(350, 60));
        
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_PRIMARY);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        
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
        
        JButton addButton = createButton("Add", SUCCESS_COLOR, e -> actionListener.onAddContact());
        JButton updateButton = createButton("Update", INFO_COLOR, e -> actionListener.onUpdateContact());
        JButton deleteButton = createButton("Delete", DANGER_COLOR, e -> actionListener.onDeleteContact());
        JButton clearButton = createButton("Clear", SECONDARY_COLOR, e -> actionListener.onClearFields());
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        return buttonPanel;
    }
    
    private JButton createButton(String text, Color color, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        ThemeManager.styleButton(button);
        button.setPreferredSize(new Dimension(100, 36));
        button.addActionListener(listener);
        return button;
    }
    
    private void setupValidation() {
        phoneText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validatePhoneField();
            }
        });
        
        emailText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validateEmailField();
            }
        });
    }
    
    private void validatePhoneField() {
        String text = phoneText.getText().trim();
        if (!text.isEmpty() && !isValidPhone(text)) {
            JOptionPane.showMessageDialog(this, 
                "Invalid phone number. Please use E.164 format (e.g., +15551234567).", 
                "Validation Warning", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void validateEmailField() {
        String text = emailText.getText().trim();
        if (!text.isEmpty() && !isValidEmail(text)) {
            JOptionPane.showMessageDialog(this, 
                "Invalid email address. Please use example@gmail.com format.", 
                "Validation Warning", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Public methods for external access
    public boolean isValidPhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }
    
    public boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    public boolean areFieldsEmpty() {
        return firstNameText.getText().trim().isEmpty() || 
               lastNameText.getText().trim().isEmpty() ||
               locationText.getText().trim().isEmpty() || 
               phoneText.getText().trim().isEmpty() ||
               emailText.getText().trim().isEmpty();
    }
    
    public void setContactData(String firstName, String lastName, String location, String phone, String email) {
        firstNameText.setText(firstName);
        lastNameText.setText(lastName);
        locationText.setText(location);
        phoneText.setText(phone);
        emailText.setText(email);
    }
    
    public void clearFields() {
        firstNameText.setText("");
        lastNameText.setText("");
        locationText.setText("");
        phoneText.setText("");
        emailText.setText("");
    }
    
    public String getFirstName() { return firstNameText.getText().trim(); }
    public String getLastName() { return lastNameText.getText().trim(); }
    public String getLocationText() { return locationText.getText().trim(); }
    public String getPhone() { return phoneText.getText().trim(); }
    public String getEmail() { return emailText.getText().trim(); }
} 
