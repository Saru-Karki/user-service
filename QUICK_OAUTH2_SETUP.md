# Quick OAuth2 Setup Guide

## 🔑 Getting Google OAuth2 Credentials

### Step 1: Go to Google Cloud Console
Visit: https://console.cloud.google.com/

### Step 2: Create a Project
1. Click the project dropdown at the top
2. Click "New Project"
3. Name it: "User Service OAuth" (or any name)
4. Click "Create"

### Step 3: Enable OAuth Consent Screen
1. In the left menu: **APIs & Services** → **OAuth consent screen**
2. Choose **External** (for testing)
3. Fill in required fields:
   - App name: "User Service"
   - User support email: your email
   - Developer contact: your email
4. Click **Save and Continue**
5. Skip "Scopes" → **Save and Continue**
6. Add test users (your own email) → **Save and Continue**

### Step 4: Create OAuth2 Credentials
1. In the left menu: **APIs & Services** → **Credentials**
2. Click **Create Credentials** → **OAuth 2.0 Client ID**
3. Application type: **Web application**
4. Name: "User Service Web Client"
5. **Authorized redirect URIs**: Click **Add URI**
   - Add: `http://localhost:8080/login/oauth2/code/google`
6. Click **Create**
7. **COPY** the Client ID and Client Secret shown in the popup

### Example of What You'll Get:
```
Client ID: 123456789012-abcdefghijklmnopqrstuvwxyz1234.apps.googleusercontent.com
Client Secret: GOCSPX-ABcd1234EFgh5678IJkl
```

These are **NOT** your Gmail password! These are app credentials.

---

## 📘 Getting Facebook OAuth2 Credentials

### Step 1: Go to Facebook Developers
Visit: https://developers.facebook.com/

### Step 2: Create an App
1. Click **My Apps** → **Create App**
2. Choose **Consumer** as app type
3. App Name: "User Service"
4. Click **Create App**

### Step 3: Add Facebook Login
1. From dashboard: **Add Product**
2. Find **Facebook Login** → Click **Set Up**
3. Choose **Web**
4. Enter Site URL: `http://localhost:8080`

### Step 4: Configure OAuth Redirect URIs
1. Left menu: **Facebook Login** → **Settings**
2. **Valid OAuth Redirect URIs**: Add
   - `http://localhost:8080/login/oauth2/code/facebook`
3. Click **Save Changes**

### Step 5: Get App Credentials
1. Left menu: **Settings** → **Basic**
2. **App ID** = Your Client ID
3. **App Secret** = Your Client Secret (click "Show" to reveal)

### Example:
```
App ID (Client ID): 1234567890123456
App Secret: a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6
```

---

## 🍎 Getting Apple Sign In Credentials (Advanced)

Apple is more complex. For now, you can **skip Apple** and just use Google/Facebook.

If you need Apple later:
- Go to: https://developer.apple.com/
- Requires Apple Developer Account ($99/year)
- Follow detailed setup in OAUTH2_SETUP.md

---

## 📝 How to Use These Credentials

### Option 1: Create .env File (Recommended)

Create a file named `.env` in the same folder as `docker-compose.yml`:

```bash
# Google OAuth2 Credentials
GOOGLE_CLIENT_ID=123456789012-abcdefghijklmnopqrstuvwxyz1234.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=GOCSPX-ABcd1234EFgh5678IJkl

# Facebook OAuth2 Credentials  
FACEBOOK_CLIENT_ID=1234567890123456
FACEBOOK_CLIENT_SECRET=a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6

# You can leave Apple commented out if you don't have credentials
# APPLE_CLIENT_ID=your-apple-service-id
# APPLE_CLIENT_SECRET=your-apple-jwt-token
```

### Option 2: Set Environment Variables

```bash
export GOOGLE_CLIENT_ID="your-actual-client-id"
export GOOGLE_CLIENT_SECRET="your-actual-client-secret"
```

### Then Restart Docker:

```bash
docker-compose down
docker-compose up -d
```

---

## ✅ Testing OAuth2 Login

1. Go to: http://localhost:8080/swagger-ui.html
2. Navigate to OAuth2 section
3. Click on `/api/oauth2/login-urls` to see available OAuth2 providers
4. Click on the Google URL: `/oauth2/authorization/google`
5. You'll be redirected to Google's login page
6. After login, you'll be redirected back with a JWT token

---

## ⚠️ Important Security Notes

1. **NEVER commit .env file to Git** (it's already in .gitignore)
2. **NEVER share your Client Secret publicly**
3. These are **app credentials**, not your personal password
4. Each OAuth2 provider credentials are separate from your personal account
5. Users will log in with THEIR own Google/Facebook accounts, not yours

---

## 🆘 Troubleshooting

**"OAuth client was not found"**
- You're still using placeholder values
- Make sure .env file exists and has real credentials
- Restart docker-compose after adding credentials

**"Redirect URI mismatch"**
- Make sure you added `http://localhost:8080/login/oauth2/code/google` exactly as shown
- No trailing slash
- Check for typos

**"This app isn't verified"**
- Normal for development
- Click "Advanced" → "Go to User Service (unsafe)"
- For production, you need to verify your app with Google

