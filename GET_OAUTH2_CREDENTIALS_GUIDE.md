# 🔑 Complete Guide: Getting OAuth2 Credentials

This guide will walk you through getting **Client ID** and **Client Secret** from Google, Facebook, and Apple.

---

## 🔵 GOOGLE OAUTH2 CREDENTIALS

### What You'll Get:
- **Client ID**: Looks like `123456789012-abcdefghijklmnop.apps.googleusercontent.com`
- **Client Secret**: Looks like `GOCSPX-AbCdEf123456`

### Step-by-Step Instructions:

#### Step 1: Go to Google Cloud Console
1. Open your browser
2. Go to: **https://console.cloud.google.com/**
3. Sign in with ANY Google account (can be your personal Gmail)

#### Step 2: Create a New Project
1. At the top of the page, click the **Project Dropdown** (says "Select a project")
2. In the popup, click **"NEW PROJECT"** (top right)
3. Enter Project Name: `UserService` (or any name you want)
4. Leave Organization as default
5. Click **"CREATE"**
6. Wait a few seconds for the project to be created
7. The project will automatically be selected

#### Step 3: Configure OAuth Consent Screen
1. In the left sidebar, click **"APIs & Services"**
2. Click **"OAuth consent screen"**
3. Choose **"External"** (this allows anyone to log in)
4. Click **"CREATE"**
5. Fill in the required fields:
   - **App name**: `User Service` (or your app name)
   - **User support email**: Select your email from dropdown
   - **Developer contact email**: Enter your email
6. Scroll down and click **"SAVE AND CONTINUE"**
7. On "Scopes" page, just click **"SAVE AND CONTINUE"** (skip this)
8. On "Test users" page, click **"+ ADD USERS"**
9. Add your email address (so you can test)
10. Click **"ADD"**, then **"SAVE AND CONTINUE"**
11. Review the summary and click **"BACK TO DASHBOARD"**

#### Step 4: Create OAuth 2.0 Credentials
1. In the left sidebar, click **"Credentials"**
2. At the top, click **"+ CREATE CREDENTIALS"**
3. Select **"OAuth client ID"**
4. For "Application type", select **"Web application"**
5. For "Name", enter: `User Service Web Client`
6. Scroll down to **"Authorized redirect URIs"**
7. Click **"+ ADD URI"**
8. Enter EXACTLY: `http://localhost:8080/login/oauth2/code/google`
   - ⚠️ **Important**: No trailing slash, must be exact!
9. Click **"CREATE"**

#### Step 5: Copy Your Credentials
1. A popup will appear with your credentials
2. **Copy the "Client ID"** - it looks like: `123456789012-abc...def.apps.googleusercontent.com`
3. **Copy the "Client secret"** - it looks like: `GOCSPX-AbCdEf123456`
4. Click **"OK"**

✅ **You now have Google OAuth2 credentials!**

---

## 🔵 FACEBOOK OAUTH2 CREDENTIALS

### What You'll Get:
- **App ID** (this is your Client ID): Looks like `1234567890123456`
- **App Secret** (this is your Client Secret): Looks like `a1b2c3d4e5f6g7h8i9j0`

### Step-by-Step Instructions:

#### Step 1: Go to Facebook Developers
1. Open your browser
2. Go to: **https://developers.facebook.com/**
3. Sign in with your Facebook account
4. If asked, register as a developer (free, just accept terms)

#### Step 2: Create a New App
1. At the top right, click **"My Apps"**
2. Click **"Create App"** button (green button)
3. Choose use case: **"Consumer"** (for allowing users to log in)
4. Click **"Next"**
5. Enter details:
   - **App name**: `User Service` (or your app name)
   - **App contact email**: Your email
6. Click **"Create app"**
7. You may need to verify your account (enter password or 2FA code)
8. Wait for the app to be created

#### Step 3: Add Facebook Login Product
1. You'll see the app dashboard
2. Scroll down to **"Add products to your app"** section
3. Find **"Facebook Login"** card
4. Click **"Set up"** button on that card
5. Choose platform: **"Web"** (globe icon)
6. For "Site URL", enter: `http://localhost:8080`
7. Click **"Save"** then **"Continue"**
8. Skip through the quickstart (click Next until done)

#### Step 4: Configure Valid OAuth Redirect URIs
1. In the left sidebar, find **"Facebook Login"**
2. Click **"Settings"** under it
3. Scroll down to **"Valid OAuth Redirect URIs"**
4. In the text box, enter: `http://localhost:8080/login/oauth2/code/facebook`
   - ⚠️ **Important**: No trailing slash, must be exact!
