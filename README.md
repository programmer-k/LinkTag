# LinkTag

**A simple link tagging service built with Spring Boot.**

## Features

### Link Management
- Add, delete, update, and view saved links.
- Store metadata such as title, URL, description, and creation date.

### Tagging
- Tag your links for easy categorization.
- Filter links by tags.

### Import / Export
- Import links from external sources.
- Export your link collection for backup or transfer.

### Sharing
- Each user can set their links as public or private.
- Users can choose whether their links are visible only to themselves or to others.

## How to Run

1. Clone the project:
    ```bash
    git clone https://github.com/programmer-k/LinkTag.git
    cd LinkTag
    ```

2. Run the app:
    ```bash
    ./gradlew bootRun
    ```

3. Open in browser:
    ```
    http://localhost:8080
    ```

## Frameworks & Libraries

- **[Spring Boot](https://spring.io/projects/spring-boot)**
    - Spring Web
    - Spring Data JPA
    - Spring Security
    - Spring Boot DevTools
- **[Gradle](https://gradle.org/)**
- **[Thymeleaf](https://www.thymeleaf.org/)**
- **[H2 Database](https://www.h2database.com/)**
- **[Lombok](https://projectlombok.org/)**

## Roadmap

### v0.1
- Basic link management (CRUD)
- User authentication (login/signup)
- In-memory DB (H2)
- Frontend with Thymeleaf
- Basic CI/CD pipeline setup

### v0.2
- Tagging support for links
- Public/private visibility settings
- Persistent DB support (MySQL)

### v0.3
- Import and export functionality
- REST API for external integrations
- Docker builds support for consistent deployment
