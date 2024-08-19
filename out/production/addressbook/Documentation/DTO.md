Here's the full documentation for the `ContactDTO` class:

---

# ContactDTO Class Documentation

## Overview

The `ContactDTO` class represents a Data Transfer Object (DTO) for a contact. It encapsulates the data related to a contact, including its ID, first name, last name, location, phone number, and email address. The class provides getter and setter methods for accessing and modifying these fields.

## Table of Contents

1. [Class Description](#class-description)
2. [Fields](#fields)
3. [Getters and Setters](#getters-and-setters)

## Class Description

The `ContactDTO` class is used to transfer contact data between different layers of the application, such as between the database layer and the business logic layer. It provides a simple structure for storing and retrieving contact information.

## Fields

- `cid`: The unique identifier for the contact.
- `firstName`: The first name of the contact.
- `lastName`: The last name of the contact.
- `location`: The location or address of the contact.
- `phone`: The phone number of the contact.
- `email`: The email address of the contact.

## Getters and Setters

### `getCid()`

```java
public int getCid()
```

Returns the unique identifier for the contact.

**Returns:** `int` - The CID of the contact.

### `setCid(int cid)`

```java
public void setCid(int cid)
```

Sets the unique identifier for the contact.

**Parameters:**
- `cid` - The CID to set.

### `getFirstName()`

```java
public String getFirstName()
```

Returns the first name of the contact.

**Returns:** `String` - The first name of the contact.

### `setFirstName(String firstName)`

```java
public void setFirstName(String firstName)
```

Sets the first name of the contact.

**Parameters:**
- `firstName` - The first name to set.

### `getLastName()`

```java
public String getLastName()
```

Returns the last name of the contact.

**Returns:** `String` - The last name of the contact.

### `setLastName(String lastName)`

```java
public void setLastName(String lastName)
```

Sets the last name of the contact.

**Parameters:**
- `lastName` - The last name to set.

### `getLocation()`

```java
public String getLocation()
```

Returns the location or address of the contact.

**Returns:** `String` - The location of the contact.

### `setLocation(String location)`

```java
public void setLocation(String location)
```

Sets the location or address of the contact.

**Parameters:**
- `location` - The location to set.

### `getPhone()`

```java
public String getPhone()
```

Returns the phone number of the contact.

**Returns:** `String` - The phone number of the contact.

### `setPhone(String phone)`

```java
public void setPhone(String phone)
```

Sets the phone number of the contact.

**Parameters:**
- `phone` - The phone number to set.

### `getEmail()`

```java
public String getEmail()
```

Returns the email address of the contact.

**Returns:** `String` - The email address of the contact.

### `setEmail(String email)`

```java
public void setEmail(String email)
```

Sets the email address of the contact.

**Parameters:**
- `email` - The email address to set.

---
