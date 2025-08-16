#!/usr/bin/env bash
set -euo pipefail

echo "🔐 Setting up SQL Server encryption..."

# Create certificates directory
mkdir -p sqlserver-certs
cd sqlserver-certs

echo "📜 Generating SSL certificate and private key..."

# Generate self-signed certificate
openssl req -x509 -newkey rsa:4096 -keyout server.key -out server.crt -days 365 -nodes \
  -subj "/C=US/ST=State/L=City/O=Organization/CN=localhost"

echo "✅ SSL certificate generated successfully!"
echo "📁 Files created:"
echo "   - server.crt (SSL certificate)"
echo "   - server.key (Private key)"

# Set proper permissions
chmod 600 server.key
chmod 644 server.crt

echo ""
echo "🔧 Next steps:"
echo "1. Update your addressbook/config.properties with:"
echo "   DB_URL=jdbc:sqlserver://localhost:1433;databaseName=AddressBook;encrypt=true;trustServerCertificate=true"
echo ""
echo "2. Run the encrypted version:"
echo "   ./run_encrypted.sh"
echo ""
echo "3. Or use the regular version (no encryption):"
echo "   ./run.sh"
echo ""
echo "🎉 Encryption setup complete!" 