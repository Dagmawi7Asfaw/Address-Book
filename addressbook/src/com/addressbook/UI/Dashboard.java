package com.addressbook.UI;

import com.addressbook.logic.ContactPage;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dashboard extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(Dashboard.class.getName());
    private final String username;
    private final String role;
    private final CardLayout layout;
    private ContactPage contactPage;

    public Dashboard(String username, String role) {
        this.username = username;
        this.role = role;
        this.layout = new CardLayout();
        initComponents();
    }

    private void initComponents() {
        JPanel displayPanel = createDisplayPanel();
        JLabel titleLabel = createTitleLabel();

        // Set up the frame
        setTitle("PHONE BOOK");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1700, 600);
        setLocationRelativeTo(null);
        setBackground(new Color(245, 245, 245));

        // Set up the main panel
        JPanel mainPanel = createMainPanel(titleLabel, displayPanel);

        setJMenuBar(createMenuBar());

        // Add main panel to the frame
        add(mainPanel);
    }

    private JPanel createDisplayPanel() {
        JPanel displayPanel = new JPanel(layout);
        contactPage = new ContactPage();
        displayPanel.add("Contacts", contactPage);
        displayPanel.setBackground(Color.WHITE);
        return displayPanel;
    }

    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("PHONE BOOK");
        //JLabel titleLabel = new JLabel("PHONE BOOK - " + role + ": " + username);
        titleLabel.setFont(new Font("Gadugi", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(0, 102, 204));
        return titleLabel;
    }

    private JPanel createMainPanel(JLabel titleLabel, JPanel displayPanel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(displayPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createViewMenu());
        menuBar.add(createHelpMenu());
        return menuBar;
    }

    private JMenu createViewMenu() {
        JMenu viewMenu = new JMenu("View");
        JMenuItem zoomInItem = new JMenuItem("Zoom In");
        JMenuItem zoomOutItem = new JMenuItem("Zoom Out");

        zoomInItem.addActionListener(e -> adjustFontSize(2));
        zoomOutItem.addActionListener(e -> adjustFontSize(-2));

        viewMenu.add(zoomInItem);
        viewMenu.add(zoomOutItem);

        return viewMenu;
    }

    private void adjustFontSize(int delta) {
        int currentSize = contactPage.contactTable.getFont().getSize();
        int newSize = Math.max(10, currentSize + delta);
        contactPage.contactTable.setFont(new Font("Arial", Font.PLAIN, newSize));
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");

        aboutItem.addActionListener(e -> showAboutDialog());

        helpMenu.add(aboutItem);
        return helpMenu;
    }

    private void showAboutDialog() {
        String[] links = {
                "https://t.me/dagmawy",
                "https://t.me/blen96",
                "https://t.me/Ruth08",
                "https://t.me/Hermi15"
        };

        String[] developerNames = {
                "Dagmawi Asfaw",
                "Blen Belay",
                "Ruth Berhanu",
                "Hermela Abebayehu"
        };

        JTextPane textPane = createAboutTextPane(links, developerNames);
        JOptionPane optionPane = new JOptionPane(textPane, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, "About");
        dialog.setSize(500, 230);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JTextPane createAboutTextPane(String[] links, String[] developerNames) {
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);

        StringBuilder htmlMessage = new StringBuilder("<html><h2>DEVELOPERS:</h2>");
        for (int i = 0; i < links.length; i++) {
            htmlMessage.append(developerNames[i])
                    .append(": <a href=\"").append(links[i]).append("\">").append(links[i]).append("</a><br>");
        }
        htmlMessage.append("</html>");
        textPane.setText(htmlMessage.toString());

        textPane.addHyperlinkListener(this::handleHyperlinkEvent);
        return textPane;
    }

    private void handleHyperlinkEvent(HyperlinkEvent event) {
        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
                Desktop.getDesktop().browse(new URI(event.getURL().toString()));
            } catch (IOException | URISyntaxException ex) {
                LOGGER.log(Level.SEVERE, "Error opening link: " + event.getURL(), ex);
                JOptionPane.showMessageDialog(this, "Error opening link: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
