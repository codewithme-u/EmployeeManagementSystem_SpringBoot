# Employee Management System — Spring Boot

A production-oriented **Employee Management System REST API** built using **Java and Spring Boot**.

The application provides APIs for managing employees and departments, along with reporting APIs for employee and department statistics.

---

## 🚀 Features

### 👨‍💼 Employee Management

- Create employee
- Get employee by ID
- Get all employees
- Update employee
- Delete employee
- Bulk employee creation
- Update employee status
- Employee search and filtering
- Pagination
- Sorting

### 🏢 Department Management

- Create department
- Get department by ID
- Get all departments
- Update department
- Delete department

### 📊 Employee Reports

- Employee count by type
- Employee count by status
- Employee count by gender

### 📈 Department Reports

- Total salary by department
- Active employee count by department
- Average salary by department

### 🛡️ Validation & Exception Handling

- Request DTO validation
- Business-rule validation
- Duplicate resource handling
- Resource-not-found handling
- Invalid parameter handling
- Global exception handling
- Standardized error responses

---

# 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Data JPA | Data Access |
| Hibernate | ORM |
| MySQL | Relational Database |
| Maven | Build Tool |
| MapStruct | DTO Mapping |
| Lombok | Boilerplate Reduction |
| Jakarta Validation | Request Validation |
| Springdoc OpenAPI | API Documentation |
| Git | Version Control |
| GitHub | Source Code Management |

---

# 🏗️ Architecture

The project follows a layered architecture:

```text
Client
   │
   ▼
Controller
   │
   ▼
Request DTO
   │
   ▼
Validation
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
Database
```

Supporting components:

```text
DTO
 ↓
Mapper
 ↓
Entity

Specification
 ↓
Dynamic Filtering

Exception
 ↓
GlobalExceptionHandler
 ↓
Standard Error Response
```

---

# 📁 Project Structure

```text
src/main/java/com/employeemanagementsystem
│
├── annotation
│   └── EmployeeManagementRestController.java
│
├── core
│   └── config
│       └── OpenAPIConfig.java
│
├── department
│   ├── controller
│   ├── dto
│   │   ├── request
│   │   └── response
│   ├── entity
│   ├── mapper
│   ├── repository
│   └── service
│       └── impl
│
├── employee
│   ├── controller
│   ├── dto
│   │   ├── request
│   │   └── response
│   ├── entity
│   ├── enums
│   ├── mapper
│   ├── repository
│   ├── service
│   │   └── impl
│   └── specification
│
├── exception
│   ├── BusinessException.java
│   ├── BusinessRuleException.java
│   ├── DuplicateResourceException.java
│   ├── ErrorCode.java
│   ├── ErrorResponse.java
│   ├── FieldErrorDetail.java
│   ├── GlobalExceptionHandler.java
│   ├── InvalidAgeException.java
│   ├── InvalidParameterException.java
│   └── ResourceNotFoundException.java
│
├── report
│   ├── controller
│   ├── dto
│   │   └── response
│   ├── repository
│   └── service
│       └── impl
│
└── EmployeemanagementsystemApplication.java
```

---

# 🌐 API Base URL

When running locally:

```text
http://localhost:8080/employee-management/api
```

---

# 👨‍💼 Employee APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/employees/{id}` | Get employee by ID |
| GET | `/employees` | Get employees |
| POST | `/employees` | Create employee |
| POST | `/employees/bulk` | Create employees in bulk |
| PUT | `/employees/{id}` | Update employee |
| DELETE | `/employees/{id}` | Delete employee |
| PATCH | `/employees/{id}/status` | Update employee status |

### Example

```http
GET /employee-management/api/employees/1
```

---

# 🏢 Department APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/departments/{id}` | Get department by ID |
| GET | `/departments` | Get all departments |
| POST | `/departments` | Create department |
| PUT | `/departments/{id}` | Update department |
| DELETE | `/departments/{id}` | Delete department |

### Example

```http
GET /employee-management/api/departments/1
```

---

# 📊 Employee Report APIs

### Employee Count by Type

