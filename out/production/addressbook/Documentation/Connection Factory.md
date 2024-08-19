Here is the comprehensive documentation for the `ConnectionFactory` class:

---

# ConnectionFactory Class Documentation

## Overview

The `ConnectionFactory` class is responsible for creating and managing database connections to a SQL Server database. It handles the initialization of the JDBC driver, establishes a connection to the database, and provides a method to retrieve the connection. The class utilizes logging to track connection status and errors.

## Table of Contents

1. [Class Description](#class-description)
2. [Constructor](#constructor)
3. [Methods](#methods)
    - [ConnectionFactory()](#connectionfactory)
    - [getConnection()](#getconnection)
4. [Constants](#constants)

## Class Description

The `ConnectionFactory` class facilitates database connections by encapsulating the details of connection setup and management. It ensures that a valid connection is available for database operations and handles exceptions related to database connectivity. The class uses JDBC to connect to a Microsoft SQL Server database.

## Constructor

### `ConnectionFactory()`

```java
public ConnectionFactory()
```

**Description:**
Initializes a new instance of the `ConnectionFactory` class. The constructor attempts to load the JDBC driver and establish a connection to the database.

**Throws:**
- `RuntimeException` - If the driver class cannot be found or a SQL exception occurs during connection initialization.

## Methods

### `getConnection()`

```java
public Connection getConnection()
```

**Returns:** `Connection` - A valid connection to the database.

**Description:**
Retrieves a connection to the database. If the current connection is null or closed, it re-establishes a new connection. Logs the connection status and errors.

**Throws:**
- `RuntimeException` - If an error occurs while obtaining the connection.

## Constants

- **`DRIVER`**: `"com.microsoft.sqlserver.jdbc.SQLServerDriver"` - The JDBC driver class for SQL Server.
- **`URL`**: `"jdbc:sqlserver://localhost:1433;databaseName=AddressBook;encrypt=false"` - The JDBC URL for connecting to the SQL Server database.
- **`USERNAME`**: `"sa"` - The username used for database authentication.
- **`PASSWORD`**: `"REDACTED"` - The password used for database authentication (should be securely managed in a production environment).
- **`LOGGER`**: `Logger.getLogger(ConnectionFactory.class.getName())` - Logger instance for recording connection-related messages.

---

This documentation outlines the purpose and functionality of the `ConnectionFactory` class, providing clear descriptions of its constructor, methods, and constants. It aims to assist developers in understanding and utilizing the class effectively.