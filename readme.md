```markdown
```
# Address Book Application

## Overview

This project is a simple address book application built using Java Swing for the UI and SQL Server for data storage. The application includes features for user login, displaying a dashboard, and managing contacts.

## Features

- **Login Page:** Secure user login with password visibility toggle.
- **Dashboard:** Provides access to contact management functionalities.
- **Contact Management:** Add, update, delete, and view contacts.

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- SQL Server 2019 or later
- JDBC Driver for SQL Server
- An IDE like IntelliJ IDEA or Eclipse

## Setup

### 1. Database Setup

Run the following SQL script to set up the database and initial data:

```sql
-- Create a new database named AddressBook
CREATE DATABASE AddressBook;
GO

-- Switch to the AddressBook database context
USE AddressBook;
GO

-- Create a new table named Contacts
CREATE TABLE Contacts
(
    cid       INT IDENTITY (1,1) PRIMARY KEY,
    firstName NVARCHAR(45) NOT NULL,
    lastName  NVARCHAR(45) NOT NULL,
    location  NVARCHAR(45),
    phone     VARCHAR(20),
    CONSTRAINT chk_phone_format CHECK (phone LIKE '+%'),
    email     VARCHAR(255) NOT NULL,
    createdAt DATETIME DEFAULT GETDATE(),
    updatedAt DATETIME DEFAULT GETDATE(),
    CONSTRAINT unique_contact UNIQUE (firstName, lastName, email)
);
GO

-- Create indexes to improve query performance on the email and location columns
CREATE INDEX idx_email ON Contacts(email);
CREATE INDEX idx_location ON Contacts(location);

-- Insert sample contact data into the Contacts table
INSERT INTO Contacts (firstName, lastName, location, phone, email)
VALUES
    ('Hans', 'Müller', 'Berlin', '+491701234567', 'hans.mueller@example.de'),
    ('Anna', 'Schmidt', 'Munich', '+491701234568', 'anna.schmidt@example.de'),
    ('Lukas', 'Meyer', 'Hamburg', '+491701234569', 'lukas.meyer@example.de');
    -- Add more sample data as needed

GO

-- Retrieve all columns from the Contacts table to verify the data
SELECT * FROM Contacts;
GO
```

### 2. Java Project Setup

1. Clone the repository:
    ```bash
    git clone https://github.com/Mishnah7/addressbook.git
    cd addressbook
    ```

2. Import the project into your IDE.

3. Add the JDBC Driver for SQL Server to your project's classpath:
   - `lib/mssql-jdbc-12.8.0.jre11.jar` (or appropriate version for your JDK)

4. Update the `ConnectionFactory` class with your SQL Server credentials.

5. Build and run the project.

## Usage

1. **Run the Application:**
   - Launch the `LoginPage` class.
   - Enter the username and password (`root`/`root` for testing) to access the dashboard.

2. **Dashboard:**
   - After a successful login, you will be redirected to the dashboard where you can manage contacts.

3. **Toggle Password Visibility:**
   - Use the checkbox on the login page to show or hide the password in the password field.

## Code Structure

- `com.addressbook.UI`:
   - `Dashboard.java`: Provides the main interface for managing and viewing contacts.
   - `LoginPage.java`: Manages user authentication and login interface.

- `com.addressbook.dao`:
   - `ConnectionFactory.java`: Manages the database connection setup.
   - `ContactDAO.java`: Handles CRUD operations for contacts in the database.

- `com.addressbook.logic`:
   - `ContactPage.java`: Handles the display and editing of contact information.

- `com.addressbook.model`:
   - `ContactDTO.java`: Data transfer object for contact information.

- `SQL/addressbook.sql`: Contains SQL scripts for setting up the database and initial data.

## Contributing

Feel free to fork the repository, make improvements, and submit pull requests. For bug reports or feature requests, please open an issue.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements

- The project uses the SQL Server JDBC Driver for database connectivity.
- Thanks to the developers and communities that provide open-source libraries and tools.

## Contact

For questions or feedback, contact [dagmawiasfaw0@gmail.com](dagmawiasfaw0@gmail.com).
```
