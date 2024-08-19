package com.addressbook.UI;

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
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }

    private void initComponents() {
        // Create components
        JLabel userNameLabel = createLabel("Username:", 16);
        JLabel passWordLabel = createLabel("Password:", 16);
        JLabel phoneBookLabel = createLabel("PHONE BOOK", 28);
        JButton loginButton = createLoginButton();

        userNameTextField = new JTextField();
        passWordField = new JPasswordField();
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.addActionListener(this::togglePasswordVisibility);

        userNameTextField.setPreferredSize(new Dimension(300, 30)); // Set width to 300
        passWordField.setPreferredSize(new Dimension(300, 30)); // Set width to 300


        // Set frame properties
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setName("loginFrame");

        // Set background color and layout
        JPanel backgroundPanel = createBackgroundPanel();
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new GridBagLayout());

        // Configure layout constraints
        GridBagConstraints gbc = createGridBagConstraints();

        // Add components to panel
        addComponentToPanel(backgroundPanel, phoneBookLabel, gbc, 0, 0, GridBagConstraints.REMAINDER);
        addComponentToPanel(backgroundPanel, userNameLabel, gbc, 0, 1, GridBagConstraints.RELATIVE);
        addComponentToPanel(backgroundPanel, userNameTextField, gbc, GridBagConstraints.RELATIVE, 1, GridBagConstraints.RELATIVE);
        addComponentToPanel(backgroundPanel, passWordLabel, gbc, 0, 2, GridBagConstraints.RELATIVE);
        addComponentToPanel(backgroundPanel, passWordField, gbc, GridBagConstraints.RELATIVE, 2, GridBagConstraints.RELATIVE);
        addComponentToPanel(backgroundPanel, showPasswordCheckBox, gbc, 0, 3, GridBagConstraints.REMAINDER);
        addComponentToPanel(backgroundPanel, loginButton, gbc, 0, 4, GridBagConstraints.REMAINDER);

        pack(); // Adjust the frame size to fit the components
    }

    private JLabel createLabel(String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        label.setForeground(Color.black);
        return label;
    }

    private JButton createLoginButton() {
        JButton button = new JButton("LOGIN");
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBackground(new Color(70, 130, 180)); // Steel Blue
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.addActionListener(this::loginButtonActionPerformed);
        return button;
    }

    private JPanel createBackgroundPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        return panel;
    }

    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    private void addComponentToPanel(JPanel panel, Component component, GridBagConstraints gbc, int x, int y, int width) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        panel.add(component, gbc);
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
