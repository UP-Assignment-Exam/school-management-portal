# 📘 School Management Portal

A **Spring Boot** application for managing students, classes, schedules, attendance, and user authentication.

---

## ✅ Prerequisites

- **Java 17** or higher
- **Maven**
- **XAMPP** with **MySQL** running locally
- (Optional) IDE like **IntelliJ IDEA** or **Eclipse**

---

## ⚙️ Setup Instructions

### 1. Start MySQL Server

- Open **XAMPP Control Panel**
- Start **MySQL**
- Go to **phpMyAdmin** or use CLI and create the database:

```sql
CREATE DATABASE school_management;
```

---

### 2. Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
# Spring application name
spring.application.name=school-management-portal

# MySQL connection settings
spring.datasource.url=jdbc:mysql://localhost:3306/school_management?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

# JPA / Hibernate settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# File Upload Configuration
file.upload-dir=uploads/
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# Server Configuration
server.port=8080

# Logging Configuration
logging.level.com.school_management_portal=DEBUG
logging.level.org.springframework.security=DEBUG
```

---

### 3. Build and Run the Project

In the terminal at the project root:

```bash
mvn clean install
mvn spring-boot:run
```

Or run via your IDE’s Spring Boot configuration.

---

### 4. Access the Application

- Web App: [http://localhost:8080](http://localhost:8080)
- Login Page: [http://localhost:8080/login](http://localhost:8080/login)
- API Endpoints: As defined in your controller classes

---

### 5. Upload Directory

- Uploaded files/images are stored in the `uploads/` folder
- Create the folder manually in the root if it doesn’t exist
- Files in `uploads/` are served as static resources

---

## 📝 Notes

- Database schema is auto-created and updated via Hibernate
- Passwords are hashed using **BCrypt**
- **Spring Security** handles authentication and role-based access control
- Ensure annotation processing is **enabled** in your IDE for **Lombok**

---

## ❗ Troubleshooting

- **MySQL errors**: Check credentials or if MySQL is running
- **Lombok issues**: Enable annotation processing in IDE settings
- **Port 8080 in use**: Change port in `application.properties`:

```properties
server.port=8080
```

---

## 🧩 Entity Relationship Diagram

```mermaid
erDiagram

    USER {
        LONG id
        STRING username
        STRING password
        ENUM role
        BOOLEAN enabled
        BOOLEAN isDeleted
        DATETIME createdAt
        DATETIME updatedAt
    }

    TEACHER {
        LONG id
        STRING firstName
        STRING lastName
        STRING email
        STRING phoneNumber
        BOOLEAN isActive
        STRING imageUrl
        BOOLEAN isDeleted
        DATETIME createdAt
        DATETIME updatedAt
    }

    STUDENT {
        LONG id
        STRING firstName
        STRING lastName
        STRING email
        DATE dateOfBirth
        STRING gender
        TEXT address
        DATE enrollmentDate
        BOOLEAN isActive
        STRING imageUrl
        BOOLEAN isDeleted
        DATETIME createdAt
        DATETIME updatedAt
    }

    CLASSENTITY {
        LONG id
        STRING name
        STRING description
        DATE startDate
        DATE endDate
        INTEGER maxStudents
        BOOLEAN isDeleted
        DATETIME createdAt
        DATETIME updatedAt
    }

    ATTENDANCE {
        LONG id
        DATE date
        STRING status
        STRING remarks
        BOOLEAN isDeleted
        DATETIME createdAt
        DATETIME updatedAt
    }

    SCHEDULE {
        LONG id
        ENUM dayOfWeek
        TIME startTime
        TIME endTime
        BOOLEAN isDeleted
        DATETIME createdAt
        DATETIME updatedAt
    }

    %% Relationships
    STUDENT ||--o{ ATTENDANCE : has
    CLASSENTITY ||--o{ ATTENDANCE : has
    CLASSENTITY ||--o{ SCHEDULE : has
    TEACHER ||--o{ CLASSENTITY : teaches
    CLASSENTITY }o--o{ STUDENT : includes
```

---

## 📬 Contact

For questions, reach out to **Damon Kert** at **damonkert@gmail.com**