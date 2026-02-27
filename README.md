# NexusPay - Advanced Event-Driven Microservices Wallet System

![Status](https://img.shields.io/badge/Status-Live_on_K8s-success)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![Kafka](https://img.shields.io/badge/Apache_Kafka-Distributed-blue)
![Kubernetes](https://img.shields.io/badge/Kubernetes-Orchestration-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-336791)
![Redis](https://img.shields.io/badge/Redis-Caching-red)

**NexusPay** is a high-performance, event-driven digital wallet ecosystem designed for scalable financial transactions. It leverages **Apache Kafka** for asynchronous inter-service communication and is fully orchestrated using **Kubernetes (K8s)** to ensure high availability and fault tolerance.

### 🔗 **Local Cluster Access:** `http://localhost:8080`
*(Note: Access requires Port-Forwarding the Gateway Service within your K8s cluster)*

---

## 🚀 Key Features

### 💰 Core Wallet & Ledger
* **Real-Time Transaction Processing:** Instant balance updates and transaction logging triggered by system events.
* **Event-Driven Architecture:** Asynchronous communication between services using Kafka Topics (`user-created-topic`, `wallet-transactions`).
* **High-Speed Caching:** Optimized transaction history retrieval using **Redis** with `RedisSerializer.json()` for efficient data handling.
* **Transaction History:** Detailed logs for DEPOSIT and WITHDRAW operations with unique transaction IDs.

### 🛠 Infrastructure Excellence (Engineering Highlight)
* **Kubernetes Orchestration:** Fully containerized services managed via K8s Deployments and Services for seamless scaling.
* **Kafka Listener Optimization:** Resolved complex `PLAINTEXT_INTERNAL` protocol mappings to ensure stable broker-client connectivity.
* **Database Reliability:** Robust PostgreSQL integration with Spring Data JPA, featuring secure credential management and dialect optimization.
* **API Gateway:** Centralized routing and request filtering through Spring Cloud Gateway.

### 🔒 Security & Performance
* **JWT Authentication:** Secure user sessions managed by the Auth Service.
* **Modern Serialization:** Custom `RedisTemplate` configuration using `StringRedisSerializer` for keys and JSON serializers for values.
* **Resilience:** Automatic pod recovery and "CrashLoopBackOff" troubleshooting strategies implemented for high uptime.

---

## 🏗 Tech Stack

### Backend Services
* **Language:** Java 17
* **Framework:** Spring Boot 3.x
* **Messaging:** Apache Kafka (Distributed Event Streaming)
* **Caching:** Redis (Data Persistence & Speed)
* **Database:** PostgreSQL (Relational Data)
* **Build Tool:** Maven

### DevOps & Orchestration
* **Containerization:** Docker & Docker Compose
* **Orchestration:** Kubernetes (K8s)
* **Networking:** Spring Cloud Gateway
* **API Testing:** Postman

---

## ⚙️ Installation & Setup (Local K8s)

### Prerequisites
* Docker Desktop (K8s Enabled)
* Java JDK 17+
* Maven
* Kubectl

### Steps to Run
1. **Start Infrastructure:** Run `docker-compose up -d` and verify Kafka is running.
2. **Build and Push:**
```bash
mvn clean package -DskipTests
docker build -t your-username/wallet-service:latest ./wallet-service
docker push your-username/wallet-service:latest
```

### Clone the Repository
```bash
git clone https://github.com/yigitkagankartal/Realtime-chat.git
