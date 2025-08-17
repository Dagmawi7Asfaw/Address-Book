package com.addressbook.UI;

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
        try {
            // Enable anti-aliasing for better text rendering
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        LoginPanel loginPanel = new LoginPanel();
        setContentPane(loginPanel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Address Book - Professional Login");
        setSize(500, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Start minimized
        setExtendedState(getExtendedState() | JFrame.ICONIFIED);
    }
}

class LoginPanel extends JPanel {
    
    private JTextField userNameTextField;
    private JPasswordField passWordField;
    private JCheckBox showPasswordCheckBox;

    public LoginPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Main content panel with Material Design layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor"), 1),
            BorderFactory.createEmptyBorder(50, 50, 50, 50)
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
        titleLabel.setForeground(UIManager.getColor("Label.foreground"));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(titleLabel);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Sign in to your Address Book account");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(UIManager.getColor("Label.foreground"));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(subtitleLabel);

        // Input fields panel
        JPanel inputPanel = createInputPanel();
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        // Login button
        JButton loginButton = createLoginButton();
        loginButton.addActionListener(this::loginButtonActionPerformed);
        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalStrut(10));

        // Test button to verify functionality
        JButton testButton = new JButton("🧪 Test Login (admin/root)");
        testButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        testButton.setPreferredSize(new Dimension(200, 32));
        testButton.setMaximumSize(new Dimension(200, 32));
        testButton.setBackground(new Color(255, 193, 7)); // Amber color
        testButton.setForeground(Color.BLACK);
        testButton.setBorder(BorderFactory.createEmptyBorder());
        testButton.setFocusPainted(false);
        testButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        testButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        testButton.addActionListener(e -> {
            userNameTextField.setText("admin");
            passWordField.setText("root");
            JOptionPane.showMessageDialog(this, "Test credentials loaded!\nUsername: admin\nPassword: root", "Test Mode", JOptionPane.INFORMATION_MESSAGE);
        });
        mainPanel.add(testButton);
        mainPanel.add(Box.createVerticalStrut(10));

        // Show password checkbox
        showPasswordCheckBox = createShowPasswordCheckBox();
        showPasswordCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        showPasswordCheckBox.addActionListener(this::togglePasswordVisibility);
        mainPanel.add(showPasswordCheckBox);
        mainPanel.add(Box.createVerticalStrut(10));

        // Footer text
        JLabel footerLabel = new JLabel("Material Design Contact Management System");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(UIManager.getColor("Label.foreground"));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        mainPanel.add(footerLabel);
        
        // Add more space before theme button
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Theme selector button
        JButton themeButton = createThemeButton();
        themeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(themeButton);
        
        // Add space after theme button
        mainPanel.add(Box.createVerticalStrut(15));

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UIManager.getColor("Panel.background"));
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
        fieldPanel.setBackground(UIManager.getColor("Panel.background"));
        fieldPanel.setMaximumSize(new Dimension(350, 70));
        fieldPanel.setPreferredSize(new Dimension(350, 70));

        // Label
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(UIManager.getColor("Label.foreground"));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        // Text field with modern styling
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(350, 44));
        textField.setMaximumSize(new Dimension(350, 44));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIManager.getColor("TextField.border"), 1),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        textField.setBackground(UIManager.getColor("TextField.background"));
        textField.setForeground(UIManager.getColor("Label.foreground"));

        // Focus effect
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UIManager.getColor("TextField.focusBorder"), 2),
                    BorderFactory.createEmptyBorder(11, 15, 11, 15)
                ));
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UIManager.getColor("TextField.border"), 1),
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
        
        // Use fallback colors if UIManager colors are not available
        final Color primaryColor = UIManager.getColor("Button.primary");
        final Color buttonColor = (primaryColor != null) ? primaryColor : new Color(41, 128, 185);
        
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(buttonColor.brighter());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(buttonColor);
            }
        });
        
        return button;
    }

    private JCheckBox createShowPasswordCheckBox() {
        JCheckBox checkBox = new JCheckBox("Show password");
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        checkBox.setForeground(UIManager.getColor("Label.foreground"));
        checkBox.setBackground(UIManager.getColor("Panel.background"));
        checkBox.setFocusPainted(false);
        return checkBox;
    }
    
    private JButton createThemeButton() {
        JButton button = new JButton("🎨 Change Theme");
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(200, 32));
        button.setMaximumSize(new Dimension(200, 32));
        
        // Use fallback colors if UIManager colors are not available
        Color secondaryColor = UIManager.getColor("Button.secondary");
        Color secondaryForeground = UIManager.getColor("Button.secondaryForeground");
        
        button.setBackground((secondaryColor != null) ? secondaryColor : new Color(108, 117, 125));
        button.setForeground((secondaryForeground != null) ? secondaryForeground : Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Theme selection action
        button.addActionListener(e -> showThemeSelector());
        
        return button;
    }
    
    private void showThemeSelector() {
        String[] themes = {"Default", "Material", "Professional"}; // Simple themes for now
        String selectedTheme = (String) JOptionPane.showInputDialog(
            this,
            "Choose a Material Design theme:",
            "Theme Selector",
            JOptionPane.QUESTION_MESSAGE,
            null,
            themes,
            themes[0]
        );
        
        if (selectedTheme != null) {
            // No theme application logic here, just a placeholder
            JOptionPane.showMessageDialog(this, "Theme selection not yet implemented.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
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

        // Debug information
        System.out.println("Login attempt - Username: " + username + ", Password length: " + password.length());

        if ("admin".equals(username) && "root".equals(password)) {
            System.out.println("Login successful! Opening Dashboard...");
            try {
                new Dashboard(username, "admin").setVisible(true);
                SwingUtilities.getWindowAncestor(this).dispose(); // Close the login window
            } catch (Exception e) {
                System.err.println("Error opening Dashboard: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Error opening Dashboard: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Login failed - Invalid credentials");
            JOptionPane.showMessageDialog(this, 
                "Invalid username or password.\n\nUse:\nUsername: admin\nPassword: root", 
                "Authentication Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}