```http
GET /employee-management/api/reports/employees/by-type
```

Returns employee counts grouped by employee type.

### Employee Count by Status

```http
GET /employee-management/api/reports/employees/by-status
```

Returns employee counts grouped by employee status.

### Employee Count by Gender

```http
GET /employee-management/api/reports/employees/by-gender
```

Returns employee counts grouped by gender.

---

# 📈 Department Report APIs

### Total Salary by Department

```http
GET /employee-management/api/reports/departments/total-salary
```

Returns the total salary for each department.

### Active Employee Count by Department

```http
GET /employee-management/api/reports/departments/employee-count
```

Returns the number of active employees in each department.

### Average Salary by Department

```http
GET /employee-management/api/reports/departments/average-salary
```

Returns the average salary for each department.

---

# 📖 API Documentation

The project uses **OpenAPI 3.1** for API documentation.

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI specification:

```text
http://localhost:8080/v3/api-docs
```

Swagger provides interactive documentation for all available APIs, request DTOs, response DTOs, and API schemas.

---

# 🗄️ Database Configuration

Configure the database in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_management
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

> ⚠️ Never commit real database credentials, passwords, API keys, or other secrets to GitHub.

---

# ▶️ Running the Application

### 1. Clone the repository

```bash
git clone https://github.com/codewithme-u/EmployeeManagementSystem_SpringBoot.git
```

### 2. Navigate to the project

```bash
cd EmployeeManagementSystem_SpringBoot
```

### 3. Build the project

#### Windows

```cmd
mvnw.cmd clean install
```

#### Linux / macOS

```bash
./mvnw clean install
```

### 4. Run the application

#### Windows

```cmd
mvnw.cmd spring-boot:run
```

#### Linux / macOS

```bash
./mvnw spring-boot:run
```

---

# 🧪 Testing

Run the test suite using:

### Windows

```cmd
mvnw.cmd test
```

### Linux / macOS

```bash
./mvnw test
```

---

# 🔍 Dynamic Search & Filtering

Employee retrieval supports dynamic filtering using **Spring Data JPA Specifications**.

The employee API can be used with filtering, pagination, and sorting parameters.

Example:

```http
GET /employee-management/api/employees?page=0&size=10&sort=employeeName,asc
```

---

# 📦 DTOs

The API uses DTOs instead of exposing JPA entities directly.

### Request DTOs

- `EmployeeRequestDTO`
- `EmployeeStatusRequestDTO`
- `DepartmentRequestDTO`

### Response DTOs

- `EmployeeResponseDTO`
- `DepartmentResponseDTO`
- `PageResponseDTO<EmployeeResponseDTO>`

### Report DTOs

- `EmployeeTypeCountResponseDTO`
- `EmployeeStatusCountResponseDTO`
- `EmployeeGenderCountResponseDTO`
- `DepartmentTotalSalaryResponseDTO`
- `DepartmentCountResponseDTO`
- `DepartmentAverageSalaryResponseDTO`

---

# 🛡️ Exception Handling

The application uses a centralized:

```text
GlobalExceptionHandler
```

to handle application exceptions consistently.

Examples include:

```text
ResourceNotFoundException
DuplicateResourceException
InvalidParameterException
InvalidAgeException
BusinessRuleException
```

The application also maintains structured error information through:

```text
ErrorCode
ErrorResponse
FieldErrorDetail
```

---

# 📄 Project Documentation

The repository also contains the detailed project documentation:

```text
Production SpringBoot Employee Management System.pdf
```

---

# 🎯 Learning Objectives

This project was built to gain practical experience with real-world Spring Boot backend development.

Key concepts demonstrated:

- Java 17
- Spring Boot
- REST API development
- Layered architecture
- DTO pattern
- MapStruct
- Spring Data JPA
- Hibernate
- Entity relationships
- Validation
- Exception handling
- Dynamic queries
- JPA Specifications
- Pagination
- Sorting
- Reporting queries
- OpenAPI / Swagger
- Maven
- Git & GitHub

---

⭐ If you find this project useful, consider giving it a star!
