# Testify API - Frontend Development Guide

Welcome to the Testify API! This guide provides everything you need to connect the frontend application to the backend services.

## 1. API Base URL

All API endpoints are relative to the following base URL:

**Production URL:** `https://testify-api-17nq.onrender.com`

---

## 2. Authentication

The API uses JWT (JSON Web Tokens) for authentication. All requests to protected endpoints must include a JWT in the `Authorization` header.

**Authentication Flow:**

1.  **Register:** The user registers with a username, email, and password.
2.  **Login:** The user logs in with their credentials to receive a JWT.
3.  **Store JWT:** The frontend application should store this token securely (e.g., in `localStorage` or `sessionStorage`).
4.  **Send JWT:** For every subsequent request to a protected endpoint, include the token in the `Authorization` header.

**Header Format:**
`Authorization: Bearer <your_jwt_token>`

### Authentication Endpoints

While the backend is configured, the specific `/api/v1/auth/` endpoints for registration and login have not been implemented yet. For now, you can use the in-memory user for testing protected endpoints via Basic Auth in tools like Postman or Insomnia.

*   **Username:** `user`
*   **Password:** `password`

The full JWT-based authentication flow will be implemented in a future step.

---

## 3. Key API Endpoints & DTOs

This section highlights the main resources and provides examples of their data structures (DTOs). For a complete, interactive list of all endpoints, please visit the **[Swagger UI](https://testify-api-17nq.onrender.com/swagger-ui.html)**.

### Test Cases (`/api/v1/test-cases`)

#### Create a Test Case

*   **Endpoint:** `POST /api/v1/test-cases`
*   **Request Body (`TestCaseCreateRequest`):**
    ```json
    {
      "name": "Verify user login with valid credentials",
      "description": "This test case ensures that a registered user can log in successfully.",
      "preconditions": "User must be registered and have an active account.",
      "steps": "1. Navigate to login page. 2. Enter valid username. 3. Enter valid password. 4. Click login.",
      "expectedResult": "User is redirected to the dashboard.",
      "priority": "HIGH",
      "testSuiteId": 1
    }
    ```

#### Get a Test Case

*   **Endpoint:** `GET /api/v1/test-cases/{id}`
*   **Response Body (`TestCaseResponse`):**
    ```json
    {
      "id": 1,
      "name": "Verify user login with valid credentials",
      "description": "This test case ensures that a registered user can log in successfully.",
      "preconditions": "User must be registered and have an active account.",
      "steps": "1. Navigate to login page. 2. Enter valid username. 3. Enter valid password. 4. Click login.",
      "expectedResult": "User is redirected to the dashboard.",
      "priority": "HIGH",
      "status": "DRAFT",
      "createdBy": {
        "id": 1,
        "username": "user",
        "firstName": null,
        "lastName": null
      },
      "createdAt": "2026-06-24T12:00:00Z",
      "updatedAt": "2026-06-24T12:00:00Z"
    }
    ```

### Test Plans (`/api/v1/test-plans`)

#### Create a Test Plan

*   **Endpoint:** `POST /api/v1/test-plans`
*   **Request Body (`TestPlanCreateRequest`):**
    ```json
    {
      "name": "Q3 2026 Regression Test Plan",
      "description": "Full regression testing for the Q3 release.",
      "scope": "All major features including login, dashboard, and reporting.",
      "startDate": "2026-07-01",
      "endDate": "2026-07-15"
    }
    ```

#### Get a Test Plan

*   **Endpoint:** `GET /api/v1/test-plans/{id}`
*   **Response Body (`TestPlanResponse`):**
    ```json
    {
      "id": 1,
      "name": "Q3 2026 Regression Test Plan",
      "description": "Full regression testing for the Q3 release.",
      "scope": "All major features including login, dashboard, and reporting.",
      "startDate": "2026-07-01",
      "endDate": "2026-07-15",
      "status": "PLANNED",
      "createdBy": {
        "id": 1,
        "username": "user"
      },
      "totalTestCases": 0,
      "createdAt": "2026-06-24T12:30:00Z",
      "updatedAt": "2026-06-24T12:30:00Z"
    }
    ```

---

## 4. Error Handling

The API returns standard HTTP status codes to indicate the success or failure of a request.

*   **`2xx` Success:**
    *   `200 OK`: Standard success response.
    *   `201 Created`: The resource was successfully created.
    *   `204 No Content`: The request was successful, but there is no content to return (e.g., after a deletion).
*   **`4xx` Client Errors:**
    *   `400 Bad Request`: The request was malformed (e.g., missing required fields, invalid data format). The response body will often contain details.
    *   `401 Unauthorized`: The request requires authentication, but no (or invalid) credentials were provided. The user should be prompted to log in.
    *   `403 Forbidden`: The authenticated user does not have permission to access the requested resource.
    *   `404 Not Found`: The requested resource does not exist.
*   **`5xx` Server Errors:**
    *   `500 Internal Server Error`: An unexpected error occurred on the server.

**Example Error Response (400 Bad Request):**

```json
{
  "timestamp": "2026-06-24T13:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for object='testCaseCreateRequest'. Error count: 1",
  "errors": [
    {
      "field": "name",
      "defaultMessage": "must not be blank"
    }
  ],
  "path": "/api/v1/test-cases"
}
```

Your frontend should be prepared to handle these status codes and display appropriate messages to the user.

---

## 5. Further Resources

For a complete and interactive API reference, always refer to the **[Swagger UI Documentation](https://testify-api-17nq.onrender.com/swagger-ui.html)**. It is the single source of truth for all API endpoints, parameters, and schemas.
