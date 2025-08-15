Here's a full documentation for the `ContactPage` class:

---

# ContactPage Class Documentation

## Overview

The `ContactPage` class is a Swing-based user interface component designed to manage contacts. It allows users to add, edit, delete, and search for contacts. The class utilizes a `ContactDAO` for database operations and presents contact information in a `JTable`.

## Table of Contents

1. [Class Description](#class-description)
2. [Constructor](#constructor)
3. [Methods](#methods)
    - [createFormPanel()](#createformpanel)
    - [createTablePanel()](#createtablepanel)
    - [filter()](#filter)
    - [createButton(String text, Color color)](#createbuttonstring-text-color-color)
    - [configureLabel(JLabel label)](#configurelabeljlabel-label)
    - [addFocusListeners()](#addfocuslisteners)
    - [isValidPhone(String phone)](#isvalidphonestring-phone)
    - [isValidEmail(String email)](#isvalidemailstring-email)
    - [showMessage(String message, String title, int messageType)](#showmessagestring-message-string-title-int-messageType)
    - [loadContacts()](#loadcontacts)
    - [clearFields()](#clearfields)
    - [handleAddContact()](#handleaddcontact)
    - [isDuplicateContact()](#isduplicatecontact)
    - [handleEditContact()](#handleeditcontact)
    - [validateFields()](#validatefields)
    - [createContactFromFields()](#createcontactfromfields)
    - [handleDeleteContact()](#handledeletecontact)
    - [areFieldsEmpty()](#arefieldsempty)
4. [Constants](#constants)

## Class Description

The `ContactPage` class is a Swing JPanel that manages the contact information. It includes a form for inputting contact details and a table for displaying the contact list. The class provides methods to interact with the contact data, validate inputs, and manage UI components.

## Constructor

```java
public ContactPage()
```

Initializes the `ContactPage` instance by setting up the user interface, including form and table panels. It also initializes the `ContactDAO` and loads the contact data.

## Methods

### `createFormPanel()`

```java
private JPanel createFormPanel()
```

Creates and configures the form panel containing input fields for contact details and action buttons (Add, Edit, Delete). The panel uses manual positioning with `null` layout.

**Returns:** `JPanel` - The configured form panel.

### `createTablePanel()`

```java
private JPanel createTablePanel()
```

Creates and configures the table panel, which includes the `JTable` for displaying contacts and a search field. It sets up table appearance, sorting, and filtering.

**Returns:** `JPanel` - The configured table panel.

### `filter()`

```java
private void filter()
```

Applies the search filter to the table based on the text entered in the search field.

### `createButton(String text, Color color)`

```java
private JButton createButton(String text, Color color)
```

Creates a `JButton` with the specified text and background color.

**Parameters:**
- `text` - The text to display on the button.
- `color` - The background color of the button.

**Returns:** `JButton` - The created button.

### `configureLabel(JLabel label)`

```java
private void configureLabel(JLabel label)
```

Configures the label with font and color settings.

**Parameters:**
- `label` - The `JLabel` to configure.

### `addFocusListeners()`

```java
private void addFocusListeners()
```

Adds focus listeners to the phone and email input fields to validate their contents on focus loss.

### `isValidPhone(String phone)`

```java
private boolean isValidPhone(String phone)
```

Validates the phone number format using E.164 format.

**Parameters:**
- `phone` - The phone number to validate.

**Returns:** `boolean` - `true` if the phone number is valid; `false` otherwise.

### `isValidEmail(String email)`

```java
private boolean isValidEmail(String email)
```

Validates the email address format.

**Parameters:**
- `email` - The email address to validate.

**Returns:** `boolean` - `true` if the email address is valid; `false` otherwise.

### `showMessage(String message, String title, int messageType)`

```java
private void showMessage(String message, String title, int messageType)
```

Displays a message dialog with the specified content.

**Parameters:**
- `message` - The message to display.
- `title` - The title of the dialog.
- `messageType` - The type of message (e.g., `JOptionPane.INFORMATION_MESSAGE`).

### `loadContacts()`

```java
private void loadContacts()
```

Loads contacts from the database and populates the table.

### `clearFields()`

```java
private void clearFields()
```

Clears all input fields and resets the selected contact ID.

### `handleAddContact()`

```java
private void handleAddContact()
```

Handles the process of adding a new contact. Validates input fields, checks for duplicates, and updates the table and database.

### `isDuplicateContact()`

```java
private boolean isDuplicateContact()
```

Checks if the current contact information already exists in the table.

**Returns:** `boolean` - `true` if a duplicate contact is found; `false` otherwise.

### `handleEditContact()`

```java
private void handleEditContact()
```

Handles the process of editing an existing contact. Validates input fields, updates the selected contact, and refreshes the table.

### `validateFields()`

```java
private boolean validateFields()
```

Validates input fields for completeness and correct formatting.

**Returns:** `boolean` - `true` if validation fails; `false` otherwise.

### `createContactFromFields()`

```java
private ContactDTO createContactFromFields()
```

Creates a `ContactDTO` object from the input fields.

**Returns:** `ContactDTO` - The created contact object.

### `handleDeleteContact()`

```java
private void handleDeleteContact()
```

Handles the process of deleting selected contacts from the table and database.

### `areFieldsEmpty()`

```java
private boolean areFieldsEmpty()
```

Checks if any of the input fields are empty.

**Returns:** `boolean` - `true` if any field is empty; `false` otherwise.

## Constants

- `LABEL_FONT`: Font settings for labels (Arial, Bold, 14).
- `DARK_BLUE`: Color used for label text (RGB: 0, 0, 128).
- `BUTTON_ADD_COLOR`: Background color for the Add button (Green, RGB: 0, 128, 0).
- `BUTTON_EDIT_COLOR`: Background color for the Edit button (Blue, RGB: 0, 0, 255).
- `BUTTON_DELETE_COLOR`: Background color for the Delete button (Red, RGB: 255, 0, 0).
- `TABLE_BACKGROUND_COLOR`: Background color for the table (Light gray, RGB: 210, 210, 210).
- `TABLE_GRID_COLOR`: Color for table grid lines (RGB: 150, 0, 10).
- `TABLE_HEADER_COLOR`: Background color for table header (Dark blue, RGB: 0, 102, 204).
- `FORM_BACKGROUND_COLOR`: Background color for the form panel (Light gray, RGB: 210, 210, 210).

---
