# LinkTag

[![Build LinkTag](https://github.com/programmer-k/LinkTag/actions/workflows/build.yml/badge.svg)](https://github.com/programmer-k/LinkTag/actions/workflows/build.yml)
[![Deploy LinkTag](https://github.com/programmer-k/LinkTag/actions/workflows/deploy.yml/badge.svg)](https://github.com/programmer-k/LinkTag/actions/workflows/deploy.yml)

**A simple link tagging service built with Spring Boot.**

## Features

### Link Management
- Add, delete, update, and view saved links.
- Store metadata such as title, URL, description, and creation date.

### Tagging
- Tag your links for easy categorization.
- Show related links by tags.

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

2. (Optional for production) Set up MySQL with Docker:
   ```bash
   docker run --name linktag-mysql \
     -e MYSQL_ROOT_PASSWORD=your-secret-password \
     -e MYSQL_DATABASE=linktag \
     -p 3306:3306 \
     --restart unless-stopped \
     -d mysql:latest
   ```

3. Run the app:
    ```bash
    ./gradlew bootRun
    ```

4. Open in browser:
    ```
    http://localhost:8080
    ```

## Tech Stack

- **[Spring Boot](https://spring.io/projects/spring-boot)**
- **[Gradle](https://gradle.org/)**
- **[Thymeleaf](https://www.thymeleaf.org/)**
- **[jsoup](https://jsoup.org/)**
- **[H2 Database](https://www.h2database.com/)**
- **[MySQL](https://www.mysql.com/)**
- **[GitHub Actions](https://github.com/features/actions)**
- **[Lombok](https://projectlombok.org/)**

## Implementation Roadmap

### v1.0
- Basic link management (CRUD)
- User authentication (login/signup)
- In-memory DB (H2)
- Frontend with Thymeleaf
- Basic CI/CD pipeline setup

### v2.0
- Tagging support for links
- Public/private visibility settings
- Persistent DB support (MySQL)

### v3.0
- Import and export functionality
- Filter links by title, user, and tag
- Show other links with overlapping tags
- Error handling

## Live Demo
Visit the live demo at [http://linktag.ddnsking.com:8080](http://linktag.ddnsking.com:8080).
