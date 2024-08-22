package com.addressbook.utils;

import com.addressbook.dao.ThemeDAO;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ThemeUtils {

    public enum Theme {
        FLAT_LIGHT("Flat Light", "com.formdev.flatlaf.FlatLightLaf"),
        FLAT_DARK("Flat Dark", "com.formdev.flatlaf.FlatDarkLaf"),
        FLAT_INTELLIJ("Flat IntelliJ", "com.formdev.flatlaf.FlatIntelliJLaf"),
        FLAT_DARCUELA("Flat Darcula", "com.formdev.flatlaf.FlatDarculaLaf");

        private final String displayName;
        private final String lookAndFeelClassName;

        Theme(String displayName, String lookAndFeelClassName) {
            this.displayName = displayName;
            this.lookAndFeelClassName = lookAndFeelClassName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void apply(JFrame frame) {
            try {
                UIManager.setLookAndFeel(lookAndFeelClassName);
                SwingUtilities.updateComponentTreeUI(frame);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static Theme fromDisplayName(String displayName) {
            return Arrays.stream(values())
                    .filter(theme -> theme.displayName.equals(displayName))
                    .findFirst()
                    .orElse(null);
        }
    }

    private static final ThemeDAO themeDAO = new ThemeDAO();

    public static List<Theme> getAvailableThemes() {
        return Arrays.asList(Theme.values());
    }

    public static void applyAndSaveTheme(JFrame jFrame, Theme theme) {
        theme.apply(jFrame);
        themeDAO.saveTheme(Utils.DEFAULT_USER_NAME, theme.getDisplayName()); // Replace "user123" with the actual username
    }

    public static Theme loadSavedTheme() {
        String savedThemeName = themeDAO.getSavedTheme(Utils.DEFAULT_USER_NAME); // Replace "user123" with the actual username
        return Theme.fromDisplayName(savedThemeName);
    }
}
