package com.addressbook.UI;

import com.addressbook.logic.ContactPage;
import com.addressbook.services.FileService;
import com.addressbook.services.EditService;
import com.addressbook.services.ContactService;
import com.addressbook.services.ToolsService;
import com.addressbook.services.DateService;
import com.addressbook.utils.ThemeManager;

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
    
    // Service instances for modular functionality
    protected final FileService fileService = new FileService();
    protected final EditService editService = new EditService();
    protected final ContactService contactService = new ContactService();
    protected final ToolsService toolsService = new ToolsService();
    protected final DateService dateService = new DateService();

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
        JTable contactTable = contactPage.getContactTable();
        int currentSize = contactTable.getFont().getSize();
        int newSize = Math.max(10, currentSize + delta);
        contactTable.setFont(new Font("Segoe UI", Font.PLAIN, newSize));
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
        ThemeManager.styleMenuBar(menuBar);
        
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createContactMenu());
        menuBar.add(createToolsMenu());
        menuBar.add(createViewMenu());
        menuBar.add(createHelpMenu());
        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        fileMenu.setForeground(TEXT_PRIMARY);
        
        JMenuItem newContactItem = new JMenuItem("New Contact");
        JMenuItem importItem = new JMenuItem("Import Contacts");
        JMenuItem exportItem = new JMenuItem("Export Contacts");
        JMenuItem printItem = new JMenuItem("Print Contacts");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        // Style menu items
        styleMenuItem(newContactItem);
        styleMenuItem(importItem);
        styleMenuItem(exportItem);
        styleMenuItem(printItem);
        styleMenuItem(exitItem);

        // Add action listeners
        newContactItem.addActionListener(e -> createNewContact());
        importItem.addActionListener(e -> importContacts());
        exportItem.addActionListener(e -> exportContacts());
        printItem.addActionListener(e -> printContacts());
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newContactItem);
        fileMenu.addSeparator();
        fileMenu.add(importItem);
        fileMenu.add(exportItem);
        fileMenu.addSeparator();
        fileMenu.add(printItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        return fileMenu;
    }

    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        editMenu.setForeground(TEXT_PRIMARY);
        
        JMenuItem undoItem = new JMenuItem("Undo");
        JMenuItem redoItem = new JMenuItem("Redo");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem deleteItem = new JMenuItem("Delete");
        JMenuItem selectAllItem = new JMenuItem("Select All");
        JMenuItem findItem = new JMenuItem("Find/Replace");
        
        // Style menu items
        styleMenuItem(undoItem);
        styleMenuItem(redoItem);
        styleMenuItem(cutItem);
        styleMenuItem(copyItem);
        styleMenuItem(pasteItem);
        styleMenuItem(deleteItem);
        styleMenuItem(selectAllItem);
        styleMenuItem(findItem);

        // Add action listeners
        undoItem.addActionListener(e -> undoAction());
        redoItem.addActionListener(e -> redoAction());
        cutItem.addActionListener(e -> cutAction());
        copyItem.addActionListener(e -> copyAction());
        pasteItem.addActionListener(e -> pasteAction());
        deleteItem.addActionListener(e -> deleteAction());
        selectAllItem.addActionListener(e -> selectAllAction());
        findItem.addActionListener(e -> findReplaceAction());

        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.addSeparator();
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.addSeparator();
        editMenu.add(deleteItem);
        editMenu.add(selectAllItem);
        editMenu.addSeparator();
        editMenu.add(findItem);

        return editMenu;
    }

    private JMenu createContactMenu() {
        JMenu contactMenu = new JMenu("Contact");
        contactMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        contactMenu.setForeground(TEXT_PRIMARY);
        
        JMenuItem addContactItem = new JMenuItem("Add Contact");
        JMenuItem editContactItem = new JMenuItem("Edit Contact");
        JMenuItem deleteContactItem = new JMenuItem("Delete Contact");
        JMenuItem duplicateContactItem = new JMenuItem("Duplicate Contact");
        JMenuItem mergeContactsItem = new JMenuItem("Merge Contacts");
        JMenuItem contactGroupsItem = new JMenuItem("Contact Groups");
        
        // Style menu items
        styleMenuItem(addContactItem);
        styleMenuItem(editContactItem);
        styleMenuItem(deleteContactItem);
        styleMenuItem(duplicateContactItem);
        styleMenuItem(mergeContactsItem);
        styleMenuItem(contactGroupsItem);

        // Add action listeners
        addContactItem.addActionListener(e -> addContact());
        editContactItem.addActionListener(e -> editContact());
        deleteContactItem.addActionListener(e -> deleteContact());
        duplicateContactItem.addActionListener(e -> duplicateContact());
        mergeContactsItem.addActionListener(e -> mergeContacts());
        contactGroupsItem.addActionListener(e -> contactGroups());

        contactMenu.add(addContactItem);
        contactMenu.add(editContactItem);
        contactMenu.add(deleteContactItem);
        contactMenu.addSeparator();
        contactMenu.add(duplicateContactItem);
        contactMenu.add(mergeContactsItem);
        contactMenu.addSeparator();
        contactMenu.add(contactGroupsItem);

        return contactMenu;
    }

    private JMenu createToolsMenu() {
        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        toolsMenu.setForeground(TEXT_PRIMARY);
        
        JMenuItem searchItem = new JMenuItem("Search");
        JMenuItem sortItem = new JMenuItem("Sort");
        JMenuItem filterItem = new JMenuItem("Filter");
        JMenuItem statisticsItem = new JMenuItem("📊 Contact Statistics");
        JMenuItem recentlyModifiedItem = new JMenuItem("📅 Recently Modified");
        JMenuItem dateRangeItem = new JMenuItem("📅 Contacts by Date Range");
        JMenuItem ageAnalysisItem = new JMenuItem("📊 Age Analysis");
        JMenuItem themeItem = new JMenuItem("🎨 Change Theme");
        JMenuItem backupItem = new JMenuItem("Backup");
        JMenuItem restoreItem = new JMenuItem("Restore");
        JMenuItem settingsItem = new JMenuItem("Settings");
        
        // Style menu items
        styleMenuItem(searchItem);
        styleMenuItem(sortItem);
        styleMenuItem(filterItem);
        styleMenuItem(statisticsItem);
        styleMenuItem(recentlyModifiedItem);
        styleMenuItem(dateRangeItem);
        styleMenuItem(ageAnalysisItem);
        styleMenuItem(themeItem);
        styleMenuItem(backupItem);
        styleMenuItem(restoreItem);
        styleMenuItem(settingsItem);

        // Add action listeners
        searchItem.addActionListener(e -> searchAction());
        sortItem.addActionListener(e -> sortAction());
        filterItem.addActionListener(e -> filterAction());
        statisticsItem.addActionListener(e -> statisticsAction());
        recentlyModifiedItem.addActionListener(e -> recentlyModifiedAction());
        dateRangeItem.addActionListener(e -> dateRangeAction());
        ageAnalysisItem.addActionListener(e -> ageAnalysisAction());
        themeItem.addActionListener(e -> themeAction());
        backupItem.addActionListener(e -> backupAction());
        restoreItem.addActionListener(e -> restoreAction());
        settingsItem.addActionListener(e -> settingsAction());

        toolsMenu.add(searchItem);
        toolsMenu.add(sortItem);
        toolsMenu.add(filterItem);
        toolsMenu.addSeparator();
        toolsMenu.add(statisticsItem);
        toolsMenu.add(recentlyModifiedItem);
        toolsMenu.add(dateRangeItem);
        toolsMenu.add(ageAnalysisItem);
        toolsMenu.addSeparator();
        toolsMenu.add(themeItem);
        toolsMenu.addSeparator();
        toolsMenu.add(backupItem);
        toolsMenu.add(restoreItem);
        toolsMenu.addSeparator();
        toolsMenu.add(settingsItem);

        return toolsMenu;
    }

    private JMenu createViewMenu() {
        JMenu viewMenu = new JMenu("View");
        viewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        viewMenu.setForeground(TEXT_PRIMARY);
        
        JMenuItem zoomInItem = new JMenuItem("Zoom In");
        JMenuItem zoomOutItem = new JMenuItem("Zoom Out");
        
        // Style menu items
        styleMenuItem(zoomInItem);
        styleMenuItem(zoomOutItem);

        zoomInItem.addActionListener(e -> adjustFontSize(2));
        zoomOutItem.addActionListener(e -> adjustFontSize(-2));

        viewMenu.add(zoomInItem);
        viewMenu.add(zoomOutItem);

        return viewMenu;
    }
    
    private void styleMenuItem(JMenuItem item) {
        item.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        item.setForeground(TEXT_PRIMARY);
        item.setBackground(CARD_BACKGROUND);
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

    // File Menu Actions
    private void createNewContact() {
        fileService.createNewContact(this);
    }

    private void importContacts() {
        fileService.importContacts(this);
    }

    private void exportContacts() {
        fileService.exportContacts(this);
    }

    private void printContacts() {
        fileService.printContacts(this);
    }

    // Edit Menu Actions
    private void undoAction() {
        editService.undoAction(this);
    }

    private void redoAction() {
        editService.redoAction(this);
    }

    private void cutAction() {
        editService.cutAction(this);
    }

    private void copyAction() {
        editService.copyAction(this);
    }

    private void pasteAction() {
        editService.pasteAction(this);
    }

    private void deleteAction() {
        editService.deleteAction(this);
    }

    private void selectAllAction() {
        editService.selectAllAction(this);
    }

    private void findReplaceAction() {
        editService.findReplaceAction(this);
    }

    // Contact Menu Actions
    private void addContact() {
        contactService.addContact(this);
    }

    private void editContact() {
        contactService.editContact(this);
    }

    private void deleteContact() {
        contactService.deleteContact(this);
    }

    private void duplicateContact() {
        contactService.duplicateContact(this);
    }

    private void mergeContacts() {
        contactService.mergeContacts(this);
    }

    private void contactGroups() {
        contactService.contactGroups(this);
    }

    // Tools Menu Actions
    private void searchAction() {
        toolsService.searchAction(this);
    }

    private void sortAction() {
        toolsService.sortAction(this);
    }

    private void filterAction() {
        toolsService.filterAction(this);
    }

    private void backupAction() {
        toolsService.backupAction(this);
    }

    private void restoreAction() {
        toolsService.restoreAction(this);
    }

    private void settingsAction() {
        toolsService.settingsAction(this);
    }
    
    // Date-related actions
    private void statisticsAction() {
        dateService.showContactStatistics(this);
    }
    
    private void recentlyModifiedAction() {
        dateService.showRecentlyModifiedContacts(this);
    }
    
    private void dateRangeAction() {
        dateService.showContactsByDateRange(this);
    }
    
    private void ageAnalysisAction() {
        dateService.showContactAgeAnalysis(this);
    }
    
    private void themeAction() {
        ThemeManager.showThemeSelector(this);
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
