#!/usr/bin/env bash
set -euo pipefail

# =============================================================================
# Container Reset Script
# =============================================================================
#
# Use this script when you want to completely reset the database containers.
# This is useful for:
#   - Testing fresh database initialization
#   - Troubleshooting database issues
#   - Switching between different SQL files with fresh data
#
# Usage:
#   ./reset_containers.sh        # Reset both containers
#   ./reset_containers.sh non    # Reset only non-encrypted container
#   ./reset_containers.sh enc    # Reset only encrypted container
#
# =============================================================================

echo "🗑️  Address Book Container Reset Script"
echo "========================================"
echo

case "${1:-}" in
    "non")
        echo "Resetting non-encrypted container (addressbook-sql)..."
        if sudo docker inspect "addressbook-sql" >/dev/null 2>&1; then
            sudo docker stop "addressbook-sql" >/dev/null 2>&1 || true
            sudo docker rm "addressbook-sql" >/dev/null 2>&1 || true
            echo "✅ Non-encrypted container reset complete."
        else
            echo "ℹ️  Non-encrypted container not found."
        fi
        ;;
    "enc")
        echo "Resetting encrypted container (addressbook-sql-encrypted)..."
        if sudo docker inspect "addressbook-sql-encrypted" >/dev/null 2>&1; then
            sudo docker stop "addressbook-sql-encrypted" >/dev/null 2>&1 || true
            sudo docker rm "addressbook-sql-encrypted" >/dev/null 2>&1 || true
            echo "✅ Encrypted container reset complete."
        else
            echo "ℹ️  Encrypted container not found."
        fi
        ;;
    *)
        echo "Resetting both containers..."
        echo
        
        # Reset non-encrypted container
        if sudo docker inspect "addressbook-sql" >/dev/null 2>&1; then
            echo "Stopping and removing addressbook-sql..."
            sudo docker stop "addressbook-sql" >/dev/null 2>&1 || true
            sudo docker rm "addressbook-sql" >/dev/null 2>&1 || true
        fi
        
        # Reset encrypted container
        if sudo docker inspect "addressbook-sql-encrypted" >/dev/null 2>&1; then
            echo "Stopping and removing addressbook-sql-encrypted..."
            sudo docker stop "addressbook-sql-encrypted" >/dev/null 2>&1 || true
            sudo docker rm "addressbook-sql-encrypted" >/dev/null 2>&1 || true
        fi
        
        echo "✅ All containers reset complete."
        echo
        echo "Next time you run a script, it will create fresh containers and databases."
        ;;
esac

echo
echo "📋 Usage:"
echo "  ./run.sh                    # Start with non-encrypted (fast)"
echo "  ./run_encrypted.sh          # Start with encrypted (fast)"
echo "  ./reset_containers.sh       # Reset both containers"
echo "  ./reset_containers.sh non   # Reset only non-encrypted"
echo "  ./reset_containers.sh enc   # Reset only encrypted" 