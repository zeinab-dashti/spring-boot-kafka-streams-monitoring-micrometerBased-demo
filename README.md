# Spring Boot Kafka Streams Monitoring (Micrometer + Prometheus + Grafana)

A sample project demonstrating **Kafka Streams** application monitoring using **Micrometer**, **Prometheus**, and **Grafana** with **Spring Boot 3.2**.

This project also shows how to safely bind Kafka Streams internal metrics and expose them via `/actuator/prometheus`, and optionally via JMX.

---

## Features

-  Kafka Streams internal metrics via Micrometer
-  Prometheus-compatible `/actuator/prometheus` endpoint
-  Grafana visualization (optional setup included)
- ️ Docker-based local development
-  Automatic topic creation and sample message publishing

---

##  Tech Stack

- Spring Boot 3.2
- Kafka Streams
- Micrometer (Prometheus + JMX registries)
- Prometheus
- Grafana
- Docker & Docker Compose


---

## Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 21
- Maven

---

### Run the project

Build and start everything using Docker Compose:

```bash
docker compose up --build
```

No need to run mvn clean package manually — it is executed as part of the Dockerfile during image build.

This will spin up:
- Kafka + Zookeeper 
- Kafka Streams app (with Micrometer metrics)
- Prometheus (scraping metrics from the app)
- Grafana (viewing metrics dashboards)


## Endpoints

| Endpoint URL                                    | Description                                       |
|-------------------------------------------------|---------------------------------------------------|
| `http://localhost:9404/actuator/prometheus`     | Exposes application metrics in Prometheus format  |
| `http://localhost:9090/`                        | Prometheus Web UI                                 |
| `http://localhost:3000/`                        | Grafana Web UI (login: `admin` / `admin`)         |


## Kafka Streams Metrics
Metrics are available under the kafka_stream_*


## Producing & Consuming Data
On startup, the app:
- Creates input-topic and output-topic 
- Sends 10 test messages to input-topic 
- Processes them via Kafka Streams 
- Writes to output-topic 
- Logs consumed results from output-topic

---

## Development Tips
- Change test message count in produceMockData()
- Use Grafana to build dashboards from Prometheus metrics 
- Extend DataProcessTopology with additional stream logic 
- Add tags or custom metrics for domain events
