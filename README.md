# ISSATSO Plus Plus Forum Project

## Project Description

ISSATSO Plus Plus is a microservice-based forum platform designed for universities, facilitating seamless information exchange, event organization, and collaborative interactions among teachers, students, and administration. The platform leverages a microservices architecture for scalability and maintainability, with interservice communication handled efficiently by RabbitMQ.

## Features

- **User Management**: Secure user registration, authentication, and profile management for different roles (students, teachers, administration).
- **Forum Management**: Create, manage, and participate in forums, topics, and discussions.
- **Notification Management**: Real-time notifications for new posts, replies, events, and announcements.
- **API Gateway**: Centralized entry point for all microservices, handling request routing, authentication, and rate limiting.
- **Report Management**: Tools for generating reports on forum activity, user engagement, and system performance.

## Architecture

The project follows a microservice architectural pattern, where each core business capability is developed as an independent service. These services communicate with each other asynchronously using RabbitMQ as a message broker.

- **API Gateway**: Acts as the single entry point for all client requests.
- **User Management Service**: Handles all user-related operations.
- **Forum Management Service**: Manages forum, topic, and post operations.
- **Notification Management Service**: Responsible for sending notifications.
- **Report Management Service**: Provides reporting functionalities.

## Technologies Used

- **Spring Boot**: Framework for building robust, stand-alone, production-grade Spring-based applications.
- **RabbitMQ**: Message broker for asynchronous communication between microservices.
- **Spring Cloud Gateway**: For building the API Gateway.
- **Spring Security & JWT**: For authentication and authorization.
- **Maven**: Dependency management and build automation.
- **Docker & Docker Compose**: For containerization and orchestration of microservices.
- **MySQL (via `init.sql`)**: Database for persisting application data.
- **Firebase Cloud Messaging**: For push notifications.

## Setup Instructions

To set up and run the ISSATSO Plus Plus project locally, follow these steps:

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/SunnyKid503/issatsoplusplus-core-main.git
    cd issatsoplusplus-core-main
    ```

2.  **Docker & Database Setup**:
    Ensure Docker is installed and running on your system. The `docker-compose.yml` file will set up the necessary services including RabbitMQ and MySQL.

    ```bash
    docker-compose up -d
    ```
    This will start the RabbitMQ server and a MySQL database, initializing it with the schema and data from `init.sql`.

3.  **Build Microservices**:
    Navigate into each microservice directory (`api-gate`, `forumManagement`, `notificationManagement`, `reportManagement`, `userManagement`) and build them using Maven.

    ```bash
    # Example for api-gate
    cd api-gate
    ./mvnw clean install
    cd ..
    
    # Repeat for other services: forumManagement, notificationManagement, reportManagement, userManagement
    ```

4.  **Run Microservices**:
    After building, you can run each microservice. For local development, you might run them from your IDE or use the `spring-boot:run` goal.

    ```bash
    # Example for api-gate
    cd api-gate
    ./mvnw spring-boot:run
    cd ..
    
    # Repeat for other services
    ```

    Alternatively, you can use the `run-issatso-core.bat` script if it's configured to run all services.

## API Overview

The API Gateway provides the entry point for all client interactions. Specific API endpoints and their functionalities will be documented within each microservice or through a separate API documentation (e.g., Swagger/OpenAPI).

Further details on API endpoints, request/response bodies, and authentication mechanisms can be found in the respective microservice documentation.