5. Click **"Save Changes"** at the bottom

#### Step 5: Get Your App Credentials
1. In the left sidebar, click **"Settings"** → **"Basic"**
2. You'll see:
   - **App ID**: This is your `FACEBOOK_CLIENT_ID` (copy it)
   - **App Secret**: Click **"Show"** button, enter your Facebook password
   - Copy the App Secret - this is your `FACEBOOK_CLIENT_SECRET`

#### Step 6: Make App Live (Important!)
1. At the top of the page, you'll see a toggle that says **"In development"**
2. For testing purposes, leave it in development mode
3. To use in production, you'll need to switch to "Live" mode later

✅ **You now have Facebook OAuth2 credentials!**

---

## 🍎 APPLE SIGN IN CREDENTIALS (ADVANCED - OPTIONAL)

⚠️ **Apple is significantly more complex than Google/Facebook**

### Requirements:
- Apple Developer Account (**$99/year** - paid membership required)
- More technical setup (need to generate JWT tokens)

### Why Apple is Harder:
1. Requires paid developer account
2. Client Secret is NOT a simple string - you must generate a JWT token using a private key
3. JWT expires every 6 months and must be regenerated
4. More configuration steps

### If You Don't Have Apple Developer Account:
**Skip Apple for now** and just use Google + Facebook. You can add Apple later if needed.

### If You Want to Set Up Apple:

#### Step 1: Apple Developer Account
1. Go to: **https://developer.apple.com/**
2. Sign in with your Apple ID
3. Enroll in Apple Developer Program ($99/year)
4. Wait for approval (can take 24-48 hours)

#### Step 2: Create an App ID
1. Go to: **https://developer.apple.com/account/**
2. Click **"Certificates, Identifiers & Profiles"**
3. Click **"Identifiers"** in the sidebar
4. Click the **"+"** button
5. Select **"App IDs"** → Continue
6. Select **"App"** → Continue
7. Fill in:
   - **Description**: `User Service App`
   - **Bundle ID**: `com.yourcompany.userservice` (must be unique)
8. Scroll down and check **"Sign in with Apple"**
9. Click **"Continue"** → **"Register"**

#### Step 3: Create a Services ID (This is your Client ID)
1. Click **"Identifiers"** → **"+"** button
2. Select **"Services IDs"** → Continue
3. Fill in:
   - **Description**: `User Service Web`
   - **Identifier**: `com.yourcompany.userservice.web` (this is your CLIENT_ID)
4. Check **"Sign in with Apple"**
5. Click **"Configure"** next to Sign in with Apple
6. Select your App ID from Step 2
7. Under "Website URLs", click **"+"**
8. Add:
   - **Domains**: `localhost` (for development)
   - **Return URLs**: `http://localhost:8080/login/oauth2/code/apple`
9. Click **"Save"** → **"Continue"** → **"Register"**

#### Step 4: Create a Key (For Client Secret Generation)
1. Click **"Keys"** in sidebar → **"+"** button
2. **Key Name**: `Sign in with Apple Key`
3. Check **"Sign in with Apple"**
4. Click **"Configure"** → Select your App ID
5. Click **"Save"** → **"Continue"** → **"Register"**
6. Click **"Download"** - save the `.p8` file (you can only download once!)
7. Note your **Key ID** (10 characters, like `ABC1234DEF`)

#### Step 5: Get Your Team ID
1. Go to: **https://developer.apple.com/account/**
2. On the right side, find **"Membership details"**
3. Copy your **Team ID** (10 characters, like `XYZ9876ABC`)

#### Step 6: Generate Client Secret (Complex!)
Apple requires a JWT token as the client secret. You need to:
1. Use your Team ID
2. Use your Key ID  
3. Use the private key from the `.p8` file
4. Generate a JWT token that expires in 6 months

This requires programming/scripting. You can:
- Use online JWT generators (search "Apple Sign In JWT generator")
- Write a script in Node.js/Python/Java
- Use tools like `jose` or `jwt.io`

**Because this is complex, I recommend skipping Apple for now.**

✅ **Apple setup complete** (if you went through all steps)

---

## 📝 WHERE TO PUT THESE VALUES

### Option 1: Create a .env File (Recommended)

Create a file named `.env` in your project root:

```bash
cd /home/sandip/work/DokoTechProjectDocuments/Projects/user-service
nano .env
```

Add your credentials:

