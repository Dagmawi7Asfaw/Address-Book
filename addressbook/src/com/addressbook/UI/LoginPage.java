package com.addressbook.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame {

    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }

    public LoginPage() {
        LoginPanel loginPanel = new LoginPanel();
        setContentPane(loginPanel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Login");
        pack(); // Adjust the frame size to fit the components
        setLocationRelativeTo(null);
    }
}

abstract class BasePanel extends JPanel {
    // Method to create a stylized JLabel
    protected JLabel createLabel(String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        label.setForeground(Color.black);
        return label;
    }

    // Method to create a stylized JButton
    protected JButton createButton() {
        JButton button = new JButton("LOGIN");
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBackground(new Color(70, 130, 180)); // Steel Blue
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        return button;
    }
}

class LoginPanel extends BasePanel {
    private final JTextField userNameTextField;
    private final JPasswordField passWordField;
    private final JCheckBox showPasswordCheckBox;

    public LoginPanel() {
        setBackground(Color.lightGray);
        setLayout(new BorderLayout(15, 15));

        // Title Label
        JLabel titleLabel = createLabel("ADDRESS BOOK", 28);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Center Panel for Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setOpaque(false);
        add(inputPanel, BorderLayout.CENTER);

        inputPanel.add(createLabel("Username:", 16));
        inputPanel.add(userNameTextField = new JTextField());
        inputPanel.add(createLabel("Password:", 16));
        inputPanel.add(passWordField = new JPasswordField());
        inputPanel.add(new JLabel());
        inputPanel.add(showPasswordCheckBox = new JCheckBox("Show Password"));
        showPasswordCheckBox.addActionListener(this::togglePasswordVisibility);


        // Login Button
        JButton loginButton = createButton();
        loginButton.addActionListener(this::loginButtonActionPerformed);
        add(loginButton, BorderLayout.SOUTH);

        userNameTextField.setPreferredSize(new Dimension(300, 30));
        passWordField.setPreferredSize(new Dimension(300, 30));
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

        if ("abc".equals(username) && "root".equals(password)) {
            new Dashboard(username, "admin").setVisible(true);
            SwingUtilities.getWindowAncestor(this).dispose(); // Close the login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }
}