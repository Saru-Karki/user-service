# pgAdmin Auto-Registration Setup ✅

## What Was Configured

Your docker-compose.yml now automatically registers the PostgreSQL server in pgAdmin when the containers start up.

## Configuration Files

### 1. docker-compose.yml (Updated)
Added to pgAdmin service:
- Volume mapping for server configuration: `./pgadmin-servers.json:/pgadmin4/servers.json`
- Volume for persistent data: `pgadmin-data:/var/lib/pgadmin`
- Server mode disabled for easier access
- Master password requirement disabled

### 2. pgadmin-servers.json (Created)
Auto-registers PostgreSQL server with these settings:
- **Server Name:** Dokotech PostgreSQL
- **Host:** postgres (Docker network hostname)
- **Port:** 5432
- **Database:** userservicedb
- **Username:** dokoadmin
- **Password:** dokoadmin123

## How to Use

### Step 1: Start the Services
```bash
cd /home/sandip/work/DokoTechProjectDocuments/Projects/user-service

# Start all services
docker-compose up -d

# Or rebuild if you made changes
docker-compose up -d --build
```

### Step 2: Access pgAdmin
1. Open browser: `http://localhost:5050`
2. Login with:
   - **Email:** sandipnup1996@gmail.com
   - **Password:** Kathmandu@123

### Step 3: PostgreSQL Server Already Registered!
✅ You'll see "Dokotech PostgreSQL" server automatically in the left panel under "Servers"
✅ Click on it to expand and explore your database
✅ No manual registration needed!

## Server Details

| Setting | Value |
|---------|-------|
| Server Name | Dokotech PostgreSQL |
| Host | postgres |
| Port | 5432 |
| Database | userservicedb |
| Username | dokoadmin |
| Password | dokoadmin123 |

## What You Can Do in pgAdmin

### 1. View Tables
```
Servers → Dokotech PostgreSQL → Databases → userservicedb → Schemas → public → Tables
```

You'll see:
- `users` - User accounts
- `roles` - User roles
- `permissions` - Permissions
- `user_roles` - Many-to-many mapping
- `role_permissions` - Many-to-many mapping

### 2. Query Database
Click on "Query Tool" or press Alt+Shift+Q to run SQL queries:

```sql
-- View all users
SELECT * FROM users;

-- View all roles
SELECT * FROM roles;

-- View users with their roles
SELECT u.username, u.email, r.name as role_name
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id;

-- Create ADMIN role if not exists
INSERT INTO roles (id, name, description, created_at, updated_at)
VALUES (gen_random_uuid(), 'ADMIN', 'Administrator role', NOW(), NOW())
ON CONFLICT DO NOTHING;
```

### 3. Create Test Data

**Create ADMIN Role:**
```sql
INSERT INTO roles (id, name, description, created_at, updated_at)
VALUES (gen_random_uuid(), 'ADMIN', 'Administrator with full access', NOW(), NOW());
```

**Assign ADMIN Role to User:**
```sql
-- First, get the user ID and role ID
SELECT id, username FROM users WHERE username = 'admin';
SELECT id, name FROM roles WHERE name = 'ADMIN';

-- Then assign (replace UUIDs with actual values)
INSERT INTO user_roles (user_id, role_id)
VALUES (
  (SELECT id FROM users WHERE username = 'admin'),
  (SELECT id FROM roles WHERE name = 'ADMIN')
);
```

### 4. View User with Roles
```sql
SELECT 
    u.id,
    u.username,
    u.email,
    u.full_name,
    u.is_active,
    r.name as role_name,
    r.description as role_description
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id
WHERE u.username = 'admin';
```

## Manual Registration (If Needed)

If for some reason auto-registration doesn't work, you can manually add the server:

1. In pgAdmin, right-click "Servers" → "Register" → "Server"
2. **General Tab:**
   - Name: `Dokotech PostgreSQL`
3. **Connection Tab:**
   - Host: `postgres` (or `localhost` if connecting from host machine)
   - Port: `5432`
   - Database: `userservicedb`
   - Username: `dokoadmin`
   - Password: `dokoadmin123`
   - Save Password: ✅ Yes
4. Click "Save"

## Troubleshooting

### Issue: Server not showing in pgAdmin
**Solution:**
```bash
# Stop and remove containers
docker-compose down

# Remove pgAdmin volume to reset
docker volume rm user-service_pgadmin-data

# Start again
docker-compose up -d
```

### Issue: Can't connect to PostgreSQL
**Solution:**
1. Check if PostgreSQL is running:
   ```bash
   docker-compose ps
   ```
2. Check PostgreSQL logs:
   ```bash
   docker-compose logs postgres
   ```
3. Make sure services are on the same network (they are in docker-compose)

### Issue: pgadmin-servers.json not loading
**Solution:**
1. Check file exists:
   ```bash
   ls -la pgadmin-servers.json
   ```
2. Restart pgAdmin:
   ```bash
   docker-compose restart pgadmin
   ```

## Connection from Host Machine

If you want to connect to PostgreSQL from your host machine (not through pgAdmin):

**Using psql:**
```bash
psql -h localhost -p 5432 -U dokoadmin -d userservicedb
# Password: dokoadmin123
```

**Using DBeaver/DataGrip:**
- Host: `localhost`
- Port: `5432`
- Database: `userservicedb`
- Username: `dokoadmin`
- Password: `dokoadmin123`

## Useful Docker Commands

```bash
# View all containers
docker-compose ps

# View logs
docker-compose logs -f postgres
docker-compose logs -f pgadmin
docker-compose logs -f user-service

# Stop all services
docker-compose down

# Stop and remove volumes (clean slate)
docker-compose down -v

# Restart specific service
docker-compose restart pgadmin
docker-compose restart postgres

# Rebuild and start
docker-compose up -d --build
```

## Security Notes

⚠️ **For Development Only!**

The current setup is configured for development convenience:
- Passwords are in plain text
- Server mode is disabled
- No SSL required

**For Production:**
1. Use environment variables for passwords
2. Enable SSL/TLS
3. Use strong passwords
4. Enable server mode in pgAdmin
5. Restrict network access
6. Use secrets management (Docker Secrets, Vault, etc.)

## Summary

✅ **pgAdmin automatically connects to PostgreSQL on startup**
✅ **No manual server registration needed**
✅ **Persistent configuration** (survives container restarts)
✅ **Easy database management** through web UI
✅ **Pre-configured with correct credentials**

**Just run `docker-compose up -d` and access pgAdmin at http://localhost:5050!**

