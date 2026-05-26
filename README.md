# Cloud-Based Banking Backend Service

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg)](https://www.docker.com/)
[![AWS](https://img.shields.io/badge/AWS-EC2-FF9900.svg)](https://aws.amazon.com/)

A production-ready backend service for managing users, accounts, and transactions, deployed in the cloud via AWS. Built with Spring Boot, Java, and PostgreSQL, featuring full containerization support via Docker.

## Overview

This backend is designed for secure and reliable account & transaction management, supporting JWT authentication, role-based access control, auditing, rate limiting, and health monitoring.

---

## Key Features

- **User Management**: Registration, authentication, role-based access control (USER / ADMIN)
- **Account Management**: Create, list, and manage accounts (freeze/unfreeze by admin)
- **Transaction Management**: Deposit and withdraw funds with immutable audit trail
- **Security**: JWT-based authentication with refresh tokens, secure password hashing
- **Monitoring**: Health checks via `/actuator/health`
- **Rate Limiting**: Configurable per endpoint using Bucket4j
- **Testing**: Comprehensive unit and integration tests using JUnit 5 and Mockito
- **Cloud Deployment**: Successfully deployed on AWS EC2
- **Containerization**: Dockerized backend and PostgreSQL for reproducible environments

---
## Architecture
```
┌─────────────┐      ┌──────────────────┐      ┌──────────────┐
│   Client    │─────▶│   Spring Boot    │─────▶│  PostgreSQL  │
│ (REST API)  │      │   Application    │      │   Database   │
└─────────────┘      └──────────────────┘      └──────────────┘
                              │
                              ▼
                     ┌─────────────────┐
                     │  JWT Security   │
                     │  Rate Limiting  │
                     │  Health Checks  │
                     └─────────────────┘
```

## Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Spring Boot** | 3.3.5 | Application framework |
| **Java** | 21 | Programming language |
| **PostgreSQL** | 16 | Database |
| **Spring Data JPA / Hibernate** | - | ORM layer |
| **Spring Security** | - | JWT authentication |
| **Bucket4j** | 8.7.0 | Rate limiting |
| **Lombok** | - | Code reduction |
| **Spring Boot Actuator** | - | Health monitoring |
| **JUnit 5 & Mockito** | - | Testing framework |
| **Docker** | - | Containerization |


### Data Model

All entities are mapped using JPA/Hibernate with automatic table generation:

- **User**: Authentication and authorization
- **Account**: Banking accounts with ownership
- **Transaction**: Immutable transaction records
- **RefreshToken**: Secure token management

## Testing

- Unit and integration tests implemented with **JUnit 5** and **Mockito**
- Ensure business logic correctness and API reliability

## Health Monitoring & Rate Limiting

- **Health Checks**: Accessible via `/actuator/health` for real-time service and database status
- **Rate Limiting**: Configured per endpoint using Bucket4j to prevent abuse

---

## CI/CD Pipeline

This project includes a **GitHub Actions CI/CD pipeline** that automates building, testing, and deployment.

### Pipeline Highlights
- **Automated Builds:** Docker images for backend and PostgreSQL are built automatically on every push or pull request.  
- **Automated Testing:** Unit and integration tests run **inside Docker containers** to ensure code correctness before merging.  
- **Environment Management:** Database credentials and JWT secret are securely stored in **GitHub Secrets**.  
- **Deployment Ready:** Once tests pass, the pipeline can deploy to staging or production environments (e.g., AWS EC2).

## AWS Deployment

The service was successfully deployed on AWS EC2:

### Deployment Steps

1. ✅ EC2 instance launched with Ubuntu
2. ✅ Docker and Docker Compose installed
3. ✅ Spring Boot backend and PostgreSQL containers deployed
4. ✅ Security groups configured (SSH and application ports)
5. ✅ Service verified via health checks and API requests
6. ✅ Instance terminated post-verification to avoid costs

> **Note**: The instance was terminated after successful deployment demonstration, confirming full cloud readiness and production deployability.

---

##  License

This is a pet project created for demonstration and learning purposes.
