package com.addressbook.utils;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ThemeManager {
    
    public enum Theme {
        LIGHT("Light", "FlatLightLaf"),
        DARK("Dark", "FlatDarkLaf"),
        INTELLIJ("IntelliJ", "FlatIntelliJLaf");
        
        private final String displayName;
        private final String className;
        
        Theme(String displayName, String className) {
            this.displayName = displayName;
            this.className = className;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public String getClassName() {
            return className;
        }
        
        @Override
        public String toString() {
            return displayName;
        }
    }
    
    private static final String THEME_PROPERTIES_FILE = "theme.properties";
    private static Theme currentTheme = Theme.LIGHT;
    
    public static void initialize() {
        loadSavedTheme();
        applyTheme(currentTheme);
        setupGlobalDefaults();
    }
    
    public static void applyTheme(Theme theme) {
        try {
            FlatLaf newLaf = createLaf(theme);
            
            // Apply the new theme
            UIManager.setLookAndFeel(newLaf);
            
            // Update all existing windows
            updateAllWindows();
            
            currentTheme = theme;
            saveTheme(theme);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error applying theme: " + e.getMessage(), 
                "Theme Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static FlatLaf createLaf(Theme theme) throws Exception {
        return switch (theme) {
            case LIGHT -> new FlatLightLaf();
            case DARK -> new FlatDarkLaf();
            case INTELLIJ -> new FlatIntelliJLaf();
        };
    }
    
    private static void setupGlobalDefaults() {
        // Set global UI defaults for consistent styling
        UIManager.put("Button.arc", 8);
        UIManager.put("Component.arc", 8);
        UIManager.put("Component.arrowType", "chevron");
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.innerFocusWidth", 1);
        UIManager.put("ScrollBar.width", 12);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.track", new Color(0xE0E0E0));
        
        // Table styling
        UIManager.put("Table.showGrid", true);
        UIManager.put("Table.gridColor", new Color(0xE0E0E0));
        UIManager.put("TableHeader.height", 32);
        UIManager.put("TableHeader.font", UIManager.getFont("TableHeader.font").deriveFont(Font.BOLD));
        
        // Text field styling
        UIManager.put("TextField.placeholderText", "Enter text...");
        UIManager.put("TextField.showClearButton", true);
        
        // Menu styling
        UIManager.put("MenuBar.background", UIManager.getColor("Panel.background"));
        UIManager.put("MenuBar.border", BorderFactory.createEmptyBorder());
        
        // Dialog styling
        UIManager.put("OptionPane.background", UIManager.getColor("Panel.background"));
        UIManager.put("OptionPane.messageBackground", UIManager.getColor("Panel.background"));
        UIManager.put("OptionPane.buttonFont", UIManager.getFont("Button.font"));
    }
    
    private static void updateAllWindows() {
        // Update all existing windows
        for (Window window : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
        }
        
        // Update all existing dialogs
        for (Window window : Window.getWindows()) {
            if (window instanceof JDialog) {
                SwingUtilities.updateComponentTreeUI(window);
            }
        }
    }
    
    public static void showThemeSelector(Component parent) {
        Theme[] themes = Theme.values();
        String[] themeNames = new String[themes.length];
        
        for (int i = 0; i < themes.length; i++) {
            themeNames[i] = themes[i].getDisplayName();
        }
        
        String selected = (String) JOptionPane.showInputDialog(
            parent,
            "Choose a theme:",
            "Theme Selector",
            JOptionPane.QUESTION_MESSAGE,
            null,
            themeNames,
            currentTheme.getDisplayName()
        );
        
        if (selected != null) {
            for (Theme theme : themes) {
                if (theme.getDisplayName().equals(selected)) {
                    applyTheme(theme);
                    break;
                }
            }
        }
    }
    
    public static Theme getCurrentTheme() {
        return currentTheme;
    }
    
    public static void enableInspector() {
        // Inspector functionality not available in current FlatLaf version
        // Can be enabled in future versions with additional dependencies
    }
    
    private static void loadSavedTheme() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(THEME_PROPERTIES_FILE)) {
            props.load(fis);
            String themeName = props.getProperty("theme", "LIGHT");
            for (Theme theme : Theme.values()) {
                if (theme.name().equals(themeName)) {
                    currentTheme = theme;
                    break;
                }
            }
        } catch (IOException e) {
            // Use default theme if file doesn't exist
            currentTheme = Theme.LIGHT;
        }
    }
    
    private static void saveTheme(Theme theme) {
        Properties props = new Properties();
        props.setProperty("theme", theme.name());
        
        try (FileOutputStream fos = new FileOutputStream(THEME_PROPERTIES_FILE)) {
            props.store(fos, "Theme preferences");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Utility methods for consistent styling
    public static void styleButton(JButton button) {
        button.setFont(UIManager.getFont("Button.font"));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public static void styleTextField(JTextField textField) {
        textField.setFont(UIManager.getFont("TextField.font"));
        textField.setBorder(UIManager.getBorder("TextField.border"));
    }
    
    public static void styleTable(JTable table) {
        table.setFont(UIManager.getFont("Table.font"));
        table.setRowHeight(28);
        table.setShowGrid(true);
        table.setGridColor(UIManager.getColor("Table.gridColor"));
        table.setSelectionBackground(UIManager.getColor("Table.selectionBackground"));
        table.setSelectionForeground(UIManager.getColor("Table.selectionForeground"));
    }
    
    public static void styleMenuBar(JMenuBar menuBar) {
        menuBar.setBackground(UIManager.getColor("MenuBar.background"));
        menuBar.setBorder(UIManager.getBorder("MenuBar.border"));
    }
    
    public static void stylePanel(JPanel panel) {
        panel.setBackground(UIManager.getColor("Panel.background"));
        panel.setBorder(UIManager.getBorder("Panel.border"));
    }
} 