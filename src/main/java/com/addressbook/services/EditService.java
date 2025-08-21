package com.addressbook.services;

import com.addressbook.utils.ModernDialog;
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
			ModernDialog.showMessage(parent,
				"Undo",
				"Nothing to undo.",
				ModernDialog.DialogType.INFO);
			return;
		}
		
		String action = undoStack.pop();
		redoStack.push(action);
		ModernDialog.showMessage(parent,
			"Undo",
			"Undone: " + action,
			ModernDialog.DialogType.SUCCESS);
	}
	
	public void redoAction(Component parent) {
		if (redoStack.isEmpty()) {
			ModernDialog.showMessage(parent,
				"Redo",
				"Nothing to redo.",
				ModernDialog.DialogType.INFO);
			return;
		}
		
		String action = redoStack.pop();
		undoStack.push(action);
		ModernDialog.showMessage(parent,
			"Redo",
			"Redone: " + action,
			ModernDialog.DialogType.SUCCESS);
	}
	
	public void cutAction(Component parent) {
		Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		if (focused instanceof JTextField textField) {
			String selectedText = textField.getSelectedText();
			if (selectedText != null && !selectedText.isEmpty()) {
				copyToClipboard(selectedText);
				textField.replaceSelection("");
				undoStack.push("Cut: " + selectedText);
				ModernDialog.showMessage(parent,
					"Cut",
					"Text cut to clipboard: " + selectedText,
					ModernDialog.DialogType.SUCCESS);
			} else {
				ModernDialog.showMessage(parent,
					"Cut",
					"No text selected to cut.",
					ModernDialog.DialogType.INFO);
			}
		} else {
			ModernDialog.showMessage(parent,
				"Cut",
				"Please select text in a text field to cut.",
				ModernDialog.DialogType.INFO);
		}
	}
	
	public void copyAction(Component parent) {
		Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		if (focused instanceof JTextField textField) {
			String selectedText = textField.getSelectedText();
			if (selectedText != null && !selectedText.isEmpty()) {
				copyToClipboard(selectedText);
				ModernDialog.showMessage(parent,
					"Copy",
					"Text copied to clipboard: " + selectedText,
					ModernDialog.DialogType.SUCCESS);
			} else {
				ModernDialog.showMessage(parent,
					"Copy",
					"No text selected to copy.",
					ModernDialog.DialogType.INFO);
			}
		} else {
			ModernDialog.showMessage(parent,
				"Copy",
				"Please select text in a text field to copy.",
				ModernDialog.DialogType.INFO);
		}
	}
	
	public void pasteAction(Component parent) {
		Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		if (focused instanceof JTextField textField) {
			String clipboardText = getFromClipboard();
			if (clipboardText != null && !clipboardText.isEmpty()) {
				textField.replaceSelection(clipboardText);
				undoStack.push("Paste: " + clipboardText);
				ModernDialog.showMessage(parent,
					"Paste",
					"Text pasted from clipboard: " + clipboardText,
					ModernDialog.DialogType.SUCCESS);
			} else {
				ModernDialog.showMessage(parent,
					"Paste",
					"No text in clipboard to paste.",
					ModernDialog.DialogType.INFO);
			}
		} else {
			ModernDialog.showMessage(parent,
				"Paste",
				"Please focus on a text field to paste.",
				ModernDialog.DialogType.INFO);
		}
	}
	
	public void deleteAction(Component parent) {
		Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		if (focused instanceof JTextField textField) {
			String selectedText = textField.getSelectedText();
			if (selectedText != null && !selectedText.isEmpty()) {
				textField.replaceSelection("");
				undoStack.push("Delete: " + selectedText);
				ModernDialog.showMessage(parent,
					"Delete",
					"Text deleted: " + selectedText,
					ModernDialog.DialogType.SUCCESS);
			} else {
				ModernDialog.showMessage(parent,
					"Delete",
					"No text selected to delete.",
					ModernDialog.DialogType.INFO);
			}
		} else {
			ModernDialog.showMessage(parent,
				"Delete",
				"Please select text in a text field to delete.",
				ModernDialog.DialogType.INFO);
		}
	}
	
	public void selectAllAction(Component parent) {
		Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		if (focused instanceof JTextField textField) {
			textField.selectAll();
			ModernDialog.showMessage(parent,
				"Select All",
				"All text selected in: " + (textField.getName() != null ? textField.getName() : "field"),
				ModernDialog.DialogType.INFO);
		} else {
			ModernDialog.showMessage(parent,
				"Select All",
				"Please focus on a text field to select all.",
				ModernDialog.DialogType.INFO);
		}
	}
	
	public void findReplaceAction(Component parent) {
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
				ModernDialog.showMessage(parent,
					"Find and Replace",
					"Find/Replace functionality will be implemented in a future version.\n" +
					"Find: '" + findText + "'\n" +
					"Replace: '" + replaceText + "'\n" +
					"Case sensitive: " + caseSensitiveBox.isSelected() + "\n" +
					"Whole word: " + wholeWordBox.isSelected(),
					ModernDialog.DialogType.INFO);
			} else {
				ModernDialog.showMessage(parent,
					"Find and Replace",
					"Please enter text to find.",
					ModernDialog.DialogType.WARNING);
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