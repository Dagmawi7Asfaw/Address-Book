package com.addressbook.UI;

import com.addressbook.utils.Utils;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.addressbook.dao.ThemeDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame {

    private JTextField userNameTextField;
    private JPasswordField passWordField;
    private JCheckBox showPasswordCheckBox;

    public LoginPage() {
        applySavedTheme();
        initComponents();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
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

        userNameTextField.setPreferredSize(new Dimension(300, 30)); // Set width to 300
        passWordField.setPreferredSize(new Dimension(300, 30));

        // Calculate frame size
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int width = (int) (screenSize.width * 0.45);
        int height = (int) (screenSize.height * 0.45);

        // Set frame properties
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login");
        setSize(width, height);
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

    private void applySavedTheme() {
        ThemeDAO themeDAO = new ThemeDAO();
        String savedTheme = themeDAO.getSavedTheme(Utils.DEFAULT_USER_NAME);

        try {
            switch (savedTheme) {
                case "FlatLightLaf":
                    FlatLightLaf.setup();
                    break;
                case "FlatDarkLaf":
                    FlatDarkLaf.setup();
                    break;
                case "FlatIntelliJLaf":
                    FlatIntelliJLaf.setup();
                    break;
                case "FlatDarculaLaf":
                    FlatDarculaLaf.setup();
                    break;
                default:
                    FlatLightLaf.setup(); // Fallback to a default theme
            }
        } catch (Exception e) {
            e.printStackTrace();
            FlatLightLaf.setup(); // Fallback to a default theme on error
        }
    }
}
