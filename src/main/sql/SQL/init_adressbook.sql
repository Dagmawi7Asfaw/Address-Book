-- Safe initialization for AddressBook (no DELETE/DROP)
IF DB_ID('AddressBook') IS NULL
BEGIN
    CREATE DATABASE AddressBook;
END
GO

-- Switch to the AddressBook database context
USE AddressBook;
GO

-- Create Contacts table if it doesn't exist
IF OBJECT_ID('dbo.Contacts', 'U') IS NULL
BEGIN
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
    CREATE INDEX idx_email ON Contacts (email);
    CREATE INDEX idx_location ON Contacts (location);
END
GO

-- Create UserSettings table if it doesn't exist
IF OBJECT_ID('dbo.UserSettings', 'U') IS NULL
BEGIN
    CREATE TABLE UserSettings (
        username NVARCHAR(128) NOT NULL PRIMARY KEY,
        theme    NVARCHAR(64)  NOT NULL
    );
END
GO

-- Seed sample data only if table is empty
IF NOT EXISTS (SELECT 1 FROM Contacts)
BEGIN
    INSERT INTO Contacts (firstName, lastName, location, phone, email)
    VALUES ('Hans', 'Müller', 'Berlin', '+491701234567', 'hans.mueller@example.de'),
           ('Anna', 'Schmidt', 'Munich', '+491701234568', 'anna.schmidt@example.de'),
           ('Lukas', 'Meyer', 'Hamburg', '+491701234569', 'lukas.meyer@example.de');
END
GO

-- Seed default theme for the default local user
IF NOT EXISTS (SELECT 1 FROM UserSettings WHERE username = 'root')
BEGIN
    INSERT INTO UserSettings (username, theme) VALUES ('root', 'FlatLightLaf');
END
GO

-- Display results
SELECT TOP 5 * FROM Contacts;
SELECT * FROM UserSettings;
GO 