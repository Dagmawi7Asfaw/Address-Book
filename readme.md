# Address Book Application

A Java Swing-based address book application with SQL Server database backend, Docker containerization, and modern UI themes.

## Features

- **Modern UI**: FlatLaf themes with customizable appearance
- **Database**: SQL Server with Docker containerization
- **Security**: Environment-based configuration (no hardcoded credentials)
- **Automation**: One-command setup and run script
- **Responsive Design**: Fullscreen startup with responsive layouts

## Quick Start (Docker + CLI)

1. **Setup Database**:

```bash
# Set your SQL Server password
export SA_PASSWORD="your-strong-password"

# Run SQL Server container
sudo docker run -d --name addressbook-sql \
  -e 'ACCEPT_EULA=Y' -e "MSSQL_SA_PASSWORD=$SA_PASSWORD" \
  -p 1433:1433 mcr.microsoft.com/mssql/server:2022-latest
```

2. **Initialize Database**:

```bash
# Wait for SQL Server to be ready, then initialize
sudo docker run --rm --network host mcr.microsoft.com/mssql-tools \
  /opt/mssql-tools/bin/sqlcmd -S 127.0.0.1,1433 -U sa -P "$SA_PASSWORD" -d master -i addressbook/SQL/init_adressbook.sql
```

3. **Build**:

```bash
# Compile Java source
find addressbook/src -name "*.java" > /tmp/sources.list
javac --release 11 -d out/production/addressbook -cp "addressbook/lib/*" @/tmp/sources.list
```

4. **Run**:

```bash
# Provide DB_PASSWORD at runtime (mandatory)
export DB_PASSWORD="$SA_PASSWORD"
# Optional overrides (defaults shown)
export DB_URL="jdbc:sqlserver://localhost:1433;databaseName=AddressBook;encrypt=false"
export DB_USERNAME="sa"

# Launch application
java -cp "out/production/addressbook:addressbook/lib/*" com.addressbook.UI.LoginPage
```

## Quick Run (Automated)

Use the provided script for automated setup:

```bash
# Copy sample config and customize
cp addressbook/config.sample.properties addressbook/config.properties
# Edit addressbook/config.properties and set your DB_PASSWORD

# Run (handles Docker, DB init, build, and launch)
./run.sh
```

## Java Project Setup (IDE)

### Prerequisites

- Java 11+
- Docker (for SQL Server)
- Git

### IDE Configuration

**VS Code/Cursor**:

- Install Java Extension Pack
- Project will auto-detect source paths

**IntelliJ IDEA**:

- Import as existing project
- Add `addressbook/lib/*.jar` to project libraries
- Set source root to `addressbook/src`

## Configuration (local, not committed)

Create `addressbook/config.properties` from the sample:

```properties
# Database configuration
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=AddressBook;encrypt=false
DB_USERNAME=sa
DB_PASSWORD=your-actual-password-here
```

**Important**:

- `DB_PASSWORD` is **required** and has no default
- This file is gitignored for security
- Use `config.sample.properties` as a template

## Database Scripts

- `addressbook/SQL/init_adressbook.sql`: Safe initialization (creates DB, tables, seeds data)
- `addressbook/SQL/adressbook.sql`: Original schema (contains destructive operations)

## Code Structure

- `com.addressbook.UI`:
  - `Dashboard.java`: Main interface for managing contacts
  - `LoginPage.java`: User authentication and theme selection
  - `components/ContactPage.java`: Contact table and form management
- `com.addressbook.dao`: Data access layer
- `com.addressbook.utils`: Theme utilities and helpers

## Development

### Building

```bash
# Clean build
rm -rf out/
find addressbook/src -name "*.java" > /tmp/sources.list
javac --release 11 -d out/production/addressbook -cp "addressbook/lib/*" @/tmp/sources.list
```

### Running

```bash
# Load config and run
source addressbook/config.properties
java -cp "out/production/addressbook:addressbook/lib/*" com.addressbook.UI.LoginPage
```

## Login Credentials

- **Username**: `root`
- **Password**: Any (local development)

## Troubleshooting

### Docker Issues

- Ensure Docker is running: `sudo systemctl start docker`
- Add user to docker group: `sudo usermod -aG docker $USER`
- Restart shell: `newgrp docker`

### Database Connection

- Verify SQL Server container is running: `sudo docker ps`
- Check logs: `sudo docker logs addressbook-sql`
- Test connection: `sudo docker exec -it addressbook-sql /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P "$DB_PASSWORD"`

### Build Issues

- Ensure Java 11+: `java -version`
- Check classpath: `echo $CLASSPATH`
- Clean and rebuild: `rm -rf out/ && ./run.sh`
