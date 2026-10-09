# Task Manager Application

A full-stack Task Manager application built using **Java, Spring Boot, Spring Security, JWT Authentication, and PostgreSQL**. The application allows users to register, log in securely, and manage their personal tasks through REST APIs.

**Project Status:** Backend development and manual API testing completed. Frontend integration and further improvements are planned.

## Features

* **User Registration:** Register new users with validated details.
* **User Login:** Authenticate users and generate JWT tokens.
* **Password Encryption:** Secure password storage using BCrypt.
* **JWT Authentication:** Protect API endpoints using JSON Web Tokens.
* **Task Management:** Create, view, update, and delete tasks.
* **Task Status:** Organize tasks as `TODO`, `IN_PROGRESS`, or `DONE`.
* **Task Priority:** Assign `LOW`, `MEDIUM`, or `HIGH` priority.
* **Due Dates:** Set due dates for tasks.
* **Search and Filtering:** Search tasks by title and filter them by status or priority.
* **User-Specific Access:** Users can access and manage only their own tasks.
* **Input Validation:** Validate incoming request data.
* **PostgreSQL Integration:** Store users and tasks in a relational database.

## Technologies Used

| Technology              | Purpose                                    |
| ----------------------- | ------------------------------------------ |
| Java 17                 | Programming language                       |
| Spring Boot 3.5.16      | Backend application framework              |
| Spring Web              | REST API development                       |
| Spring Data JPA         | Database operations                        |
| Hibernate               | ORM implementation                         |
| Spring Security         | Application security                       |
| JWT (JJWT 0.12.6)       | Token-based authentication                 |
| BCrypt                  | Password hashing                           |
| PostgreSQL              | Relational database                        |
| Maven                   | Dependency management and build automation |
| Jakarta Bean Validation | Request validation                         |
| Eclipse IDE             | Development environment                    |
| Postman                 | API testing                                |

## Project Structure

```text
TaskManager/
├── src/
│   ├── main/
│   │   ├── java/com/kiran/taskmanager/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── TaskController.java
│   │   │   │   └── UserController.java
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── enums/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   └── service/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

*Note: The exact files and package contents may change as development continues.*

## Prerequisites

Before running the application, install:

* Java Development Kit (JDK) 17
* PostgreSQL
* Eclipse IDE or another Java IDE
* Git (optional, for cloning the repository)
* Postman (for testing the APIs)

## Setup and Installation

### 1. Clone the repository

```bash
git clone https://github.com/kirangithub09/TaskManager.git
cd TaskManager
```

Alternatively, import the project into Eclipse as an **Existing Maven Project**.

### 2. Create the PostgreSQL database

Open PostgreSQL or pgAdmin and execute:

```sql
CREATE DATABASE taskmanager_db;
```

### 3. Configure environment variables

The application reads the database password and JWT signing secret from environment variables instead of storing them directly in the source code.

Configure these variables in your environment or in your IDE's run configuration:

| Variable      | Description                                             |
| ------------- | ------------------------------------------------------- |
| `DB_PASSWORD` | Password for your PostgreSQL database user              |
| `JWT_SECRET`  | A strong, Base64-encoded secret used to sign JWT tokens |

Use your own database password. Generate a strong, randomly generated JWT secret and keep it private. Do not commit real credentials or secrets to GitHub.

In Eclipse:

1. Right-click the project and select **Run As → Run Configurations**.
2. Select the Spring Boot application run configuration.
3. Open the **Environment** tab.
4. Add `DB_PASSWORD` and `JWT_SECRET` with your own values.
5. Apply the changes and run the application.

Ensure that `application.properties` references these variables correctly.

### 4. Check application configuration

The main configuration file is:

`src/main/resources/application.properties`

It contains the database connection, JPA/Hibernate, server port, and JWT configuration. Check that the PostgreSQL username, database name, and environment variable references match your local setup.

### 5. Run the application

From the project directory on Windows:

```bash
mvnw.cmd spring-boot:run
```

Alternatively, in Eclipse, run the main Spring Boot application class.

The application runs at:

`http://localhost:8080`

## API Endpoints

The following endpoints are available in the backend. Use the request DTOs and controller mappings in the source code as the final reference for request fields and parameters.

### Authentication

| Method | Endpoint             | Description              | Authentication |
| ------ | -------------------- | ------------------------ | -------------- |
| POST   | `/api/auth/register` | Register a new user      | Not required   |
| POST   | `/api/auth/login`    | Log in and receive a JWT | Not required   |

### Task Management

| Method | Endpoint          | Description                             |
| ------ | ----------------- | --------------------------------------- |
| POST   | `/api/tasks`      | Create a task                           |
| GET    | `/api/tasks`      | Retrieve the authenticated user's tasks |
| GET    | `/api/tasks/{id}` | Retrieve a task by ID                   |
| PUT    | `/api/tasks/{id}` | Update a task                           |
| DELETE | `/api/tasks/{id}` | Delete a task                           |

Task listing also supports filtering by status and priority and searching by title, as implemented in the controller.

### Authentication Header

After logging in, copy the JWT token from the response and include it in the `Authorization` header for protected endpoints:

```http
Authorization: Bearer <your-jwt-token>
```

Replace `<your-jwt-token>` with the token returned by the login endpoint.

## Testing with Postman

A typical manual testing workflow is:

1. Register a user using `/api/auth/register`.
2. Log in using `/api/auth/login`.
3. Copy the JWT token from the login response.
4. Add the bearer token to protected requests.
5. Create a task and retrieve the task list.
6. Test updating and deleting tasks.
7. Test status and priority filters and title search.
8. Register a second user and verify that users cannot access or modify each other's tasks.

## Security

* Passwords are hashed using BCrypt.
* JWT tokens are used to authenticate protected requests.
* Task access is scoped to the authenticated user.
* Database passwords and JWT signing secrets are configured outside the source code.
* Protected endpoints require a valid bearer token.

## Future Improvements

* Build and integrate a responsive frontend using React.
* Improve exception handling and API error responses.
* Add automated unit and integration tests.
* Improve CORS configuration for frontend integration.
* Add pagination and sorting for task lists.
* Improve API documentation with Swagger/OpenAPI.
* Add database schema migrations.
* Improve deployment and production configuration.

## Learning Outcomes

This project provides practical experience with:

* Java and object-oriented programming
* REST API development
* Spring Boot layered architecture
* Dependency injection
* Spring Data JPA and Hibernate
* PostgreSQL database integration
* Spring Security and JWT authentication
* DTOs and request validation
* CRUD operations and user-specific authorization
* API testing with Postman
* Git and GitHub

## Repository

GitHub: https://github.com/kirangithub09/TaskManager

---

**Note:** This project is actively being developed as a learning project. Features, configuration, and documentation may evolve as new concepts are implemented.
