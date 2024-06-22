# Hotel Management System

### Christian Hosh 1200482
### Othman Hijawi 1202927

## Project Overview

We built an Hotel Management System using Spring boot for the web services and technology course in the spring semester of 2023/2024. The project is a comprehensive system that manages hotel entries.

## Project Resources

### Available Rooms API
- **Endpoint**: `/available`
- **Method**: GET
- **Description**: Retrieves a paginated list of available rooms within a specified date range.
- **Request Parameters**:
  - `page` (int, default: 0): Page number.
  - `size` (int, default: 10): Number of rooms per page.
  - `fromDate` (String): Start date for availability.
  - `toDate` (String): End date for availability.

### User Login API
- **Endpoint**: `/login`
- **Method**: POST
- **Description**: Authenticates a user and returns a JWT token.
- **Request Body**:
  - `username` (String): User's username.
  - `password` (String): User's password.

### User Registration API
- **Endpoint**: `/register`
- **Method**: POST
- **Description**: Registers a new user and returns a JWT token.
- **Request Body**:
  - `username` (String): Desired username.
  - `password` (String): Desired password.
  - `email` (String): User's email address.

## ER Diagram

![erd](https://github.com/ChristianHosh/hotel-management-system/assets/73076057/ee9cc714-5b49-4f93-b17c-3e03ac8cc472)


## Building, Packaging, and Running the Application

### Prerequisites

- JDK 11 or later
- Maven 3.6.3 or later
- Docker (for Docker image generation)

### Building the Application

```bash
mvn clean install
```

## Docker Image

https://hub.docker.com/repository/docker/chrishosh/spring-hms/general
