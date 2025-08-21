package com.addressbook.services;

import com.addressbook.dao.ContactDAO;
import com.addressbook.model.ContactDTO;
import com.addressbook.utils.ModernDialog;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ToolsService {
	private final ContactDAO contactDAO;
	
	public ToolsService() {
		this.contactDAO = new ContactDAO();
	}
	
	public void searchAction(Component parent) {
		String searchTerm = JOptionPane.showInputDialog(parent,
			"Enter search term:",
			"Search Contacts",
			JOptionPane.PLAIN_MESSAGE);
			
		if (searchTerm != null && !searchTerm.trim().isEmpty()) {
			List<ContactDTO> contacts = contactDAO.getAllContacts();
			List<ContactDTO> results = contacts.stream()
				.filter(c -> c.getFirstName().toLowerCase().contains(searchTerm.toLowerCase()) ||
						   c.getLastName().toLowerCase().contains(searchTerm.toLowerCase()) ||
						   c.getEmail().toLowerCase().contains(searchTerm.toLowerCase()) ||
						   c.getPhone().contains(searchTerm) ||
						   c.getLocation().toLowerCase().contains(searchTerm.toLowerCase()))
				.toList();
				
			if (results.isEmpty()) {
				ModernDialog.showMessage(parent,
					"Search Results",
					"No contacts found matching '" + searchTerm + "'",
					ModernDialog.DialogType.INFO);
			} else {
				showSearchResults(parent, results, searchTerm);
			}
		}
	}
	
	public void sortAction(Component parent) {
		String[] sortOptions = {"First Name", "Last Name", "Location", "Phone", "Email"};
		String selected = (String) JOptionPane.showInputDialog(parent,
			"Sort contacts by:",
			"Sort Contacts",
			JOptionPane.QUESTION_MESSAGE,
			null,
			sortOptions,
			sortOptions[0]);
			
		if (selected != null) {
			List<ContactDTO> contacts = contactDAO.getAllContacts();
			List<ContactDTO> sorted = switch (selected) {
				case "First Name" -> contacts.stream()
					.sorted((c1, c2) -> c1.getFirstName().compareToIgnoreCase(c2.getFirstName()))
					.toList();
				case "Last Name" -> contacts.stream()
					.sorted((c1, c2) -> c1.getLastName().compareToIgnoreCase(c2.getLastName()))
					.toList();
				case "Location" -> contacts.stream()
					.sorted((c1, c2) -> c1.getLocation().compareToIgnoreCase(c2.getLocation()))
					.toList();
				case "Phone" -> contacts.stream()
					.sorted((c1, c2) -> c1.getPhone().compareTo(c2.getPhone()))
					.toList();
				case "Email" -> contacts.stream()
					.sorted((c1, c2) -> c1.getEmail().compareToIgnoreCase(c2.getEmail()))
					.toList();
				default -> contacts;
			};
			
			showSortResults(parent, sorted, selected);
		}
	}
	
	public void filterAction(Component parent) {
		String[] filterOptions = {"By Location", "By Email Domain", "By Phone Prefix"};
		String selected = (String) JOptionPane.showInputDialog(parent,
			"Filter contacts by:",
			"Filter Contacts",
			JOptionPane.QUESTION_MESSAGE,
			null,
			filterOptions,
			filterOptions[0]);
			
		if (selected != null) {
			String filterValue = JOptionPane.showInputDialog(parent,
				"Enter filter value:",
				"Filter Value",
				JOptionPane.PLAIN_MESSAGE);
				
			if (filterValue != null && !filterValue.trim().isEmpty()) {
				List<ContactDTO> contacts = contactDAO.getAllContacts();
				List<ContactDTO> filtered = switch (selected) {
					case "By Location" -> contacts.stream()
						.filter(c -> c.getLocation().toLowerCase().contains(filterValue.toLowerCase()))
						.toList();
					case "By Email Domain" -> contacts.stream()
						.filter(c -> c.getEmail().toLowerCase().contains(filterValue.toLowerCase()))
						.toList();
					case "By Phone Prefix" -> contacts.stream()
						.filter(c -> c.getPhone().startsWith(filterValue))
						.toList();
					default -> contacts;
				};
				
				if (filtered.isEmpty()) {
					ModernDialog.showMessage(parent,
						"Filter Results",
						"No contacts found matching the filter criteria.",
						ModernDialog.DialogType.INFO);
				} else {
					showFilterResults(parent, filtered, selected, filterValue);
				}
			}
		}
	}
	
	public void backupAction(Component parent) {
		List<ContactDTO> contacts = contactDAO.getAllContacts();
		if (contacts.isEmpty()) {
			ModernDialog.showMessage(parent,
				"Backup",
				"No contacts to backup.",
				ModernDialog.DialogType.INFO);
			return;
		}
		
		ModernDialog.showMessage(parent,
			"Backup",
			"Backup functionality will be implemented in a future version.\nThis will create a backup file of all contacts.",
			ModernDialog.DialogType.INFO);
	}
	
	public void restoreAction(Component parent) {
		ModernDialog.showMessage(parent,
			"Restore",
			"Restore functionality will be implemented in a future version.\nThis will restore contacts from a backup file.",
			ModernDialog.DialogType.INFO);
	}
	
	public void settingsAction(Component parent) {
		ModernDialog.showMessage(parent,
			"Settings",
			"Settings functionality will be implemented in a future version.\nThis will allow you to configure application preferences.",
			ModernDialog.DialogType.INFO);
	}
	
	private void showSearchResults(Component parent, List<ContactDTO> results, String searchTerm) {
		StringBuilder message = new StringBuilder();
		message.append("Found ").append(results.size()).append(" contact(s) matching '").append(searchTerm).append("':\n\n");
		
		for (ContactDTO contact : results) {
			message.append("• ").append(contact.getFirstName()).append(" ").append(contact.getLastName())
				   .append(" (").append(contact.getEmail()).append(")\n");
		}
		
		ModernDialog.showScrollableText(parent, "Search Results", message.toString(), 500,500);
	}
	
	private void showSortResults(Component parent, List<ContactDTO> sorted, String sortBy) {
		StringBuilder message = new StringBuilder();
		message.append("Contacts sorted by ").append(sortBy).append(":\n\n");
		
		for (ContactDTO contact : sorted) {
			message.append("• ").append(contact.getFirstName()).append(" ").append(contact.getLastName())
				   .append(" (").append(contact.getEmail()).append(")\n");
		}
		
		ModernDialog.showScrollableText(parent, "Sort Results", message.toString(), 500,500);
	}
	
	private void showFilterResults(Component parent, List<ContactDTO> filtered, String filterBy, String filterValue) {
		StringBuilder message = new StringBuilder();
		message.append("Contacts filtered by ").append(filterBy).append(" = '").append(filterValue).append("':\n\n");
		
		for (ContactDTO contact : filtered) {
			message.append("• ").append(contact.getFirstName()).append(" ").append(contact.getLastName())
				   .append(" (").append(contact.getEmail()).append(")\n");
		}
		
		ModernDialog.showScrollableText(parent, "Filter Results", message.toString(), 500,500);
	}
} 