Here's the full documentation for the `ContactDAO` class:

---

# ContactDAO Class Documentation

## Overview

The `ContactDAO` class is responsible for data access operations related to contacts. It interacts with the database to perform CRUD (Create, Read, Update, Delete) operations on contact records. The class uses a `ConnectionFactory` to obtain database connections and logs errors using `Logger`.

## Table of Contents

1. [Class Description](#class-description)
2. [Constructor](#constructor)
3. [Methods](#methods)
    - [contactExists(String firstName, String lastName, String email)](#contactexistssring-firstName-string-lastName-string-email)
    - [addContact(ContactDTO contact)](#addcontactcontactdto-contact)
    - [updateContact(ContactDTO contact)](#updatecontactcontactdto-contact)
    - [deleteContact(int cid)](#deletecontactint-cid)
    - [getAllContacts()](#getallcontacts)
4. [Constants](#constants)

## Class Description

The `ContactDAO` class provides methods for accessing and manipulating contact data in a database. It performs operations such as checking for the existence of a contact, adding a new contact, updating an existing contact, deleting a contact, and retrieving all contacts.

## Constructor

```java
public ContactDAO()
```

Initializes the `ContactDAO` instance by creating a new `ConnectionFactory` for managing database connections.

## Methods

### `contactExists(String firstName, String lastName, String email)`

```java
public boolean contactExists(String firstName, String lastName, String email)
```

Checks if a contact with the specified first name, last name, and email exists in the database.

**Parameters:**
- `firstName` - The first name of the contact.
- `lastName` - The last name of the contact.
- `email` - The email of the contact.

**Returns:** `boolean` - `true` if the contact exists; `false` otherwise.

### `addContact(ContactDTO contact)`

```java
public int addContact(ContactDTO contact)
```

Adds a new contact to the database using the provided `ContactDTO` object.

**Parameters:**
- `contact` - The `ContactDTO` object containing contact details.

**Returns:** `int` - The number of rows affected (always returns 0 in the current implementation).

### `updateContact(ContactDTO contact)`

```java
public void updateContact(ContactDTO contact)
```

Updates an existing contact in the database with the details from the provided `ContactDTO` object.

**Parameters:**
- `contact` - The `ContactDTO` object containing updated contact details.

### `deleteContact(int cid)`

```java
public void deleteContact(int cid)
```

Deletes the contact with the specified CID from the database.

**Parameters:**
- `cid` - The CID of the contact to delete.

### `getAllContacts()`

```java
public List<ContactDTO> getAllContacts()
```

Retrieves all contacts from the database and returns them as a list of `ContactDTO` objects.

**Returns:** `List<ContactDTO>` - A list of `ContactDTO` objects representing all contacts.

## Constants

- `LOGGER`: Logger instance for logging errors and other information.

---

