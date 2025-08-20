package com.addressbook.logic;

import javax.swing.*;
import java.util.regex.Pattern;

/**
 * Handles all contact validation logic
 */
public class ContactValidator {
    // Validation patterns
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+[1-9]\\d{11,14}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    
    /**
     * Validates all contact fields
     * @param firstName First name to validate
     * @param lastName Last name to validate
     * @param location Location to validate
     * @param phone Phone to validate
     * @param email Email to validate
     * @param parentComponent Parent component for showing error dialogs
     * @return true if validation fails, false if validation passes
     */
    public static boolean validateFields(String firstName, String lastName, String location, 
                                       String phone, String email, java.awt.Component parentComponent) {
        if (areFieldsEmpty(firstName, lastName, location, phone, email)) {
            showMessage("All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE, parentComponent);
            return true;
        }
        
        if (!isValidPhone(phone)) {
            showMessage("Invalid phone number. Please use E.164 format (e.g., +15551234567).", 
                       "Input Error", JOptionPane.ERROR_MESSAGE, parentComponent);
            return true;
        }
        
        if (!isValidEmail(email)) {
            showMessage("Invalid email address. Please use example@gmail.com format.", 
                       "Input Error", JOptionPane.ERROR_MESSAGE, parentComponent);
            return true;
        }
        
        return false;
    }
    
    /**
     * Checks if any field is empty
     */
    public static boolean areFieldsEmpty(String firstName, String lastName, String location, 
                                       String phone, String email) {
        return firstName.trim().isEmpty() || lastName.trim().isEmpty() ||
               location.trim().isEmpty() || phone.trim().isEmpty() ||
               email.trim().isEmpty();
    }
    
    /**
     * Validates phone number format
     */
    public static boolean isValidPhone(String phone) {
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }
    
    /**
     * Validates email format
     */
    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Shows validation warning for phone field (non-blocking)
     */
    public static void showPhoneWarning(java.awt.Component parentComponent) {
        showMessage("Invalid phone number. Please use E.164 format (e.g., +15551234567).", 
                   "Validation Warning", JOptionPane.WARNING_MESSAGE, parentComponent);
    }
    
    /**
     * Shows validation warning for email field (non-blocking)
     */
    public static void showEmailWarning(java.awt.Component parentComponent) {
        showMessage("Invalid email address. Please use example@gmail.com format.", 
                   "Validation Warning", JOptionPane.WARNING_MESSAGE, parentComponent);
    }
    
    /**
     * Shows a message dialog
     */
    private static void showMessage(String message, String title, int messageType, 
                                   java.awt.Component parentComponent) {
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
    }
} 
