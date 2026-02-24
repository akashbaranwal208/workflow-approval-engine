# 🚀 Workflow Approval Engine

A production-style backend system built using Spring Boot that implements a dynamic, multi-step approval workflow with JWT-based authentication and role-based authorization.

This project simulates real-world enterprise approval flows such as Leave Requests and Purchase Requests.

---

## 📌 Project Overview

The Workflow Approval Engine is designed to handle business approval processes where requests move through multiple validation steps based on roles.

It demonstrates:

- Secure authentication using JWT
- Role-based access control
- Multi-step approval workflows
- Strict state transition validation
- Production-style security configuration

---

## 🏗 Architecture Overview

Layered architecture implementation:

```
Client
  ↓
Controller Layer
  ↓
Service Layer (Business Logic)
  ↓
Repository Layer (JPA)
  ↓
MySQL Database
```

Security Flow:

```
Request → JWT Filter → SecurityContext → Controller → Service
```

---

## 👤 Roles & Responsibilities

| Role      | Responsibilities |
|-----------|------------------|
| EMPLOYEE  | Create requests |
| MANAGER   | Approve Step 1 |
| FINANCE   | Approve Step 2 |

---

## 🔐 Demo Credentials

### 👤 Admin
Email: admin@example.com  
Password: password135

### 👔 Manager
Email: manager@example.com  
Password: password567

### 💼 Finance
Email: finance@example.com  
Password: password890

### 👨‍💻 Employee
Email: akash@example.com  
Password: password123


## 🔄 Workflow Logic

### 1️⃣ LEAVE Request (Single Step)
- Step 1: MANAGER approves
- Status → APPROVED

### 2️⃣ PURCHASE Request (Two Steps)
- Step 1: MANAGER approves
- Step 2: FINANCE approves
- Status → APPROVED

If rejected at any step → Status becomes REJECTED.

State transitions are strictly controlled:
- Only PENDING requests can be approved/rejected.
- Approved/Rejected requests cannot transition again.

---

## 🔐 Authentication & Authorization

### Login
```
POST /api/auth/login
```

Returns a JWT token.

### Using JWT Token
Include in header:

```
Authorization: Bearer <JWT_TOKEN>
```

### Security Rules
- Only `/api/auth/**` is publicly accessible.
- All other endpoints require valid JWT.
- Approver identity is derived from JWT (not passed via API).
- Role validation is enforced at Service layer.

---

## 📡 API Endpoints

### 🔑 Authentication
```
POST /api/auth/login
```

### 👤 Users
```
GET /api/users
```

### 🔄 Workflows
```
POST /api/workflows?requestType=PURCHASE&totalSteps=2
POST /api/workflows?requestType=LEAVE&totalSteps=1
```

### 📝 Requests
```
POST /api/requests?requestType=PURCHASE&userEmail=akash@example.com
```

### ✅ Approvals
```
POST /api/approvals/approve?requestId=1
POST /api/approvals/reject?requestId=1
```

---

## ⚙️ Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- JWT (jjwt 0.9.1)
- Hibernate / JPA
- MySQL
- Maven

---

## 🧠 Key Engineering Decisions

- Approver identity is extracted from JWT token.
- Role-based approval enforced at Service layer.
- Workflow steps are configurable via database.
- Centralized secret key management.
- Global exception handling implemented using `@ControllerAdvice`.
- Clean layered architecture separation.

---

## 🛠 How to Run the Project

1. Clone the repository
2. Configure MySQL in `application.properties`
3. Run:

```
mvn clean install
mvn spring-boot:run
```

4. Insert initial users directly into database (production-style setup)
5. Login and test using Postman

---

## 🧪 Testing Scenarios Covered

- Unauthorized access without JWT
- Role-based approval restrictions
- Invalid request ID handling
- Invalid token rejection
- Multi-step approval validation
- Rejection flow validation

---

## 🚀 Future Enhancements

- Kafka-based event notifications
- Docker containerization
- CI/CD with Jenkins
- AWS deployment
- Swagger API documentation

---

## 👨‍💻 Author

Akash Baranwal  
Backend Engineer | Java | Spring Boot | Microservices
