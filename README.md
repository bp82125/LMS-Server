# LMS-Server

This repository contains the backend service for a Library Management System (LMS), built with Kotlin, Spring Boot, and MySQL.

Check out [LMS-Client](https://github.com/bp82125/LMS-Client)

## Tech Stack

- **Backend Framework:** Spring Boot
- **Language:** Kotlin
- **Database:** MySQL
- **Database Migrations:** Flyway
- **Authentication:** OAuth2 Resource Server
- **Dependencies:**
  - Spring Data JPA
  - Jackson (for JSON handling)
  - Spring Boot Validation
  - JUnit 5, MockK for testing

## Getting Started

### Prerequisites

- JDK 17 (or higher)
- MySQL
- Gradle (to build the project)
- A MySQL database configured and running

### Clone the Repository

```bash
git clone https://github.com/bp82125/LMS-Server.git
cd LMS-Server
```

### Set Up the Database

Ensure MySQL is running, and create a new database for the application. You can use tools like phpMyAdmin or MySQL Workbench.

For example:

```sql
CREATE DATABASE lms;
```

### Configure the Application

In your `src/main/resources/application.yaml`, ensure the database and API configuration match your local setup.

Here is an example of what your `application.yaml` should look like:

```yaml
server:
  port: 8080

spring:
  application:
    name: LibraryManagementSystem
  datasource:
    url: jdbc:mysql://localhost:3306/lms
    username: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl:
        auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQL8Dialect
    generate-ddl: true

logging:
  level:
    org.springframework.security: TRACE

api:
  endpoint:
    base-url: /api/v1
```

### Build the Project

Run the following Gradle command to build the project:

```bash
./gradlew build
```

Alternatively, you can run the application as a JAR file after building:

```bash
java -jar build/libs/lms-server-0.0.1-SNAPSHOT.jar
```

The application will start running on [http://localhost:8080](http://localhost:8080).

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details. 
