-- Create a new database named AddressBook
CREATE DATABASE AddressBook;
GO

-- Switch to the AddressBook database context
USE AddressBook;
GO

-- Create a new table named Contacts
CREATE TABLE Contacts
(
    -- Primary key column that auto-increments with each new entry
    cid       INT IDENTITY (1,1) PRIMARY KEY,

    -- Column for the contact's first name, cannot be NULL
    firstName NVARCHAR(45) NOT NULL,

    -- Column for the contact's last name, cannot be NULL
    lastName  NVARCHAR(45) NOT NULL,

    -- Column for the contact's location (e.g., city or address)
    location  NVARCHAR(45),

    -- Column for the contact's phone number with a check constraint to ensure it starts with '+'
    phone     VARCHAR(20),
    CONSTRAINT chk_phone_format CHECK (phone LIKE '+%'),

    -- Column for the contact's email address, cannot be NULL
    email     VARCHAR(255) NOT NULL,

    -- Column to track when the contact was created, defaults to the current date and time
    createdAt DATETIME DEFAULT GETDATE(),

    -- Column to track the last update time, defaults to the current date and time
    updatedAt DATETIME DEFAULT GETDATE(),

    -- Unique constraint to ensure no duplicate contacts with the same first name, last name, and email
    CONSTRAINT unique_contact UNIQUE (firstName, lastName, email)
);
GO

-- Create indexes to improve query performance on the email and location columns
CREATE INDEX idx_email ON Contacts (email);
CREATE INDEX idx_location ON Contacts (location);

