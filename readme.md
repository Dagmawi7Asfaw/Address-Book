# Address Book Application

A Java Swing-based address book application with SQL Server database backend, Docker containerization, and modern UI themes.

## Features

- **Modern UI**: FlatLaf themes with customizable appearance
- **Database**: SQL Server with Docker containerization
- **Security**: Environment-based configuration (no hardcoded credentials)
- **Automation**: One-command setup and run scripts
- **Performance**: Smart container management for fast startup
- **Responsive Design**: Fullscreen startup with responsive layouts

## Quick Start

You can run the application using either encrypted or non-encrypted connections **without modifying the config file**.

### Non-Encrypted Connection (Development/Testing)

```bash
./run.sh
```

- Uses port 1433
- No SSL certificates required
- **Fast startup** (5-10 seconds after first run)
- Simpler setup

### Encrypted Connection (Production/Security)

```bash
./run_encrypted.sh
```

- Uses port 1434
- Requires SSL certificates
- **Fast startup** (5-10 seconds after first run)
- Production-ready

## Project Structure

```
Address-Book/
├── src/
│   ├── main/
│   │   ├── java/                    # Java source code
│   │   │   └── com/addressbook/
│   │   │       ├── dao/             # Data Access Objects
│   │   │       ├── logic/           # Business Logic
│   │   │       ├── model/           # Data Models
│   │   │       ├── UI/              # User Interface
│   │   │       └── utils/           # Utility Classes
│   │   ├── resources/               # Configuration files
│   │   │   └── config.properties
│   │   └── sql/                     # Database scripts
│   │       └── SQL/                 # SQL initialization files
│   └── test/                        # Test files (placeholder)
├── lib/                             # External libraries
├── out/                             # Compiled classes
├── sqlserver-certs/                 # SSL certificates for encryption
├── run.sh                           # Non-encrypted runner
├── run_encrypted.sh                 # Encrypted runner
├── test_sql_files.sh                # SQL file testing
├── reset_containers.sh              # Container reset
├── setup_encryption.sh              # SSL certificate setup
└── addressbook.iml                  # IntelliJ IDEA project file
```

## Performance Optimizations

### Smart Container Management

- **First run**: Creates containers and databases (30-60 seconds)
- **Subsequent runs**: Reuses existing containers (5-10 seconds)
- **Automatic optimization**: Scripts check if containers are running
- **Smart database initialization**: Only initializes if database doesn't exist

### When You Need Fresh Start

```bash
# Reset both containers for fresh start
./reset_containers.sh

# Reset only non-encrypted container
./reset_containers.sh non

# Reset only encrypted container
./reset_containers.sh enc
```

## Configuration

The `config.properties` file only needs authentication settings:

```properties
DB_PASSWORD=your-password-here
DB_USERNAME=sa
```

**Note**: The `DB_URL` is not needed in the config file because each script automatically sets the appropriate connection settings:

- `./run.sh` → non-encrypted (port 1433)
- `./run_encrypted.sh` → encrypted (port 1434)

## SQL File Options

Both scripts support different SQL initialization files:

### Minimal Data (Default)

```bash
./run.sh
# or
./run_encrypted.sh
```

Uses: `src/main/sql/SQL/init_adressbook.sql` (3 sample contacts)

### Extensive Data

```bash
SQL_FILE="src/main/sql/SQL/adressbook.sql" ./run.sh
# or
SQL_FILE="src/main/sql/SQL/adressbook.sql" ./run_encrypted.sh
```

Uses: `src/main/sql/SQL/adressbook.sql` (50+ sample contacts)

## Development Workflow

### Daily Development (Fast)

```bash
./run.sh  # 5-10 seconds startup
```

### Testing Different Data Sets

```bash
# Test with minimal data
./test_sql_files.sh init

# Test with extensive data  
./test_sql_files.sh full

# Test with custom SQL file
SQL_FILE="path/to/custom.sql" ./run.sh
```

### Fresh Start Testing

```bash
# Reset and start fresh
./reset_containers.sh
./run.sh
```

### Production Setup

```bash
# Setup encryption (one-time) - generates SSL certificates
./setup_encryption.sh

# Run with encryption
./run_encrypted.sh
```

**Note**: SSL certificates are generated locally and not committed to version control for security reasons.

## IDE Setup

### Prerequisites

- Java 21+
- Docker (for SQL Server)
- Git

### IntelliJ IDEA

- Import as existing project
- The `addressbook.iml` file is already configured
- Libraries and source paths are pre-configured

### VS Code/Cursor

- Install Java Extension Pack
- Project will auto-detect source paths

## Login Credentials

- **Username**: `root`
- **Password**: Any (local development)

## Troubleshooting

### Port Conflicts

- Non-encrypted uses port 1433
- Encrypted uses port 1434
- Make sure the ports are available

### SSL Certificates (Encrypted Only)

- Required for `run_encrypted.sh`
- Must be in `sqlserver-certs/` directory
- Run `./setup_encryption.sh` to generate certificates

### Container Issues

- Use `./reset_containers.sh` to reset containers
- Check container status: `sudo docker ps`
- View container logs: `sudo docker logs addressbook-sql`

### Performance Issues

- **Slow startup**: First run takes 30-60 seconds, subsequent runs are 5-10 seconds
- **Container conflicts**: Use `./reset_containers.sh` to clean up
- **Database issues**: Reset containers for fresh database

### Build Issues

- Ensure Java 11+: `java -version`
- Check classpath: `echo $CLASSPATH`
- Clean and rebuild: `rm -rf out/ && ./run.sh`

## Script Summary

| Script | Purpose | When to Use |
|--------|---------|-------------|
| `./run.sh` | Non-encrypted startup | Daily development |
| `./run_encrypted.sh` | Encrypted startup | Production/security |
| `./test_sql_files.sh` | SQL file testing | Testing different data |
| `./reset_containers.sh` | Container reset | Fresh start/troubleshooting |
| `./setup_encryption.sh` | SSL setup | One-time encryption setup |

## Code Structure

- `com.addressbook.UI`:
  - `Dashboard.java`: Main interface for managing contacts
  - `LoginPage.java`: User authentication and theme selection
  - `components/ContactPage.java`: Contact table and form management
- `com.addressbook.dao`: Data access layer
- `com.addressbook.utils`: Theme utilities and helpers
