package com.addressbook.UI;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame {

    private JTextField userNameTextField;
    private JPasswordField passWordField;
    private JCheckBox showPasswordCheckBox;

    public LoginPage() {
        initComponents();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup();
            new LoginPage().setVisible(true);
        });
    }

    private void initComponents() {
        // Create components
        JLabel userNameLabel = new JLabel("Username:");
        JLabel passWordLabel = new JLabel("Password:");
        JLabel phoneBookLabel = new JLabel("PHONE BOOK", SwingConstants.CENTER);
        JButton loginButton = new JButton("LOGIN");

        userNameTextField = new JTextField();
        passWordField = new JPasswordField();
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.addActionListener(this::togglePasswordVisibility);

        // Set frame properties
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setName("loginFrame");

        // Set background color and layout
        JPanel backgroundPanel = new JPanel();
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new GridBagLayout());

        // Configure layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Add components to panel
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridy = 0;
        backgroundPanel.add(phoneBookLabel, gbc);

        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.gridy = 1;
        backgroundPanel.add(userNameLabel, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        backgroundPanel.add(userNameTextField, gbc);

        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.gridy = 2;
        backgroundPanel.add(passWordLabel, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        backgroundPanel.add(passWordField, gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridy = 3;
        backgroundPanel.add(showPasswordCheckBox, gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridy = 4;
        backgroundPanel.add(loginButton, gbc);

        pack(); // Adjust the frame size to fit the components

        // Action Listener for the Login Button
        loginButton.addActionListener(this::loginButtonActionPerformed);
    }

    private void togglePasswordVisibility(ActionEvent evt) {
        if (showPasswordCheckBox.isSelected()) {
            passWordField.setEchoChar((char) 0); // Show password
        } else {
            passWordField.setEchoChar('*'); // Hide password
        }
    }

    private void loginButtonActionPerformed(ActionEvent evt) {
        String username = userNameTextField.getText();
        String password = new String(passWordField.getPassword()); // Convert char[] to String for comparison
        if ("root".equals(username) && "root".equals(password)) {
            new Dashboard(username, "admin").setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }
}
