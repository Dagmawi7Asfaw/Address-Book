#!/usr/bin/env bash
set -euo pipefail

# Load local config (ignored by git)
if [[ -f "addressbook/config.properties" ]]; then
  # shellcheck disable=SC2046
  export $(grep -v '^#' addressbook/config.properties | xargs)
fi

: "${DB_PASSWORD:?DB_PASSWORD is required. Set it in addressbook/config.properties or export it.}"

# Optional defaults
export DB_URL="${DB_URL:-jdbc:sqlserver://localhost:1433;databaseName=AddressBook;encrypt=false}"
export DB_USERNAME="${DB_USERNAME:-sa}"

# Ensure SQL Server container is running (uses DB_PASSWORD as SA password)
CONTAINER_NAME="addressbook-sql"
SA_PASSWORD="$DB_PASSWORD"

# Create container if missing
if ! sudo docker inspect "$CONTAINER_NAME" >/dev/null 2>&1; then
  echo "Creating SQL Server container $CONTAINER_NAME..."
  sudo docker run -d --name "$CONTAINER_NAME" \
    -e 'ACCEPT_EULA=Y' -e "MSSQL_SA_PASSWORD=$SA_PASSWORD" \
    -p 1433:1433 mcr.microsoft.com/mssql/server:2022-latest >/dev/null
fi

# Start if stopped
if [[ "$(sudo docker inspect -f '{{.State.Running}}' "$CONTAINER_NAME")" != "true" ]]; then
  echo "Starting SQL Server container $CONTAINER_NAME..."
  sudo docker start "$CONTAINER_NAME" >/dev/null
fi

# Wait for readiness
echo "Waiting for SQL Server to be ready..."
for i in {1..60}; do
  if sudo docker run --rm --network host mcr.microsoft.com/mssql-tools \
    /opt/mssql-tools/bin/sqlcmd -S 127.0.0.1,1433 -U sa -P "$SA_PASSWORD" -Q "SELECT 1" >/dev/null 2>&1; then
    READY=1; break
  fi
  sleep 2
done
if [[ "${READY:-0}" != "1" ]]; then
  echo "SQL Server did not become ready in time." >&2
  exit 1
fi

# Initialize database (idempotent safe script)
if [[ -f "src/main/resources/sql/init_addressbook.sql" ]]; then
  echo "Initializing database (safe script)..."
  sudo docker run --rm --network host -v "$PWD/src/main/resources/sql:/sql:ro" mcr.microsoft.com/mssql-tools \
    /opt/mssql-tools/bin/sqlcmd -S 127.0.0.1,1433 -U sa -P "$SA_PASSWORD" -d master -i /sql/init_addressbook.sql >/dev/null
fi

# Build if classes are missing
if [[ ! -d out/production/addressbook ]]; then
  mkdir -p out/production/addressbook
fi
if ! find out/production/addressbook -type f -name '*.class' | grep -q .; then
  find addressbook/src -name "*.java" > /tmp/sources.list
  javac --release 11 -d out/production/addressbook -cp "addressbook/lib/*" @/tmp/sources.list
fi

# Run
exec java -cp "out/production/addressbook:addressbook/lib/*" com.addressbook.UI.LoginPage 