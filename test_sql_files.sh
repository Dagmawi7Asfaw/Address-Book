#!/usr/bin/env bash
set -euo pipefail

# =============================================================================
# Test Script for SQL File Switching
# =============================================================================
#
# This script demonstrates how to switch between different SQL initialization files.
#
# Usage:
#   ./test_sql_files.sh init    # Use init_adressbook.sql (minimal data)
#   ./test_sql_files.sh full    # Use adressbook.sql (extensive data)
#   ./test_sql_files.sh         # Use default (init_adressbook.sql)
#
# =============================================================================

echo "Address Book SQL File Testing Script"
echo "===================================="
echo

case "${1:-}" in
    "init")
        echo "Testing with init_adressbook.sql (minimal sample data)..."
        SQL_FILE="src/main/sql/SQL/init_adressbook.sql" ./run_encrypted.sh
        ;;
    "full")
        echo "Testing with adressbook.sql (extensive sample data)..."
        SQL_FILE="src/main/sql/SQL/adressbook.sql" ./run_encrypted.sh
        ;;
    *)
        echo "Testing with default SQL file (init_adressbook.sql)..."
        echo "Available options:"
        echo "  ./test_sql_files.sh init    # Use init_adressbook.sql (3 contacts)"
        echo "  ./test_sql_files.sh full    # Use adressbook.sql (50+ contacts)"
        echo "  ./test_sql_files.sh         # Use default"
        echo
        ./run_encrypted.sh
        ;;
esac 