# 🛡️ Intelligent Runbook System (AI-Powered SRE Assistant)

An event-driven, microservices-based architecture designed to automate Site Reliability Engineering (SRE) tasks. This system ingests critical error logs, processes them asynchronously using message queues, and leverages AI (LLMs) to automatically generate step-by-step resolution runbooks for operations teams.

## 🏗️ Architecture & Tech Stack

This project is built using modern cloud-native patterns and fully containerized via Docker.

* **API Gateway:** Spring Boot (Entry point for log ingestion)
* **AI Analyzer Service:** Spring Boot / Java 8 (Processes logs, integrates with AI via Asynchronous `CompletableFuture`)
* **Message Broker:** RabbitMQ (Decouples ingestion from processing to handle high throughput)
* **Database:** PostgreSQL (Persists generated runbooks for future retrieval)
* **Observability:** Prometheus & Grafana (Live JVM metrics, CPU, and API traffic monitoring)
* **Frontend UI:** Vanilla JavaScript, HTML5, Bootstrap 5 (Dark-mode SRE Dashboard)
* **Containerization:** Docker & Docker Compose

## 🚀 Getting Started

### Prerequisites
* Docker and Docker Compose installed on your machine.
* Java 17+ and Maven (for local development).
* Python 3 (to run the local frontend server).

### 1. Start the Backend Infrastructure
Navigate to the root directory and start all 6 containers using Docker Compose:
```bash
docker-compose up --build -d