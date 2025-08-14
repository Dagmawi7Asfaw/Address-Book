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