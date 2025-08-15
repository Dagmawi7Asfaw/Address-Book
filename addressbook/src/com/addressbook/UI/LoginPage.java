package com.addressbook.UI;

import com.addressbook.dao.ThemeDAO;
import com.addressbook.utils.ThemeUtils;
import com.addressbook.utils.Utils;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame {

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
        setTitle("Login");
        pack(); // Adjust the frame size to fit the components
        // Start maximized
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
}

abstract class BasePanel extends JPanel {
    // Method to create a stylized JLabel
    protected JLabel createLabel(String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        label.setForeground(new Color(15, 20, 25)); // Twitter dark text
        return label;
    }
}

class LoginPanel extends BasePanel {
    private JTextField userNameTextField;
    private JPasswordField passWordField;
    private JCheckBox showPasswordCheckBox;

    public LoginPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Main content panel with Twitter-like layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Twitter-style logo/icon (using a bird-like symbol)
        JLabel logoLabel = createTwitterLogo();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalStrut(40));

        // "Sign in to Address Book" title
        JLabel titleLabel = createLabel("Sign in to Address Book", 24);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(15, 20, 25)); // Twitter dark text
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40));

        // Input fields panel
        JPanel inputPanel = createInputPanel();
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Login button
        JButton loginButton = createTwitterLoginButton();
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(this::loginButtonActionPerformed);
        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalStrut(20));

        // Show password checkbox
        showPasswordCheckBox = createShowPasswordCheckBox();
        showPasswordCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        showPasswordCheckBox.addActionListener(this::togglePasswordVisibility);
        mainPanel.add(showPasswordCheckBox);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JLabel createTwitterLogo() {
        JLabel logoLabel = new JLabel("🐦");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setForeground(new Color(29, 161, 242)); // Twitter blue
        return logoLabel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(400, 200));
        panel.setPreferredSize(new Dimension(400, 200));

        // Username field
        JLabel usernameLabel = createLabel("Username", 14);
        usernameLabel.setForeground(new Color(83, 100, 113)); // Twitter gray text
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(8));

        userNameTextField = createTwitterTextField();
        panel.add(userNameTextField);
        panel.add(Box.createVerticalStrut(20));

        // Password field
        JLabel passwordLabel = createLabel("Password", 14);
        passwordLabel.setForeground(new Color(83, 100, 113)); // Twitter gray text
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(8));

        passWordField = createTwitterPasswordField();
        panel.add(passWordField);

        return panel;
    }

    private JTextField createTwitterTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(400, 50));
        field.setMaximumSize(new Dimension(400, 50));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(207, 217, 222), 1), // Twitter border
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(15, 20, 25));
        
        // Focus effect
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(29, 161, 242), 2), // Twitter blue
                    BorderFactory.createEmptyBorder(11, 15, 11, 15)
                ));
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(207, 217, 222), 1),
                    BorderFactory.createEmptyBorder(12, 16, 12, 16)
                ));
            }
        });
        
        return field;
    }

    private JPasswordField createTwitterPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(400, 50));
        field.setMaximumSize(new Dimension(400, 50));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(207, 217, 222), 1), // Twitter border
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(15, 20, 25));
        
        // Focus effect
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(29, 161, 242), 2), // Twitter blue
                    BorderFactory.createEmptyBorder(11, 15, 11, 15)
                ));
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(207, 217, 222), 1),
                    BorderFactory.createEmptyBorder(12, 16, 12, 16)
                ));
            }
        });
        
        return field;
    }

    private JButton createTwitterLoginButton() {
        JButton button = new JButton("Sign in");
        button.setPreferredSize(new Dimension(400, 50));
        button.setMaximumSize(new Dimension(400, 50));
        button.setMinimumSize(new Dimension(400, 50));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(29, 161, 242)); // Twitter blue
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(26, 145, 218)); // Darker Twitter blue
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(29, 161, 242)); // Twitter blue
            }
        });
        
        return button;
    }

    private JCheckBox createShowPasswordCheckBox() {
        JCheckBox checkBox = new JCheckBox("Show password");
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkBox.setForeground(new Color(83, 100, 113)); // Twitter gray text
        checkBox.setBackground(Color.WHITE);
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
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }
}