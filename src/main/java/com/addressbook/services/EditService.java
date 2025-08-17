package com.addressbook.services;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.util.Stack;

public class EditService {
    private final Stack<String> undoStack = new Stack<>();
    private final Stack<String> redoStack = new Stack<>();
    private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    
    public void undoAction(Component parent) {
        if (undoStack.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                "Nothing to undo.",
                "Undo",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String action = undoStack.pop();
        redoStack.push(action);
        
        JOptionPane.showMessageDialog(parent,
            "Undone: " + action,
            "Undo",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void redoAction(Component parent) {
        if (redoStack.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                "Nothing to redo.",
                "Redo",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String action = redoStack.pop();
        undoStack.push(action);
        
        JOptionPane.showMessageDialog(parent,
            "Redone: " + action,
            "Redo",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void cutAction(Component parent) {
        // Get selected text from focused component
        Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (focused instanceof JTextField) {
            JTextField textField = (JTextField) focused;
            String selectedText = textField.getSelectedText();
            if (selectedText != null && !selectedText.isEmpty()) {
                copyToClipboard(selectedText);
                textField.replaceSelection("");
                undoStack.push("Cut: " + selectedText);
                JOptionPane.showMessageDialog(parent,
                    "Text cut to clipboard: " + selectedText,
                    "Cut",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parent,
                    "No text selected to cut.",
                    "Cut",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(parent,
                "Please select text in a text field to cut.",
                "Cut",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void copyAction(Component parent) {
        // Get selected text from focused component
        Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (focused instanceof JTextField) {
            JTextField textField = (JTextField) focused;
            String selectedText = textField.getSelectedText();
            if (selectedText != null && !selectedText.isEmpty()) {
                copyToClipboard(selectedText);
                JOptionPane.showMessageDialog(parent,
                    "Text copied to clipboard: " + selectedText,
                    "Copy",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parent,
                    "No text selected to copy.",
                    "Copy",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(parent,
                "Please select text in a text field to copy.",
                "Copy",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void pasteAction(Component parent) {
        // Paste text to focused component
        Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (focused instanceof JTextField) {
            JTextField textField = (JTextField) focused;
            String clipboardText = getFromClipboard();
            if (clipboardText != null && !clipboardText.isEmpty()) {
                textField.replaceSelection(clipboardText);
                undoStack.push("Paste: " + clipboardText);
                JOptionPane.showMessageDialog(parent,
                    "Text pasted from clipboard: " + clipboardText,
                    "Paste",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parent,
                    "No text in clipboard to paste.",
                    "Paste",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(parent,
                "Please focus on a text field to paste.",
                "Paste",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void deleteAction(Component parent) {
        // Delete selected text from focused component
        Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (focused instanceof JTextField) {
            JTextField textField = (JTextField) focused;
            String selectedText = textField.getSelectedText();
            if (selectedText != null && !selectedText.isEmpty()) {
                textField.replaceSelection("");
                undoStack.push("Delete: " + selectedText);
                JOptionPane.showMessageDialog(parent,
                    "Text deleted: " + selectedText,
                    "Delete",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parent,
                    "No text selected to delete.",
                    "Delete",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(parent,
                "Please select text in a text field to delete.",
                "Delete",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void selectAllAction(Component parent) {
        // Select all text in focused component
        Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (focused instanceof JTextField) {
            JTextField textField = (JTextField) focused;
            textField.selectAll();
            JOptionPane.showMessageDialog(parent,
                "All text selected in: " + textField.getName(),
                "Select All",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(parent,
                "Please focus on a text field to select all.",
                "Select All",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void findReplaceAction(Component parent) {
        // Create a simple find/replace dialog
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        
        JTextField findField = new JTextField();
        JTextField replaceField = new JTextField();
        JCheckBox caseSensitiveBox = new JCheckBox("Case sensitive");
        JCheckBox wholeWordBox = new JCheckBox("Whole word only");
        
        panel.add(new JLabel("Find:"));
        panel.add(findField);
        panel.add(new JLabel("Replace with:"));
        panel.add(replaceField);
        panel.add(new JLabel(""));
        panel.add(caseSensitiveBox);
        panel.add(new JLabel(""));
        panel.add(wholeWordBox);
        
        int result = JOptionPane.showConfirmDialog(parent, panel, "Find and Replace", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
        if (result == JOptionPane.OK_OPTION) {
            String findText = findField.getText();
            String replaceText = replaceField.getText();
            
            if (findText != null && !findText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(parent,
                    "Find/Replace functionality will be implemented in a future version.\n" +
                    "Find: '" + findText + "'\n" +
                    "Replace: '" + replaceText + "'\n" +
                    "Case sensitive: " + caseSensitiveBox.isSelected() + "\n" +
                    "Whole word: " + wholeWordBox.isSelected(),
                    "Find and Replace",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parent,
                    "Please enter text to find.",
                    "Find and Replace",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, selection);
    }
    
    private String getFromClipboard() {
        try {
            return (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            return null;
        }
    }
} 