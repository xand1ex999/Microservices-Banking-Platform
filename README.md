# Distributed Banking Microservices Platform
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)![Kafka](https://img.shields.io/badge/Kafka-black)
![Prometheus](https://img.shields.io/badge/Prometheus-orange)
![Grafana](https://img.shields.io/badge/Grafana-F46800)
![Docker](https://img.shields.io/badge/Docker-2496ED)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D)

A production-ready banking microservices platform demonstrating event-driven communication, observability, infrastructure automation, and cloud-native deployment practices.
## Architecture

```text
                    Internet
                        │
                        ▼
        ┌──────────────────────────────┐
        │ Nginx + Firewall (Hetzner)   │
        └──────────────┬───────────────┘
                       │
      ┌────────────────┼────────────────┐
      ▼                ▼                ▼
┌──────────┐    ┌──────────┐    ┌──────────────┐
│   Auth   │    │ Banking  │    │ Notification │
│ Service  │    │ Service  │    │   Service    │
└────┬─────┘    └────┬─────┘    └──────┬───────┘
     │               │                 │
     └──────────┬────┴─────────────────┘
                ▼            
           ┌──────────┐
           │  Kafka   │
           └────┬─────┘
                │
      ┌─────────┴─────────┐
      ▼                   ▼
┌─────────────┐   ┌─────────────────┐
│ PostgreSQL  │   │ Prometheus      │
│ 3 Databases │   │ + Grafana       │
└─────────────┘   └─────────────────┘

Infrastructure
───────────────────────── 
Auth Service :8080 
Banking Service :8082 
Notification Service :8084 
PostgreSQL :5432 
Apache Kafka :9092 
Kafka UI :8081 
Prometheus (Metrics) :9090 
Grafana (Dashboards) :3000
```
## What it does

- User registration, login, JWT-based auth with refresh tokens, role-based access (USER / ADMIN)
- Account creation and per-account transaction history
- Kafka event flow: auth-service publishes `user.registered` → notification-service consumes and persists the notification
- Per-service PostgreSQL databases, each with independent Flyway migrations
- CI/CD: PRs run build + tests; merges to `main` auto-deploy to VPS via SSH
- Nginx reverse proxy, Prometheus metrics scraping, Grafana dashboards

## Deployment

The project is deployed on a self-managed Hetzner VPS. The server runs Nginx as a reverse proxy with a firewall, and all services run in Docker containers managed by Compose.

## Local run

**Prerequisites:** Java 21, Maven 3.9+, Docker with Compose plugin.

```bash
# 1. Clone and build JARs
git clone <repo-url>
mvn clean package -DskipTests

# 2. Copy the env file and adjust if needed
cp .env.example .env   # or use the existing .env as-is for local dev

# 3. Start the full stack (all 3 services + Postgres + Kafka + monitoring)
docker-compose --profile full up --build
```

Once running, the services are available at:

| Service | URL |
|---------|-----|
| Auth Service | http://localhost:8080 |
| Banking Service | http://localhost:8082 |
| Notification Service | http://localhost:8084 |
| Swagger UI (auth) | http://localhost:8080/swagger-ui.html |
| Kafka UI | http://localhost:8081 |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 (admin / see `.env`) |

**Health checks:**
```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:8084/actuator/health
```

To start only infrastructure (Postgres + Kafka) without the app services:
```bash
docker-compose up postgres kafka kafka-ui
```

## Notes

This repository focuses more on microservice architecture and infrastructure than on banking domain complexity.
