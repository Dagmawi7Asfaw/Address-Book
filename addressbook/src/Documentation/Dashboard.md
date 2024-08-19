Here's a comprehensive documentation for the `Dashboard` class:

---

# Dashboard Class Documentation

## Overview

The `Dashboard` class represents the main user interface of the address book application after user login. It provides a central hub for navigating between different views, such as the contact management page. The class sets up a layout with dynamic content panels, menu options for zooming in and out, and an "About" dialog to display developer information.

## Table of Contents

1. [Class Description](#class-description)
2. [Constructor](#constructor)
3. [Methods](#methods)
    - [initComponents()](#initcomponents)
    - [createDisplayPanel()](#createdisplaypanel)
    - [createTitleLabel()](#createtitlelabel)
    - [createMainPanel(JLabel titleLabel, JPanel displayPanel)](#createmainpaneljlabel-titleLabel-jpanel-displayPanel)
    - [createMenuBar()](#createmenubar)
    - [createViewMenu()](#createviewmenu)
    - [adjustFontSize(int delta)](#adjustfontsizeint-delta)
    - [createHelpMenu()](#createhelpmenu)
    - [showAboutDialog()](#showaboutdialog)
    - [createAboutTextPane(String[] links, String[] developerNames)](#createabouttextpanestring-links-string-developerNames)
    - [handleHyperlinkEvent(HyperlinkEvent event)](#handlehyperlinkeventhyperlinkevent-event)
4. [Constants](#constants)

## Class Description

The `Dashboard` class provides the main interface for the address book application. It manages the layout and display of various panels using `CardLayout`, provides menu options for zooming in and out, and includes a feature to show developer information.

## Constructor

```java
public Dashboard(String username, String role)
```

**Parameters:**
- `username`: The username of the logged-in user.
- `role`: The role of the logged-in user (e.g., admin, user).

**Description:**
Initializes the `Dashboard` with the given username and role. Sets up the layout and user interface components by calling `initComponents()`.

## Methods

### `initComponents()`

```java
private void initComponents()
```

**Description:**
Initializes and configures the main components of the `Dashboard` frame. Sets up the title label, display panel, and menu bar. Adds these components to the frame's main panel.

### `createDisplayPanel()`

```java
private JPanel createDisplayPanel()
```

**Returns:** `JPanel` - The panel that displays different views using `CardLayout`.

**Description:**
Creates a `JPanel` with `CardLayout` and adds the `ContactPage` instance to it. Configures the panel's background color.

### `createTitleLabel()`

```java
private JLabel createTitleLabel()
```

**Returns:** `JLabel` - The label displaying the title of the dashboard.

**Description:**
Creates a `JLabel` for the dashboard's title, formatted with the role and username. Configures the font, alignment, and color.

### `createMainPanel(JLabel titleLabel, JPanel displayPanel)`

```java
private JPanel createMainPanel(JLabel titleLabel, JPanel displayPanel)
```

**Parameters:**
- `titleLabel`: The title label to be displayed at the top.
- `displayPanel`: The panel containing the main content.

**Returns:** `JPanel` - The main panel that combines the title and display panels.

**Description:**
Creates a `JPanel` with `BorderLayout`, adds the title label and display panel to it, and sets the background color.

### `createMenuBar()`

```java
private JMenuBar createMenuBar()
```

**Returns:** `JMenuBar` - The menu bar for the `Dashboard`.

**Description:**
Creates and configures the menu bar with "View" and "Help" menus. Adds zoom functionality and an "About" dialog to the menus.

### `createViewMenu()`

```java
private JMenu createViewMenu()
```

**Returns:** `JMenu` - The "View" menu with zoom options.

**Description:**
Creates the "View" menu with "Zoom In" and "Zoom Out" options. Configures actions to adjust the font size of the contact table.

### `adjustFontSize(int delta)`

```java
private void adjustFontSize(int delta)
```

**Parameters:**
- `delta`: The change in font size (positive to increase, negative to decrease).

**Description:**
Adjusts the font size of the contact table by the specified delta. Ensures that the font size does not fall below a minimum threshold.

### `createHelpMenu()`

```java
private JMenu createHelpMenu()
```

**Returns:** `JMenu` - The "Help" menu with an "About" item.

**Description:**
Creates the "Help" menu with an "About" menu item. Configures an action to display developer information in a dialog.

### `showAboutDialog()`

```java
private void showAboutDialog()
```

**Description:**
Displays a dialog with information about the developers. Constructs an HTML view with clickable links and uses `JOptionPane` to show the dialog.

### `createAboutTextPane(String[] links, String[] developerNames)`

```java
private JTextPane createAboutTextPane(String[] links, String[] developerNames)
```

**Parameters:**
- `links`: Array of developer contact links.
- `developerNames`: Array of developer names.

**Returns:** `JTextPane` - The text pane containing the HTML content with developer information.

**Description:**
Creates a `JTextPane` with HTML content displaying developer names and links. Sets up a hyperlink listener to handle clicks on the links.

### `handleHyperlinkEvent(HyperlinkEvent event)`

```java
private void handleHyperlinkEvent(HyperlinkEvent event)
```

**Parameters:**
- `event`: The hyperlink event.

**Description:**
Handles hyperlink activation events. Opens the URL in the default web browser and logs any errors encountered.

## Constants

- **`LOGGER`**: Logger instance for logging errors and information.

---

This documentation outlines the `Dashboard` class, detailing its methods, constructor, and constants. It helps developers understand how to use the class and what each method does.