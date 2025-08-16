package com.addressbook.UI;

import com.addressbook.dao.ThemeDAO;
import com.addressbook.logic.ContactPage;
import com.addressbook.utils.ThemeUtils;
import com.addressbook.utils.Utils;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Abstract class defining common UI components and initialization
abstract class AbstractDashboard extends JFrame {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDashboard.class.getName());
    protected final String username;
    protected final String role;
    protected final CardLayout layout = new CardLayout();
    protected ContactPage contactPage;

    // Professional color scheme
    protected static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Professional blue
    protected static final Color SECONDARY_COLOR = new Color(52, 73, 94); // Dark slate
    protected static final Color ACCENT_COLOR = new Color(46, 204, 113); // Success green
    protected static final Color BACKGROUND_COLOR = new Color(248, 249, 250); // Light gray
    protected static final Color CARD_BACKGROUND = Color.WHITE;
    protected static final Color BORDER_COLOR = new Color(229, 231, 235);
    protected static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    protected static final Color TEXT_SECONDARY = new Color(108, 117, 125);

    public AbstractDashboard(String username, String role) {
        this.username = username;
        this.role = role;
        initComponents();
    }

    protected abstract JPanel createDisplayPanel();

    protected abstract JLabel createTitleLabel();

    protected JPanel createMainPanel(JLabel titleLabel, JPanel displayPanel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Create header panel with shadow effect
        JPanel headerPanel = createHeaderPanel(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create content panel with card-like appearance
        JPanel contentPanel = createContentPanel(displayPanel);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        return mainPanel;
    }

    private JPanel createHeaderPanel(JLabel titleLabel) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CARD_BACKGROUND);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        
        // Add subtle shadow effect
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 0, 2, 0),
            headerPanel.getBorder()
        ));
        
        // Create title container with better spacing
        JPanel titleContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleContainer.setBackground(CARD_BACKGROUND);
        titleContainer.add(titleLabel);
        
        // Add user info on the right
        JPanel userInfoPanel = createUserInfoPanel();
        
        headerPanel.add(titleContainer, BorderLayout.WEST);
        headerPanel.add(userInfoPanel, BorderLayout.EAST);
        
        return headerPanel;
    }

    private JPanel createUserInfoPanel() {
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        userPanel.setBackground(CARD_BACKGROUND);
        
        // User avatar placeholder
        JLabel avatarLabel = new JLabel("👤");
        avatarLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        avatarLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        // User info
        JLabel userLabel = new JLabel(username);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(TEXT_PRIMARY);
        
        JLabel roleLabel = new JLabel(role);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roleLabel.setForeground(TEXT_SECONDARY);
        
        JPanel userTextPanel = new JPanel();
        userTextPanel.setLayout(new BoxLayout(userTextPanel, BoxLayout.Y_AXIS));
        userTextPanel.setBackground(CARD_BACKGROUND);
        userTextPanel.add(userLabel);
        userTextPanel.add(roleLabel);
        
        userPanel.add(userTextPanel);
        userPanel.add(avatarLabel);
        
        return userPanel;
    }

    private JPanel createContentPanel(JPanel displayPanel) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        
        // Add card-like appearance to the display panel
        displayPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        displayPanel.setBackground(CARD_BACKGROUND);
        
        contentPanel.add(displayPanel, BorderLayout.CENTER);
        return contentPanel;
    }

    protected void adjustFontSize(int delta) {
        int currentSize = contactPage.contactTable.getFont().getSize();
        int newSize = Math.max(10, currentSize + delta);
        contactPage.contactTable.setFont(new Font("Segoe UI", Font.PLAIN, newSize));
    }

    private void initComponents() {
        JPanel displayPanel = createDisplayPanel();
        JLabel titleLabel = createTitleLabel();

        // Set up the frame with professional appearance
        setTitle("Address Book - Professional Contact Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1800, 1000);
        setLocationRelativeTo(null);
        setBackground(BACKGROUND_COLOR);
        
        // Remove default window decorations for custom look
        setUndecorated(false);
        
        // Set up the main panel
        JPanel mainPanel = createMainPanel(titleLabel, displayPanel);
        setJMenuBar(createMenuBar());

        // Add main panel to the frame
        add(mainPanel);
    }

    protected JTextPane createAboutTextPane(String[] links, String[] developerNames) {
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        textPane.setBackground(CARD_BACKGROUND);
        textPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        StringBuilder htmlMessage = new StringBuilder("<html><div style='font-family: Segoe UI, Arial, sans-serif;'>");
        htmlMessage.append("<h2 style='color: #2980b9; margin-bottom: 20px;'>DEVELOPERS</h2>");
        for (int i = 0; i < links.length; i++) {
            htmlMessage.append("<div style='margin-bottom: 15px;'>")
                    .append("<strong style='color: #2c3e50;'>").append(developerNames[i]).append("</strong><br>")
                    .append("<a href=\"").append(links[i]).append("\" style='color: #3498db; text-decoration: none;'>")
                    .append(links[i]).append("</a></div>");
        }
        htmlMessage.append("</div></html>");
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

    public void showAboutDialog() {
        String[] links = {
                "https://t.me/realloc"
        };

        String[] developerNames = {
                "Dagmawi Asfaw"
        };

        JTextPane textPane = createAboutTextPane(links, developerNames);
        JOptionPane optionPane = new JOptionPane(textPane, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, "About Address Book");
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(CARD_BACKGROUND);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        
        menuBar.add(createViewMenu());
        menuBar.add(createHelpMenu());
        return menuBar;
    }

    private JMenu createViewMenu() {
        JMenu viewMenu = new JMenu("View");
        viewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        viewMenu.setForeground(TEXT_PRIMARY);
        
        JMenuItem zoomInItem = new JMenuItem("Zoom In");
        JMenuItem zoomOutItem = new JMenuItem("Zoom Out");
        
        // Theme submenu with modern styling
        JMenu themeMenu = new JMenu("Theme");
        JMenuItem lightTheme = new JMenuItem("Light Theme");
        JMenuItem darkTheme = new JMenuItem("Dark Theme");
        JMenuItem macLightTheme = new JMenuItem("Mac Light");
        JMenuItem macDarkTheme = new JMenuItem("Mac Dark");

        // Style menu items
        styleMenuItem(zoomInItem);
        styleMenuItem(zoomOutItem);
        styleMenuItem(lightTheme);
        styleMenuItem(darkTheme);
        styleMenuItem(macLightTheme);
        styleMenuItem(macDarkTheme);

        zoomInItem.addActionListener(e -> adjustFontSize(2));
        zoomOutItem.addActionListener(e -> adjustFontSize(-2));
        
        lightTheme.addActionListener(e -> changeTheme(ThemeUtils.Theme.FLAT_LIGHT));
        darkTheme.addActionListener(e -> changeTheme(ThemeUtils.Theme.FLAT_DARK));
        macLightTheme.addActionListener(e -> changeTheme(ThemeUtils.Theme.MAC_LIGHT));
        macDarkTheme.addActionListener(e -> changeTheme(ThemeUtils.Theme.MAC_DARK));

        viewMenu.add(zoomInItem);
        viewMenu.add(zoomOutItem);
        viewMenu.addSeparator();
        viewMenu.add(themeMenu);
        themeMenu.add(lightTheme);
        themeMenu.add(darkTheme);
        themeMenu.add(macLightTheme);
        themeMenu.add(macDarkTheme);

        return viewMenu;
    }
    
    private void styleMenuItem(JMenuItem item) {
        item.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        item.setForeground(TEXT_PRIMARY);
        item.setBackground(CARD_BACKGROUND);
    }
    
    private void changeTheme(ThemeUtils.Theme theme) {
        ThemeUtils.applyTheme(theme);
        ThemeDAO themeDAO = new ThemeDAO();
        themeDAO.saveTheme(Utils.DEFAULT_USER_NAME, theme.getClassName());
        
        // Refresh the UI
        SwingUtilities.updateComponentTreeUI(this);
        if (contactPage != null) {
            SwingUtilities.updateComponentTreeUI(contactPage);
        }
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        helpMenu.setForeground(TEXT_PRIMARY);
        
        JMenuItem aboutItem = new JMenuItem("About");
        styleMenuItem(aboutItem);

        aboutItem.addActionListener(e -> showAboutDialog());

        helpMenu.add(aboutItem);
        return helpMenu;
    }
}

// Concrete class implementing the actual Dashboard
public class Dashboard extends AbstractDashboard {

    public Dashboard(String username, String role) {
        super(username, role);
        // Start maximized immediately
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    @Override
    protected JPanel createDisplayPanel() {
        JPanel displayPanel = new JPanel(layout);
        contactPage = new ContactPage();
        displayPanel.add("Contacts", contactPage);
        displayPanel.setBackground(CARD_BACKGROUND);
        return displayPanel;
    }

    @Override
    protected JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("Address Book");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        
        // Add subtle shadow effect
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        return titleLabel;
    }
}
