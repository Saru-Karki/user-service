# User Service API

A comprehensive Spring Boot microservice for managing users, roles, and permissions with a complete CRUD API.

## 🚀 Features

- **User Management**: Full CRUD operations for users
- **Role-Based Access Control (RBAC)**: Manage roles with multiple permissions
- **Permission System**: Fine-grained permission management
- **JWT Authentication**: Secure token-based authentication
- **OAuth2 Social Login**: Login with Google, Facebook, and Apple
- **Swagger/OpenAPI Documentation**: Interactive API documentation at `/swagger-ui.html`
- **Docker Support**: Containerized PostgreSQL database
- **Environment-based Configuration**: Easy deployment across environments

## 📋 Prerequisites

- Java 21
- Maven 3.6+
- Docker & Docker Compose
- PostgreSQL (via Docker)

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.5.7
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Validation**: Jakarta Bean Validation
- **Build Tool**: Maven
- **Utilities**: Lombok

## 📁 Project Structure

```
com.dokotech.platform.userservice/
├── UserServiceApplication.java
├── configs/
│   └── OpenApiConfig.java          # Swagger/OpenAPI configuration
├── controllers/
│   ├── UserController.java
│   ├── RoleController.java
│   ├── PermissionController.java
│   └── apis/                        # API Interfaces with Swagger docs
│       ├── UserApi.java
│       ├── RoleApi.java
│       └── PermissionApi.java
├── services/
│   ├── UserService.java
│   ├── RoleService.java
│   ├── PermissionService.java
│   └── impl/                        # Service Implementations
│       ├── UserServiceImpl.java
│       ├── RoleServiceImpl.java
│       └── PermissionServiceImpl.java
├── repositories/
│   ├── UserRepository.java
│   ├── RoleRepository.java
│   └── PermissionRepository.java
├── models/                          # JPA Entities
│   ├── User.java
│   ├── Role.java
│   └── Permission.java
├── dtos/                            # Data Transfer Objects
│   ├── UserDTO.java
│   ├── RoleDTO.java
│   ├── PermissionDTO.java
│   ├── CreateUserRequest.java
│   ├── CreateRoleRequest.java
│   └── CreatePermissionRequest.java
├── exceptions/
│   ├── GlobalExceptionHandler.java
│   └── ErrorResponse.java
└── utils/                           # Utility classes

```

## 🔧 Configuration

### Application Properties

The application uses environment variables for database configuration:

```properties
# Database Configuration
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/userservicedb}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:dokoadmin}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:dokoadmin123}
```

### Docker Compose Setup

Start the PostgreSQL database:

```bash
docker-compose up -d
```

## 🚀 Running the Application

### 1. Build the project

```bash
mvn clean install
```

### 2. Run with Maven

```bash
mvn spring-boot:run
```

### 3. Run with Docker

```bash
docker-compose up
```

The application will be available at: `http://localhost:8080`

## 📚 API Documentation

Once the application is running, access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

Or view the OpenAPI JSON specification at:

```
http://localhost:8080/v3/api-docs
```

## 🔗 API Endpoints

### Permissions API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/permissions` | Create a new permission |
| GET | `/api/v1/permissions/{id}` | Get permission by ID |
| GET | `/api/v1/permissions` | Get all permissions |
| PUT | `/api/v1/permissions/{id}` | Update permission |
| DELETE | `/api/v1/permissions/{id}` | Delete permission |

### Roles API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/roles` | Create a new role |
| GET | `/api/v1/roles/{id}` | Get role by ID |
| GET | `/api/v1/roles` | Get all roles |
| PUT | `/api/v1/roles/{id}` | Update role |
| DELETE | `/api/v1/roles/{id}` | Delete role |

### Users API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/users` | Create a new user |
| GET | `/api/v1/users/{id}` | Get user by ID |
| GET | `/api/v1/users` | Get all users |
| PUT | `/api/v1/users/{id}` | Update user |
| DELETE | `/api/v1/users/{id}` | Delete user |

## 📝 Example API Requests

### Create a Permission

```bash
curl -X POST http://localhost:8080/api/v1/permissions \
  -H "Content-Type: application/json" \
  -d '{
    "name": "USER_CREATE",
    "description": "Permission to create users"
  }'
```

### Create a Role

```bash
curl -X POST http://localhost:8080/api/v1/roles \
  -H "Content-Type: application/json" \
  -d '{
    "name": "ADMIN",
    "description": "Administrator role",
    "permissionIds": ["<permission-uuid>"]
  }'
```

### Create a User

```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "username": "johndoe",
    "password": "SecurePass123",
    "fullName": "John Doe",
    "roleIds": ["<role-uuid>"]
  }'
```

## 🗄️ Database Schema

### Tables

- **users**: User information
- **roles**: Role definitions
- **permissions**: Permission definitions
- **user_roles**: Many-to-many relationship between users and roles
- **role_permissions**: Many-to-many relationship between roles and permissions

### Relationships

- Users ↔ Roles: Many-to-Many
- Roles ↔ Permissions: Many-to-Many
- Users can have a self-referencing relationship (created_by)

## 🏗️ Design Patterns Used

1. **Interface + Implementation Pattern**: 
   - Service layer separated into interfaces and implementations
   - Controllers implement API interfaces with Swagger documentation

2. **DTO Pattern**: 
   - Separate DTOs for request/response to avoid exposing entity structure

3. **Repository Pattern**: 
   - Spring Data JPA repositories for data access

4. **Builder Pattern**: 
   - Lombok @Builder for clean object construction

## ⚠️ Important Notes

1. **Password Storage**: Passwords are hashed using BCryptPasswordEncoder for secure storage.

2. **Security**: Spring Security is implemented with:
   - JWT-based authentication
   - OAuth2 social login (Google, Facebook, Apple)
   - Role-based access control (RBAC)
   - Method-level security with `@PreAuthorize`

3. **Validation**: Request validation is implemented using Jakarta Bean Validation annotations.

4. **Exception Handling**: Global exception handler catches and formats errors consistently.

5. **OAuth2 Configuration**: Set up OAuth2 credentials in environment variables or `.env` file (see OAUTH2_SETUP.md).

## 🔐 Security Recommendations

Before deploying to production:

1. ✅ Spring Security (implemented)
2. ✅ JWT-based authentication (implemented)
3. ✅ Hash passwords using BCrypt (implemented)
4. ⚠️ Implement rate limiting
5. ⚠️ Add CORS configuration
6. ⚠️ Use HTTPS/SSL
7. ⚠️ Implement audit logging
8. ⚠️ Add input sanitization
9. ⚠️ Rotate JWT secrets regularly
10. ⚠️ Implement refresh token rotation

## 📦 Dependencies

Key dependencies include:

- `spring-boot-starter-web`: REST API support
- `spring-boot-starter-data-jpa`: Database operations
- `spring-boot-starter-validation`: Request validation
- `postgresql`: PostgreSQL driver
- `lombok`: Reduce boilerplate code
- `springdoc-openapi-starter-webmvc-ui`: Swagger/OpenAPI documentation

## 🤝 Contributing

1. Follow Java naming conventions
2. Use plural names for packages (controllers, services, etc.)
3. Write meaningful commit messages
4. Add unit tests for new features
5. Update documentation

## 📄 License

This project is licensed under the MIT License.

## 👥 Contact

For questions or support, contact: support@dokotech.com
1. **Password Storage**: Currently uses plain text with `{noop}` prefix. In production, implement `BCryptPasswordEncoder` for secure password hashing.
