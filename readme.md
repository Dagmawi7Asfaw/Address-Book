# Address Book Application

## Overview

This project is a simple address book application built using Java Swing for the UI and SQL Server for data storage. The application includes features for user login, displaying a dashboard, and managing contacts.

## Features

- **Login Page:** Secure user login with password visibility toggle.
- **Dashboard:** Provides access to contact management functionalities.
- **Contact Management:** Add, update, delete, and view contacts.

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- Docker (for running SQL Server locally)
- SQL Server JDBC Driver (already included under `addressbook/lib/`)
- An IDE like IntelliJ IDEA or VS Code (optional)

## Quick start (Docker + CLI)

1) Start SQL Server in Docker

```bash
export SA_PASSWORD='REDACTED'
sudo docker run -d --name addressbook-sql \
  -e 'ACCEPT_EULA=Y' -e "MSSQL_SA_PASSWORD=$SA_PASSWORD" \
  -p 1433:1433 mcr.microsoft.com/mssql/server:2022-latest
```

Wait until it is ready (optional):

```bash
until sudo docker run --rm --network host mcr.microsoft.com/mssql-tools \
  /opt/mssql-tools/bin/sqlcmd -S 127.0.0.1,1433 -U sa -P "$SA_PASSWORD" -Q "SELECT 1" >/dev/null 2>&1; do
  sleep 2
done
```

2) Initialize the database

Use the provided safe initializer (creates `AddressBook`, `Contacts`, and `UserSettings` if missing):

```bash
sudo docker run --rm --network host -v "$PWD/addressbook/SQL:/sql:ro" mcr.microsoft.com/mssql-tools \
  /opt/mssql-tools/bin/sqlcmd -S 127.0.0.1,1433 -U sa -P "$SA_PASSWORD" -d master -i /sql/init_adressbook.sql
```

Alternatively, you can run the more extensive sample data script (verify and remove any destructive statements first):

```bash
sudo docker run --rm --network host -v "$PWD/addressbook/SQL:/sql:ro" mcr.microsoft.com/mssql-tools \
  /opt/mssql-tools/bin/sqlcmd -S 127.0.0.1,1433 -U sa -P "$SA_PASSWORD" -d master -i /sql/adressbook.sql
```

3) Build

```bash
mkdir -p out/production/addressbook
find addressbook/src -name "*.java" > /tmp/sources.list
javac --release 11 -d out/production/addressbook -cp "addressbook/lib/*" @/tmp/sources.list
```

4) Run

```bash
java -cp "out/production/addressbook:addressbook/lib/*" com.addressbook.UI.LoginPage
```

- Default login for testing: username `root`, password `root`.
- The app reads DB config from environment variables (see Configuration).

## Java Project Setup (IDE)

- Import as a Java project in IntelliJ IDEA or VS Code.
- Libraries under `addressbook/lib/` include FlatLaf and the SQL Server JDBC driver.
- If needed for VS Code, `.vscode/settings.json` references `addressbook/lib/**/*.jar`.

## Database scripts

- `addressbook/SQL/init_adressbook.sql` – safe initializer (creates tables if missing, seeds minimal data)
- `addressbook/SQL/adressbook.sql` – sample dataset (review before use; comment out DELETE/DROP if present)

## Configuration (local, not committed)

The app reads DB settings from environment variables if present:

- `DB_URL` (default: `jdbc:sqlserver://localhost:1433;databaseName=AddressBook;encrypt=false`)
- `DB_USERNAME` (default: `sa`)
- `DB_PASSWORD` (default: `REDACTED`)

Alternatively, copy the sample file:

```bash
cp addressbook/config.sample.properties addressbook/config.properties
# edit values
export $(grep -v '^#' addressbook/config.properties | xargs)
```

`.env`, `addressbook/config.properties`, and other local config files are ignored by git.

## Code Structure

- `com.addressbook.UI`:
  - `Dashboard.java`: Provides the main interface for managing and viewing contacts.
  - `LoginPage.java`: Manages user authentication and login interface.

- `com.addressbook.dao`:
  - `ConnectionFactory.java`: Manages the database connection setup.
  - `ContactDAO.java`: Handles CRUD operations for contacts in the database.
  - `ThemeDAO.java`: Persists and loads UI theme settings.

- `com.addressbook.logic`:
  - `ContactPage.java`: Handles the display and editing of contact information.

- `com.addressbook.model`:
  - `ContactDTO.java`: Data transfer object for contact information.

## License

This project is licensed under the MIT License. See the LICENSE file for details.
