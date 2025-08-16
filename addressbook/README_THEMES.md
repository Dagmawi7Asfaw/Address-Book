# Material Design Theme System for Address Book

## Overview

Your Address Book application now features a modern Material Design 3 theme system that provides beautiful, professional styling with multiple color schemes.

## Available Themes

### Material Design Themes

- **Material Light - Modern**: Clean, light theme with purple accents (default)
- **Material Dark - Modern**: Elegant dark theme with purple accents
- **Material Blue - Professional**: Professional blue theme for business use
- **Material Green - Nature**: Natural green theme for eco-friendly applications
- **Material Purple - Creative**: Creative purple theme for artistic applications

### Classic Themes

- **Flat Light - Classic**: Traditional flat design light theme
- **Flat Dark - Classic**: Traditional flat design dark theme
- **Mac Light - Elegant**: macOS-inspired light theme
- **Mac Dark - Elegant**: macOS-inspired dark theme

## How to Use

### 1. Automatic Theme Application

The application automatically applies the Material Light theme when it starts.

### 2. Manual Theme Selection

- Click the "🎨 Change Theme" button at the bottom of the login page
- Select your preferred theme from the dropdown
- The theme will be applied immediately

### 3. Theme Switching

You can switch themes at any time using the theme selector button.

## Features

### Material Design 3 Compliance

- Follows Google's Material Design 3 guidelines
- Modern color palette with proper contrast ratios
- Consistent spacing and typography
- Professional appearance suitable for business applications

### Responsive Design

- Themes automatically adapt to different screen sizes
- Consistent appearance across different platforms
- Professional look and feel

### Easy Customization

- Add new themes by extending the ThemeUtils class
- Modify existing themes by adjusting color values
- Simple integration with existing Swing components

## Technical Details

### Dependencies

- FlatLaf 3.5.1 (included in lib folder)
- Java 11 or higher

### Implementation

- Uses FlatLaf's advanced theming capabilities
- Custom Material Design color schemes
- UIManager integration for consistent styling
- Automatic theme application on startup

## Benefits

1. **Modern Appearance**: Professional, 2025-ready design
2. **User Choice**: Multiple theme options for different preferences
3. **Accessibility**: Proper contrast ratios and readable fonts
4. **Consistency**: Unified look across all application components
5. **Maintainability**: Easy to update and extend themes

## Future Enhancements

- Theme persistence (remember user's choice)
- Custom theme creation tool
- High contrast themes for accessibility
- Seasonal theme variations
- Integration with system theme preferences

## Usage Example

```java
// Apply a specific theme
ThemeUtils.applyTheme("Material Blue - Professional");

// Apply default theme
ThemeUtils.applyDefaultTheme();

// Get available themes
String[] themes = ThemeUtils.getAvailableThemes();
```

Your Address Book application now has a modern, professional appearance that rivals contemporary web and mobile applications!
