package com.addressbook.utils;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class ThemeUtils {
    
    public enum Theme {
        FLAT_LIGHT("FlatLightLaf"),
        FLAT_DARK("FlatDarkLaf"),
        MAC_LIGHT("FlatMacLightLaf"),
        MAC_DARK("FlatMacDarkLaf");
        
        private final String className;
        
        Theme(String className) {
            this.className = className;
        }
        
        public String getClassName() {
            return className;
        }
        
        public static Theme fromString(String themeName) {
            for (Theme theme : values()) {
                if (theme.className.equals(themeName)) {
                    return theme;
                }
            }
            return FLAT_LIGHT; // Default
        }
    }
    
    public static void applyTheme(Theme theme) {
        try {
            switch (theme) {
                case FLAT_LIGHT:
                    FlatLightLaf.setup();
                    break;
                case FLAT_DARK:
                    FlatDarkLaf.setup();
                    break;
                case MAC_LIGHT:
                    FlatMacLightLaf.setup();
                    break;
                case MAC_DARK:
                    FlatMacDarkLaf.setup();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback to default theme
            FlatLightLaf.setup();
        }
    }
    
    public static void applyTheme(String themeName) {
        applyTheme(Theme.fromString(themeName));
    }
} 