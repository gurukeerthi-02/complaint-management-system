# Complaint System Backend - Spring Boot

## Prerequisites

1. **Java 17** or higher
2. **Maven 3.6+**
3. **PostgreSQL 12+**

## Database Setup

1. Install PostgreSQL and create a database:
```sql
CREATE DATABASE complaint_system;
```

2. Create a user (optional):
```sql
CREATE USER complaint_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE complaint_system TO complaint_user;
```

## Configuration

1. Update `src/main/resources/application.yml` with your database credentials:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/complaint_system
    username: your_username
    password: your_password
```

2. Set environment variables (optional):
```bash
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export JWT_SECRET=your_secret_key
```

## Running the Application

1. Navigate to the backend directory:
```bash
cd backend
```

2. Install dependencies and run:
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/signup` - User registration
- `POST /api/auth/login` - User login

### Complaints
- `GET /api/complaints` - Get all complaints
- `GET /api/complaints/my` - Get user's complaints
- `POST /api/complaints` - Create new complaint
- `PUT /api/complaints/{id}/status` - Update complaint status
- `PUT /api/complaints/{id}/upvote` - Upvote complaint

## Database Schema

The application will automatically create the following tables:
- `users` - User profiles
- `complaints` - Complaint records

## CORS Configuration

The backend is configured to accept requests from `http://localhost:5173` (Vite dev server).