```bash
# Google OAuth2 Credentials
GOOGLE_CLIENT_ID=123456789012-abcdefghijklmnop.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=GOCSPX-AbCdEf123456

# Facebook OAuth2 Credentials  
FACEBOOK_CLIENT_ID=1234567890123456
FACEBOOK_CLIENT_SECRET=a1b2c3d4e5f6g7h8i9j0k1l2

# Apple OAuth2 Credentials (optional - skip if too complex)
# APPLE_CLIENT_ID=com.yourcompany.userservice.web
# APPLE_CLIENT_SECRET=your-generated-jwt-token
```

**Save the file** (Ctrl+O, Enter, Ctrl+X in nano)

### Option 2: Add to docker-compose.yml

Edit `docker-compose.yml` and replace the environment variables:

```yaml
environment:
  # ... other variables ...
  GOOGLE_CLIENT_ID: "123456789012-abcdefghijklmnop.apps.googleusercontent.com"
  GOOGLE_CLIENT_SECRET: "GOCSPX-AbCdEf123456"
  FACEBOOK_CLIENT_ID: "1234567890123456"
  FACEBOOK_CLIENT_SECRET: "a1b2c3d4e5f6g7h8i9j0k1l2"
```

⚠️ **Don't commit this to Git!** Credentials should stay private.

---

## 🚀 RESTART YOUR APPLICATION

After adding credentials:

```bash
cd /home/sandip/work/DokoTechProjectDocuments/Projects/user-service
docker-compose down
docker-compose up -d
```

Wait 15-20 seconds for the app to start.

---

## ✅ TEST YOUR OAUTH2 LOGIN

### Test in Browser:

1. **Google Login**: Go to `http://localhost:8080/oauth2/authorization/google`
   - Should redirect to Google login page
   - Log in with YOUR Google account
   - Should redirect back with a JWT token

2. **Facebook Login**: Go to `http://localhost:8080/oauth2/authorization/facebook`
   - Should redirect to Facebook login page
   - Log in with YOUR Facebook account
   - Should redirect back with a JWT token

### Test in Swagger:

1. Go to: `http://localhost:8080/swagger-ui.html`
2. Find the OAuth2 section
3. Try the `/api/oauth2/login-urls` endpoint to see available providers

---

## ❓ TROUBLESHOOTING

### "OAuth client was not found"
- ✅ Check that you copied the correct Client ID
- ✅ Check that credentials are in .env file or docker-compose.yml
- ✅ Restart docker-compose after adding credentials
- ✅ Make sure .env is in the same directory as docker-compose.yml

### "Redirect URI mismatch"
- ✅ Check that you added EXACT redirect URI: `http://localhost:8080/login/oauth2/code/google`
- ✅ No trailing slash
- ✅ Use `http://` not `https://` for localhost
- ✅ Port must be `8080`

### "This app isn't verified" (Google)
- This is normal for development
- Click "Advanced" → "Go to User Service (unsafe)"
- For production, you need to submit for verification

### Facebook: "URL Blocked: This redirect failed"
- Check Valid OAuth Redirect URIs in Facebook Login settings
- Must be exact: `http://localhost:8080/login/oauth2/code/facebook`

---

## 🎉 SUCCESS!

Once you've added real credentials and restarted, OAuth2 login will work!

You can now:
- ✅ Let users log in with Google
- ✅ Let users log in with Facebook  
- ✅ Let users log in with Apple (if you set it up)
- ✅ Still use JWT login (`/api/auth/login`) for username/password

---

## 📚 RESOURCES

- **Google OAuth2 Setup**: https://console.cloud.google.com/
- **Facebook App Setup**: https://developers.facebook.com/
- **Apple Developer**: https://developer.apple.com/
- **Google OAuth2 Docs**: https://developers.google.com/identity/protocols/oauth2
- **Facebook Login Docs**: https://developers.facebook.com/docs/facebook-login
- **Apple Sign In Docs**: https://developer.apple.com/sign-in-with-apple/

---

## 🔒 SECURITY REMINDERS

1. ✅ Never commit `.env` to Git (it's in `.gitignore`)
2. ✅ Never share your Client Secret publicly
3. ✅ These are APP credentials, not your personal passwords
4. ✅ Users log in with THEIR OWN accounts, not yours
5. ✅ For production, use environment variables, not hardcoded values
6. ✅ Rotate credentials if they're ever exposed
7. ✅ Use HTTPS in production (required by OAuth2 providers)

---

**Need help?** If you get stuck:
1. Check the error message carefully
2. Verify your redirect URIs are exact
3. Make sure credentials are in the right place (.env file)
4. Restart docker-compose after changes
5. Check docker logs: `docker logs dokotech-user-service`

