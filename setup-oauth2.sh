#!/bin/bash

# OAuth2 Setup Helper Script
# This script helps you set up OAuth2 credentials for your User Service

echo "============================================"
echo "  OAuth2 Setup Helper"
echo "============================================"
echo ""

# Check if .env already exists
if [ -f ".env" ]; then
    echo "⚠️  .env file already exists!"
    echo ""
    read -p "Do you want to overwrite it? (y/N): " -n 1 -r
    echo ""
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "Cancelled. Edit your existing .env file manually."
        exit 0
    fi
fi

# Copy template
if [ -f ".env.template" ]; then
    cp .env.template .env
    echo "✅ Created .env file from template"
else
    echo "❌ Error: .env.template not found!"
    exit 1
fi

echo ""
echo "============================================"
echo "  Let's set up OAuth2 credentials!"
echo "============================================"
echo ""
echo "You need to get credentials from OAuth2 providers."
echo "See GET_OAUTH2_CREDENTIALS_GUIDE.md for detailed instructions."
echo ""

# Google setup
echo "----------------------------------------"
echo "🔵 Google OAuth2 Setup"
echo "----------------------------------------"
read -p "Do you have Google OAuth2 credentials? (y/N): " -n 1 -r
echo ""
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo ""
    echo "Get credentials from: https://console.cloud.google.com/"
    echo ""
    read -p "Enter Google Client ID: " google_id
    read -p "Enter Google Client Secret: " google_secret

    # Add to .env
    echo "" >> .env
    echo "# Google OAuth2 Credentials (Added by setup script)" >> .env
    echo "GOOGLE_CLIENT_ID=$google_id" >> .env
    echo "GOOGLE_CLIENT_SECRET=$google_secret" >> .env
    echo "✅ Google credentials added to .env"
else
    echo "⏭️  Skipped Google setup"
    echo "   To add later: See GET_OAUTH2_CREDENTIALS_GUIDE.md"
fi

echo ""

# Facebook setup
echo "----------------------------------------"
echo "🔵 Facebook OAuth2 Setup"
echo "----------------------------------------"
read -p "Do you have Facebook OAuth2 credentials? (y/N): " -n 1 -r
echo ""
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo ""
    echo "Get credentials from: https://developers.facebook.com/"
    echo ""
    read -p "Enter Facebook App ID: " facebook_id
    read -p "Enter Facebook App Secret: " facebook_secret

    # Add to .env
    echo "" >> .env
    echo "# Facebook OAuth2 Credentials (Added by setup script)" >> .env
    echo "FACEBOOK_CLIENT_ID=$facebook_id" >> .env
    echo "FACEBOOK_CLIENT_SECRET=$facebook_secret" >> .env
    echo "✅ Facebook credentials added to .env"
else
    echo "⏭️  Skipped Facebook setup"
    echo "   To add later: See GET_OAUTH2_CREDENTIALS_GUIDE.md"
fi

echo ""

# Apple setup
echo "----------------------------------------"
echo "🍎 Apple Sign In Setup (Optional)"
echo "----------------------------------------"
echo "⚠️  Apple requires paid developer account ($99/year)"
echo "⚠️  Apple setup is complex (JWT generation required)"
echo ""
read -p "Do you want to set up Apple? (y/N): " -n 1 -r
echo ""
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo ""
    echo "Get credentials from: https://developer.apple.com/"
    echo ""
    read -p "Enter Apple Service ID (Client ID): " apple_id
    read -p "Enter Apple Client Secret (JWT token): " apple_secret

    # Add to .env
    echo "" >> .env
    echo "# Apple OAuth2 Credentials (Added by setup script)" >> .env
    echo "APPLE_CLIENT_ID=$apple_id" >> .env
    echo "APPLE_CLIENT_SECRET=$apple_secret" >> .env
    echo "✅ Apple credentials added to .env"
else
    echo "⏭️  Skipped Apple setup (recommended for initial testing)"
fi

echo ""
echo "============================================"
echo "  Setup Complete!"
echo "============================================"
echo ""
echo "Next steps:"
echo "1. Review your .env file: nano .env"
echo "2. Restart docker: docker-compose down && docker-compose up -d"
echo "3. Wait 15-20 seconds for app to start"
echo "4. Test Google: http://localhost:8080/oauth2/authorization/google"
echo "5. Test Facebook: http://localhost:8080/oauth2/authorization/facebook"
echo ""
echo "For detailed instructions, see:"
echo "  - GET_OAUTH2_CREDENTIALS_GUIDE.md (complete guide)"
echo "  - OAUTH2_CHECKLIST.md (quick checklist)"
echo ""
echo "❓ Need help getting credentials?"
echo "   See GET_OAUTH2_CREDENTIALS_GUIDE.md for step-by-step instructions"
echo ""

