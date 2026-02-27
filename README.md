# UCAMP API
Campus community platform backend API (Kotlin + Spring Boot)

## Tech Stack
- Kotlin
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Spring Security
- JWT

## Project Structure
src/main/kotlin/com/ucamp/api
├─ board
├─ user
├─ auth
├─ security
└─ common


## Key Features
- Board CRUD (GET/POST/PUT/PATCH/DELETE)
- Signup / Login
- Password hashing (PasswordEncoder)
- JWT access token issuance
- Spring Security authorization rules
  - Public: GET /boards, GET /boards/**
  - Protected: POST/PUT/PATCH/DELETE /boards/**

## API Quick Test (Postman/curl)
### 1) Signup
POST `/auth/signup`

### 2) Login → Get accessToken
POST `/auth/login`

### 3) Create board with token
POST `/boards`
Header:
`Authorization: Bearer <accessToken>`

## Error Response Format
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "name must not be blank",
  "path": "/boards/1"
}
