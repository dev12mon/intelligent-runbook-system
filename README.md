# 🛡️ Intelligent Runbook System (AI-Powered SRE Assistant)

An event-driven, microservices-based architecture designed to automate Site Reliability Engineering (SRE) tasks. This system ingests critical error logs, processes them asynchronously using message queues, and leverages Generative AI (LLMs) to automatically generate step-by-step resolution runbooks for operations teams.

---

## 🏗️ Architecture & Tech Stack

This project is built using modern cloud-native patterns and fully containerized via Docker.

### Core Services
* **API Gateway:** Spring Boot (Entry point for log ingestion)
* **AI Analyzer Service:** Spring Boot & Java 8 (Processes logs, integrates with AI via Asynchronous `CompletableFuture`)
* **Message Broker:** RabbitMQ (Decouples ingestion from processing to handle high throughput)
* **Database:** PostgreSQL (Persists generated runbooks for future retrieval)

### Observability & UI
* **Monitoring Stack:** Prometheus & Grafana (Live JVM metrics, CPU, and API traffic monitoring using Spring Boot Actuator & Micrometer)
* **Frontend UI:** Vanilla JavaScript, HTML5, Bootstrap 5 (Dark-mode SRE Dashboard)
* **Containerization:** Docker & Docker Compose

---

## 🚀 Getting Started

### Prerequisites
* Docker and Docker Compose installed on your machine.
* Java 17+ and Maven (for local development).
* Python 3 (to serve the local frontend UI).

### 1. Start the Backend Infrastructure
Navigate to the root directory of the project and start all containers using Docker Compose. This will spin up the Gateway, Analyzer, RabbitMQ, PostgreSQL, Prometheus, and Grafana.

```bash
docker-compose up --build -d
Verify that all 6 containers are running successfully:Bashdocker ps
2. Start the Frontend DashboardOpen a new terminal, navigate to the runbook-ui folder, and start the local web server to host the dashboard:Bashcd runbook-ui
python3 -m http.server 5500
Access your SRE Dashboard at: http://localhost:5500🚦 Usage & Data Flow (How it Works)To simulate a production error and trigger the AI runbook generation, send a POST request to the API Gateway:Bashcurl -X POST http://localhost:8080/api/v1/logs/ingest \
-H "Content-Type: application/json" \
-d '{
  "serviceName": "payment-service",
  "level": "CRITICAL",
  "errorMessage": "Database connection timeout in PaymentProcessor causing transaction failures",
  "stackTrace": "java.net.SocketTimeoutException: Read timed out"
}' -i
The Event-Driven Flow:Ingestion: API Gateway receives the log and pushes it to RabbitMQ.Processing: Analyzer Service consumes the log from the queue and triggers a non-blocking background thread.AI Generation: The AI analyzes the stack trace, generates a resolution plan, and saves it to PostgreSQL.Visualization: Refresh your Web Dashboard (Port 5500) to see the newly generated runbook fetched dynamically via the REST API.📊 Observability (Live Monitoring)The system is fully instrumented for enterprise-grade observability.Prometheus: Available at http://localhost:9090 (Scrapes metrics every 5 seconds).Grafana: Available at http://localhost:3000.Default Login: admin / adminSetup Tip: Add Prometheus as a data source (http://prometheus:9090) and import Dashboard ID 4701 for a complete JVM/Spring Boot metrics view.🛠️ Microservices Port Mapping ReferenceServicePortDescriptionAPI Gateway8080Ingests incoming error logs (/api/v1/logs/ingest)Analyzer API8081Serves generated Runbooks (/api/v1/runbooks)RabbitMQ UI15672Message Broker Admin PanelPostgreSQL5432Relational Database for persistencePrometheus9090Time-series metrics databaseGrafana3000Visualization and DashboardsFrontend UI5500SRE Runbook Viewer (Web Page)