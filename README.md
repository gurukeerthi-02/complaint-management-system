# Complaint System - Spring Boot + React

A full-stack complaint management system with Spring Boot backend and React frontend.

## Architecture

- **Backend**: Spring Boot with PostgreSQL
- **Frontend**: React with TypeScript and Tailwind CSS
- **Authentication**: JWT-based authentication (not done yet)
- **Database**: PostgreSQL with JPA/Hibernate

## Prerequisites

1. **Java 17** or higher
2. **Maven 3.6+**
3. **Node.js 18+** and npm
4. **PostgreSQL 12+**

## Setup Instructions

### 1. Database Setup

Install PostgreSQL and create a database:

```sql
CREATE DATABASE complaint_system;
CREATE USER complaint_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE complaint_system TO complaint_user;
```

### 2. Backend Setup

1. Navigate to the backend directory:
```bash
cd backend
```

2. Update database configuration in `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/complaint_system
    username: complaint_user
    password: your_password
```

3. Install dependencies and run:
```bash
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### 3. Frontend Setup

1. Navigate to the project root directory:
```bash
cd ..
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

The frontend will start on `http://localhost:5173`

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

## Features

- User registration and authentication
- JWT-based security
- Create and manage complaints
- Dashboard with statistics
- Filter and search complaints
- Responsive design
- Real-time status updates

## Environment Variables

You can set the following environment variables:

```bash
DB_USERNAME=your_username
DB_PASSWORD=your_password
JWT_SECRET=your_secret_key
```

## Database Schema

The application automatically creates the following tables:
- `users` - User profiles with authentication
- `complaints` - Complaint records with status tracking

## Development

### Backend Development
- The backend uses Spring Boot with auto-configuration
- JPA entities automatically create database schema
- JWT authentication with configurable secret
- CORS enabled for frontend integration

### Frontend Development
- React with TypeScript for type safety
- Tailwind CSS for styling
- React Router for navigation
- Custom API service replacing Supabase

## Production Deployment

1. Build the frontend:
```bash
npm run build
```

2. Package the backend:
```bash
cd backend
mvn clean package
```

3. Run the JAR file:
```bash
java -jar target/complaint-system-1.0.0.jar
```

## Migration from Supabase

This project has been migrated from Supabase to a custom Spring Boot backend:

- Replaced Supabase client with custom API service
- Migrated authentication to JWT-based system
- Converted database schema to JPA entities
- Updated all components to use new API endpoints

The migration maintains all existing functionality while providing more control over the backend infrastructure.
