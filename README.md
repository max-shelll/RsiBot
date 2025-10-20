# Rsi Bot — Relative Strength Index indicator

## 🧩 About the Project

A lightweight **Java microservice bot** built around the **Relative Strength Index (RSI)** indicator.  
Each service operates independently — enabling flexible scaling, clean separation of logic, and easy integration with external systems.

---

## ⚙️ Tech Stack

- **Spring Boot** `v3.5.6` + **Lombok**
- **Telegrambot** `v6.9.7.1`
- **RabbitMQ**
- **Webflux**

---

## 🚀 Features
- 📊 Calculates RSI and detects key thresholds (overbought / oversold)
- ⚙️ Microservice-based architecture (`rsi-service`, `dispatcher`)
- 🔔 Event-driven actions or notifications
- 🧩 Easily extendable with new services and indicators

---

---

## 🧱 Architecture Overview
RsiBot Microservices
├── rsi-service → Calculates RSI values from market data
├── dispatcher → Handles event routing & inter-service communication

---

---

## ⚡ Installation & Run
```bash
# Clone repository
git clone https://github.com/max-shelll/RsiBot.git
cd RsiBot

# Build project
mvn clean install   # or gradle build

# Run each microservice
java -jar rsi-service/target/rsi-service.jar
java -jar dispatcher/target/dispatcher.jar
```

---

---

## ⚙️ Configuration
Adjust environment variables or config files for:

* `RSI_PERIOD` (default: 14)
* `OVERBOUGHT` / `OVERSOLD` thresholds (e.g., 70 / 30)
* Data source endpoint (API / WebSocket)
* Dispatcher routes or webhook URLs

---
