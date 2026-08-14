# Task Manager API

> **Status:** 🚧 Actively Under Development

A backend REST API for managing personal tasks, built with **Spring Boot** and **PostgreSQL**. This project is part of my journey to strengthen my backend development skills by implementing real-world concepts such as layered architecture, RESTful APIs, data persistence, validation, exception handling, and security.

The project is being developed incrementally, with each feature added, tested, and refined before moving on to the next.

---

## About the Project

The goal of this project is not just to build a CRUD application, but to understand how a production-style Spring Boot application is structured.

The application follows a clean layered architecture, separating responsibilities into controllers, services, repositories, DTOs, entities, configuration, and exception handling. As development progresses, additional features and best practices will be incorporated to make the project more scalable and maintainable.

---

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA (Hibernate)
* Spring Security
* PostgreSQL
* Maven
* Lombok
* Jakarta Bean Validation

---

## Features Implemented

### User Module

* User registration
* User login
* Password encryption using BCrypt
* Request validation
* User response DTOs (without exposing sensitive data)

### Task Module

* Create a task
* Retrieve all tasks
* Retrieve a task by ID
* Update a task
* Delete a task
* Filter tasks by status
* Filter tasks by priority
* Search tasks by title

### Backend Features

* Layered architecture
* RESTful API design
* Global exception handling
* Input validation
* Entity relationships using JPA
* Repository pattern with Spring Data JPA

---

## Work in Progress

The following features are currently planned and will be added in future updates:

* JWT-based authentication and authorization
* Role-based access control
* Pagination and sorting
* Swagger/OpenAPI documentation
* Unit and integration testing
* Docker support
* Improved API response structure
* Production-ready exception handling
* Deployment configuration

---

## Project Structure

```text
src/main/java
└── com.kiran.taskmanager
    ├── config
    ├── controller
    ├── dto
    ├── entity
    ├── exception
    ├── repository
    ├── service
    └── TaskmanagerApplication.java
```

---

## Getting Started

### Prerequisites

* Java 17 or later
* Maven
* PostgreSQL
* Eclipse, IntelliJ IDEA, or VS Code

### Clone the Repository

```bash
git clone <repository-url>
cd taskmanager
```

### Configure the Database

Create a PostgreSQL database named:

```text
taskmanager_db
```

Update the database credentials in `application.properties` to match your local PostgreSQL configuration.

### Run the Application

Run `TaskmanagerApplication.java` from your IDE, or use:

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

## Current API Endpoints

### Authentication

| Method | Endpoint             | Description                   |
| ------ | -------------------- | ----------------------------- |
| POST   | `/api/auth/register` | Register a new user           |
| POST   | `/api/auth/login`    | Authenticate an existing user |

### Tasks

| Method | Endpoint          | Description             |
| ------ | ----------------- | ----------------------- |
| POST   | `/api/tasks`      | Create a task           |
| GET    | `/api/tasks`      | Retrieve all tasks      |
| GET    | `/api/tasks/{id}` | Retrieve a task by ID   |
| PUT    | `/api/tasks/{id}` | Update an existing task |
| DELETE | `/api/tasks/{id}` | Delete a task           |

---

## Learning Goals

This project is helping me gain practical experience with:

* Spring Boot application development
* REST API design
* Spring Data JPA and Hibernate
* Database modelling
* Exception handling
* Validation
* Spring Security
* Clean code practices
* Scalable backend architecture

---

## Current Development Status

This repository is actively being developed. New features, improvements, and documentation will be added as I continue learning and expanding the project.

Feedback, suggestions, and constructive criticism are always appreciated.

---

## Author

**Kiran Rawle**
- GitHub: [@kirangithub09]
- Email: kiranrawle07@gmail.com

---

> Thank you for visiting the project. More updates and features will be added as development continues.
