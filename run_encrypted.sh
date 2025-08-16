#!/usr/bin/env bash
set -euo pipefail

# =============================================================================
# Address Book Application Runner Script - WITH ENCRYPTION
# =============================================================================
#
# This script sets up SQL Server with SSL encryption enabled.
# It requires the sqlserver-certs directory with SSL certificates.
#
# To use a different SQL file for database seeding, set the SQL_FILE variable:
#
# Examples:
#   SQL_FILE="sql/custom_init.sql" ./run_encrypted.sh
#   SQL_FILE="sql/test_data.sql" ./run_encrypted.sh
#
# Default SQL file: src/main/resources/sql/init_addressbook.sql
#
# =============================================================================

# Load local config (ignored by git)
if [[ -f "src/main/resources/config.properties" ]]; then
  # shellcheck disable=SC2046
  export $(grep -v '^#' src/main/resources/config.properties | xargs)
fi

# Note: This script overrides DB_URL to use encrypted connection regardless of config file

: "${DB_PASSWORD:?DB_PASSWORD is required. Set it in src/main/resources/config.properties or export it.}"

# Override config for encrypted connection
export DB_URL="jdbc:sqlserver://localhost:1434;databaseName=AddressBook;encrypt=true;trustServerCertificate=true"
export DB_USERNAME="${DB_USERNAME:-sa}"

# SQL file to seed the database (change this variable to use different SQL files)
# Available options:
#   - src/main/sql/SQL/init_adressbook.sql: Safe initialization with minimal sample data (3 contacts)
#   - src/main/sql/SQL/adressbook.sql: Full initialization with extensive sample data (50+ contacts)
SQL_FILE="${SQL_FILE:-src/main/sql/SQL/init_adressbook.sql}"

# Check if SSL certificates exist
if [[ ! -f "sqlserver-certs/server.crt" ]] || [[ ! -f "sqlserver-certs/server.key" ]]; then
  echo "Error: SSL certificates not found in sqlserver-certs directory!"
  echo "Please run the certificate generation script first."
  exit 1
fi

# Ensure SQL Server container is running with encryption
CONTAINER_NAME="addressbook-sql-encrypted"
SA_PASSWORD="$DB_PASSWORD"

# Check if container exists and is running
if sudo docker inspect "$CONTAINER_NAME" >/dev/null 2>&1; then
  if [[ "$(sudo docker inspect -f '{{.State.Running}}' "$CONTAINER_NAME")" == "true" ]]; then
    echo "Container $CONTAINER_NAME is already running."
  else
    echo "Starting existing container $CONTAINER_NAME..."
    sudo docker start "$CONTAINER_NAME" >/dev/null
  fi
else
  echo "Creating new SQL Server container $CONTAINER_NAME with encryption..."
  sudo docker run -d --name "$CONTAINER_NAME" \
    -e 'ACCEPT_EULA=Y' \
    -e "MSSQL_SA_PASSWORD=$SA_PASSWORD" \
    -e "MSSQL_PID=Developer" \
    -p 1434:1433 \
    -v "$PWD/sqlserver-certs:/var/opt/mssql/certs:ro" \
    -v "$PWD/sqlserver-certs/mssql.conf:/var/opt/mssql/mssql.conf:ro" \
    mcr.microsoft.com/mssql/server:2022-latest >/dev/null
fi

# Wait for readiness
echo "Waiting for SQL Server to be ready..."
for i in {1..60}; do
  if sudo docker run --rm --network host mcr.microsoft.com/mssql-tools \
    /opt/mssql-tools/bin/sqlcmd -S 127.0.0.1,1434 -U sa -P "$SA_PASSWORD" -Q "SELECT 1" >/dev/null 2>&1; then
    READY=1; break
  fi
  sleep 2
done

if [[ "${READY:-0}" != "1" ]]; then
  echo "SQL Server did not become ready in time." >&2
  exit 1
fi

echo "SQL Server is ready with encryption enabled!"

# Initialize database only if needed (check if database exists)
if [[ -f "$SQL_FILE" ]]; then
  echo "Checking if database needs initialization..."
  if sudo docker run --rm --network host mcr.microsoft.com/mssql-tools \
    /opt/mssql-tools/bin/sqlcmd -S 127.0.0.1,1434 -U sa -P "$SA_PASSWORD" -Q "SELECT name FROM sys.databases WHERE name = 'AddressBook'" 2>/dev/null | grep -q "AddressBook"; then
    echo "Database already exists. Skipping initialization."
  else
    echo "Initializing database with SQL file: $SQL_FILE..."
    # Extract directory and filename for proper volume mounting
    SQL_DIR=$(dirname "$SQL_FILE")
    SQL_FILENAME=$(basename "$SQL_FILE")
    
    sudo docker run --rm --network host -v "$PWD/$SQL_DIR:/sql:ro" mcr.microsoft.com/mssql-tools \
      /opt/mssql-tools/bin/sqlcmd -S 127.0.0.1,1434 -U sa -P "$SA_PASSWORD" -i "/sql/$SQL_FILENAME" >/dev/null
  fi
else
  echo "SQL file not found: $SQL_FILE"
  echo "Database initialization skipped."
fi

# Build management - always recompile if source files are newer than classes
if [[ ! -d out/production/addressbook ]]; then
  mkdir -p out/production/addressbook
fi

# Check if we need to recompile
NEED_RECOMPILE=false

if ! find out/production/addressbook -type f -name '*.class' | grep -q .; then
  NEED_RECOMPILE=true
  echo "No compiled classes found. Compiling..."
else
  NEWEST_CLASS=$(find out/production/addressbook -type f -name '*.class' -printf '%T@ %p\n' | sort -n | tail -1 | cut -d' ' -f2-)
  NEWEST_SOURCE=$(find src/main/java -name "*.java" -printf '%T@ %p\n' | sort -n | tail -1 | cut -d' ' -f2-)
  
  if [[ -n "$NEWEST_CLASS" && -n "$NEWEST_SOURCE" ]]; then
    if [[ "$NEWEST_SOURCE" -nt "$NEWEST_CLASS" ]]; then
      NEED_RECOMPILE=true
      echo "Source files modified. Recompiling..."
    fi
  else
    NEED_RECOMPILE=true
    echo "Unable to determine file timestamps. Recompiling..."
  fi
fi

if [[ "$NEED_RECOMPILE" == "true" ]]; then
  echo "Compiling Java source files..."
  find src/main/java -name "*.java" > /tmp/sources.list
  javac --release 11 -d out/production/addressbook -cp "lib/*" @/tmp/sources.list
  echo "Compilation completed."
else
  echo "No recompilation needed. Using existing classes."
fi

# Run
echo "Starting Address Book application with encrypted database connection..."
exec java -cp "out/production/addressbook:lib/*" com.addressbook.UI.LoginPage 