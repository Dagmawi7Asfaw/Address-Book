package com.addressbook.UI;

import com.addressbook.dao.ThemeDAO;
import com.addressbook.utils.ThemeUtils;
import com.addressbook.utils.Utils;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame {

    // Professional color scheme
    protected static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Professional blue
    protected static final Color SECONDARY_COLOR = new Color(52, 73, 94); // Dark slate
    protected static final Color SUCCESS_COLOR = new Color(46, 204, 113); // Success green
    protected static final Color BACKGROUND_COLOR = new Color(248, 249, 250); // Light gray
    protected static final Color CARD_BACKGROUND = Color.WHITE;
    protected static final Color BORDER_COLOR = new Color(229, 231, 235);
    protected static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    protected static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    protected static final Color INPUT_BORDER = new Color(207, 217, 222);
    protected static final Color INPUT_FOCUS = new Color(29, 161, 242);

    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }

    public LoginPage() {
        applySavedTheme();
        initComponents();
    }

    private void applySavedTheme() {
        ThemeDAO themeDAO = new ThemeDAO();
        String savedTheme = themeDAO.getSavedTheme(Utils.DEFAULT_USER_NAME);

        try {
            if (savedTheme == null || savedTheme.isEmpty()) {
                FlatLightLaf.setup();
                return;
            }
            switch (savedTheme) {
                case "FlatLightLaf":
                    ThemeUtils.applyTheme(ThemeUtils.Theme.FLAT_LIGHT);
                    break;
                case "FlatDarkLaf":
                    ThemeUtils.applyTheme(ThemeUtils.Theme.FLAT_DARK);
                    break;
                case "FlatMacLightLaf":
                    ThemeUtils.applyTheme(ThemeUtils.Theme.MAC_LIGHT);
                    break;
                case "FlatMacDarkLaf":
                    ThemeUtils.applyTheme(ThemeUtils.Theme.MAC_DARK);
                    break;
                default:
                    FlatLightLaf.setup();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FlatLightLaf.setup(); // Fallback to a default theme on error
        }
    }

    private void initComponents() {
        LoginPanel loginPanel = new LoginPanel();
        setContentPane(loginPanel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Address Book - Professional Login");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Start minimized
        setExtendedState(getExtendedState() | JFrame.ICONIFIED);
    }
}

class LoginPanel extends JPanel {
    // Professional color scheme for LoginPanel
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Professional blue
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94); // Dark slate
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113); // Success green
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250); // Light gray
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(229, 231, 235);
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    private static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    private static final Color INPUT_BORDER = new Color(207, 217, 222);
    private static final Color INPUT_FOCUS = new Color(29, 161, 242);
    
    private JTextField userNameTextField;
    private JPasswordField passWordField;
    private JCheckBox showPasswordCheckBox;

    public LoginPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Main content panel with professional layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(CARD_BACKGROUND);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        // Logo/Icon placeholder
        JLabel logoLabel = new JLabel("📚");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(logoLabel);

        // Title
        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(titleLabel);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Sign in to your Address Book account");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        mainPanel.add(subtitleLabel);

        // Input fields panel
        JPanel inputPanel = createInputPanel();
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(30));

        // Login button
        JButton loginButton = createLoginButton();
        loginButton.addActionListener(this::loginButtonActionPerformed);
        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalStrut(20));

        // Show password checkbox
        showPasswordCheckBox = createShowPasswordCheckBox();
        showPasswordCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        showPasswordCheckBox.addActionListener(this::togglePasswordVisibility);
        mainPanel.add(showPasswordCheckBox);

        // Footer text
        JLabel footerLabel = new JLabel("Professional Contact Management System");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(TEXT_SECONDARY);
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        mainPanel.add(footerLabel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BACKGROUND);
        panel.setMaximumSize(new Dimension(350, 200));

        // Username field
        panel.add(createInputField("Username", userNameTextField = new JTextField()));
        panel.add(Box.createVerticalStrut(20));

        // Password field
        panel.add(createInputField("Password", passWordField = new JPasswordField()));

        return panel;
    }

    private JPanel createInputField(String labelText, JTextField textField) {
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(CARD_BACKGROUND);
        fieldPanel.setMaximumSize(new Dimension(350, 70));
        fieldPanel.setPreferredSize(new Dimension(350, 70));

        // Label
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_PRIMARY);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        // Text field with modern styling
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(350, 44));
        textField.setMaximumSize(new Dimension(350, 44));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(INPUT_BORDER, 1),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        textField.setBackground(CARD_BACKGROUND);
        textField.setForeground(TEXT_PRIMARY);

        // Focus effect
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(INPUT_FOCUS, 2),
                    BorderFactory.createEmptyBorder(11, 15, 11, 15)
                ));
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(INPUT_BORDER, 1),
                    BorderFactory.createEmptyBorder(12, 16, 12, 16)
                ));
            }
        });

        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(textField, BorderLayout.CENTER);

        return fieldPanel;
    }

    private JButton createLoginButton() {
        JButton button = new JButton("Sign In");
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(350, 48));
        button.setMaximumSize(new Dimension(350, 48));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(PRIMARY_COLOR.brighter());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        
        return button;
    }

    private JCheckBox createShowPasswordCheckBox() {
        JCheckBox checkBox = new JCheckBox("Show password");
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        checkBox.setForeground(TEXT_SECONDARY);
        checkBox.setBackground(CARD_BACKGROUND);
        checkBox.setFocusPainted(false);
        return checkBox;
    }

    // Action listeners
    private void togglePasswordVisibility(ActionEvent evt) {
        if (showPasswordCheckBox.isSelected()) {
            passWordField.setEchoChar((char) 0); // Show password
        } else {
            passWordField.setEchoChar('*'); // Hide password
        }
    }

    private void loginButtonActionPerformed(ActionEvent evt) {
        String username = userNameTextField.getText();
        String password = new String(passWordField.getPassword());

        if ("admin".equals(username) && "root".equals(password)) {
            new Dashboard(username, "admin").setVisible(true);
            SwingUtilities.getWindowAncestor(this).dispose(); // Close the login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Authentication Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}