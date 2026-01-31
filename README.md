# UCAMP API

Campus community platform for CA backend API

## Tech Stack
- Kotlin
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Spring Security
- JMT

## Porject Structure
src/main/kotlin/com/ucamp/api
-board
-user
-auth
-common

##Board API

### PUT /boards/{id}
### PATCH /boards/{id}

##Error Response Format

- json
  {
  "status" : 400,
  "error" : "Bad request",
  "message" : "name must not be blank",
  "path" : "/boards/1"
  }
