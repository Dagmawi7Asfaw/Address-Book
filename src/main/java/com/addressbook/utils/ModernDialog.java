package com.addressbook.utils;

import javax.swing.*;
import java.awt.*;

public class ModernDialog {
	public enum DialogType { INFO, WARNING, ERROR, SUCCESS }

	private static JDialog createBaseDialog(Component parent, String title, Dimension preferredSize) {
		Window owner = parent != null ? SwingUtilities.getWindowAncestor(parent) : null;
		JDialog dialog = new JDialog(owner, title, Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setLayout(new BorderLayout());
		dialog.getRootPane().putClientProperty("JComponent.roundRect", true);
		dialog.getRootPane().setBorder(BorderFactory.createEmptyBorder());
		if (preferredSize != null) dialog.setPreferredSize(preferredSize);
		return dialog;
	}

	private static JPanel createHeader(String title, DialogType type) {
		JPanel header = new JPanel(new BorderLayout());
		header.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 16));
		header.setBackground(UIManager.getColor("Panel.background"));

		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		titleLabel.setForeground(UIManager.getColor("Label.foreground"));

		JLabel iconLabel = new JLabel(switch (type) {
			case SUCCESS -> "✅";
			case WARNING -> "⚠️";
			case ERROR -> "❌";
			default -> "ℹ️";
		});
		iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
		iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

		JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		left.setOpaque(false);
		left.add(iconLabel);
		left.add(Box.createHorizontalStrut(8));
		left.add(titleLabel);

		JButton close = new JButton("✖");
		close.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
		close.setFocusable(false);
		close.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
		close.addActionListener(e -> SwingUtilities.getWindowAncestor(close).dispose());

		header.add(left, BorderLayout.WEST);
		header.add(close, BorderLayout.EAST);
		return header;
	}

	public static void showMessage(Component parent, String title, String message, DialogType type) {
		JDialog dialog = createBaseDialog(parent, title, new Dimension(520, 240));
		dialog.add(createHeader(title, type), BorderLayout.NORTH);

		JTextArea textArea = new JTextArea(message);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textArea.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setPreferredSize(new Dimension(480, 120));

		JPanel content = new JPanel(new BorderLayout());
		content.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		content.add(scrollPane, BorderLayout.CENTER);

		JButton ok = new JButton("OK");
		ok.addActionListener(e -> dialog.dispose());
		JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		footer.add(ok);

		dialog.add(content, BorderLayout.CENTER);
		dialog.add(footer, BorderLayout.SOUTH);
		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}

	public static boolean showConfirm(Component parent, String title, String message, DialogType type) {
		final boolean[] result = {false};
		JDialog dialog = createBaseDialog(parent, title, new Dimension(520, 240));
		dialog.add(createHeader(title, type), BorderLayout.NORTH);

		JTextArea textArea = new JTextArea(message);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textArea.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		JPanel content = new JPanel(new BorderLayout());
		content.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		content.add(scrollPane, BorderLayout.CENTER);

		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		yes.addActionListener(e -> { result[0] = true; dialog.dispose(); });
		no.addActionListener(e -> { result[0] = false; dialog.dispose(); });
		JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		footer.add(no);
		footer.add(yes);

		dialog.add(content, BorderLayout.CENTER);
		dialog.add(footer, BorderLayout.SOUTH);
		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
		return result[0];
	}

	public static void showScrollableText(Component parent, String title, String text, int width, int height) {
		JDialog dialog = createBaseDialog(parent, title, new Dimension(width, height));
		dialog.add(createHeader(title, DialogType.INFO), BorderLayout.NORTH);

		JTextArea textArea = new JTextArea(text);
		textArea.setEditable(false);
		textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		textArea.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		JPanel content = new JPanel(new BorderLayout());
		content.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		content.add(scrollPane, BorderLayout.CENTER);

		JButton ok = new JButton("Close");
		ok.addActionListener(e -> dialog.dispose());
		JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		footer.add(ok);

		dialog.add(content, BorderLayout.CENTER);
		dialog.add(footer, BorderLayout.SOUTH);
		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}
} 