-- Insert sample contact data into the Contacts table
INSERT INTO Contacts (firstName, lastName, location, phone, email)
VALUES ('Hans', 'Müller', 'Berlin', '+491701234567', 'hans.mueller@example.de'),
       ('Anna', 'Schmidt', 'Munich', '+491701234568', 'anna.schmidt@example.de'),
       ('Lukas', 'Meyer', 'Hamburg', '+491701234569', 'lukas.meyer@example.de'),
       ('Laura', 'Fischer', 'Cologne', '+491701234570', 'laura.fischer@example.de'),
       ('Maximilian', 'Weber', 'Frankfurt', '+491701234571', 'maximilian.weber@example.de'),
       ('Sophie', 'Schneider', 'Stuttgart', '+491701234572', 'sophie.schneider@example.de'),
       ('Felix', 'Wagner', 'Düsseldorf', '+491701234573', 'felix.wagner@example.de'),
       ('Mia', 'Braun', 'Bremen', '+491701234574', 'mia.braun@example.de'),
       ('Paul', 'Hoffmann', 'Leipzig', '+491701234575', 'paul.hoffmann@example.de'),
       ('Emma', 'Schulz', 'Dresden', '+491701234576', 'emma.schulz@example.de'),
       ('John', 'Doe', 'New York', '+12125551234', 'john.doe@example.com'),
       ('Jane', 'Smith', 'Los Angeles', '+13105555678', 'jane.smith@example.com'),
       ('Robert', 'Johnson', 'Chicago', '+13125558765', 'robert.johnson@example.com'),
       ('Emily', 'Davis', 'Houston', '+17135554321', 'emily.davis@example.com'),
       ('Michael', 'Brown', 'Phoenix', '+16025556789', 'michael.brown@example.com'),
       ('Sarah', 'Wilson', 'Philadelphia', '+12155557890', 'sarah.wilson@example.com'),
       ('David', 'Lee', 'San Antonio', '+12105553456', 'david.lee@example.com'),
       ('Laura', 'Martinez', 'San Diego', '+16195552345', 'laura.martinez@example.com'),
       ('James', 'Taylor', 'Dallas', '+12145556780', 'james.taylor@example.com'),
       ('Olivia', 'Anderson', 'San Jose', '+14085553450', 'olivia.anderson@example.com'),
       ('William', 'Miller', 'San Francisco', '+14155551234', 'william.miller@example.com'),
       ('Ava', 'Garcia', 'Seattle', '+12065555678', 'ava.garcia@example.com'),
       ('Ethan', 'Martinez', 'Boston', '+16175558765', 'ethan.martinez@example.com'),
       ('Mia', 'Hernandez', 'Denver', '+13035554321', 'mia.hernandez@example.com'),
       ('Alexander', 'Wilson', 'Austin', '+15125556789', 'alexander.wilson@example.com'),
       ('Sophia', 'Lopez', 'Jacksonville', '+19045552345', 'sophia.lopez@example.com'),
       ('Benjamin', 'Taylor', 'Columbus', '+16145553456', 'benjamin.taylor@example.com'),
       ('Isabella', 'Anderson', 'Indianapolis', '+13175556780', 'isabella.anderson@example.com'),
       ('Lucas', 'Thomas', 'Fort Worth', '+18175557890', 'lucas.thomas@example.com'),
       ('Charlotte', 'Jackson', 'Charlotte', '+17045554321', 'charlotte.jackson@example.com'),
       ('Henry', 'White', 'San Francisco', '+14155552345', 'henry.white@example.com'),
       ('Amelia', 'Harris', 'Seattle', '+12065556789', 'amelia.harris@example.com'),
       ('James', 'Clark', 'Boston', '+16175553456', 'james.clark@example.com'),
       ('Harper', 'Lewis', 'Denver', '+13035557890', 'harper.lewis@example.com'),
       ('Owen', 'Walker', 'Austin', '+15125551234', 'owen.walker@example.com'),
       ('Ella', 'Young', 'Jacksonville', '+19045555678', 'ella.young@example.com'),
       ('Jack', 'Allen', 'Columbus', '+16145558765', 'jack.allen@example.com'),
       ('Chloe', 'King', 'Indianapolis', '+13175554321', 'chloe.king@example.com'),
       ('Daniel', 'Scott', 'Fort Worth', '+18175556789', 'daniel.scott@example.com'),
       ('Grace', 'Adams', 'Charlotte', '+17045551234', 'grace.adams@example.com'),
       ('Liam', 'Baker', 'San Francisco', '+14155555678', 'liam.baker@example.com'),
       ('Abebe', 'Tadesse', 'Addis Ababa', '+251912345678', 'abebe.tadesse@example.com'),
       ('Marta', 'Yohannes', 'Addis Ababa', '+251911234567', 'marta.yohannes@example.com'),
       ('Kebede', 'Girma', 'Addis Ababa', '+251913456789', 'kebede.girma@example.com'),
       ('Selam', 'Tesfaye', 'Addis Ababa', '+251915678901', 'selam.tesfaye@example.com'),
       ('Daniel', 'Abraham', 'Addis Ababa', '+251917890123', 'daniel.abraham@example.com'),
       ('Alem', 'Fikremariam', 'Addis Ababa', '+251918901234', 'alem.fikremariam@example.com'),
       ('Hana', 'Mesfin', 'Bahir Dar', '+251912345679', 'hana.mesfin@example.com'),
       ('Mulu', 'Tiruneh', 'Mekelle', '+251919876543', 'mulu.tiruneh@example.com'),
       ('Dawit', 'Kebede', 'Gondar', '+251911987654', 'dawit.kebede@example.com'),
       ('Saba', 'Biruk', 'Awasa', '+251918765432', 'saba.biruk@example.com');
GO


-- Retrieve all columns from the Contacts table to verify the data
SELECT *
FROM Contacts;
GO

-- Delete all rows from the Contacts table
DELETE
FROM Contacts;
GO

-- Retrieve specific columns to confirm the table is empty after deletion
SELECT firstName, lastName, createdAt, updatedAt
FROM Contacts;
GO

-- Drop the Contacts table from the database (commented out to prevent accidental execution)
DROP TABLE Contacts;
GO

-- Drop the AddressBook database (commented out to prevent accidental execution)
DROP DATABASE AddressBook;
GO
