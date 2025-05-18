# 🖥️ System Resource Monitoring Application (Spring Boot + Quartz)

This Spring Boot application periodically monitors **CPU** and **Memory usage** on a Windows machine using the **Quartz Scheduler** and logs the information to a CSV file. It also provides REST APIs to fetch monitoring history, download logs, and control scheduling behavior.

---

## 🚀 Features

- ✅ Periodic resource monitoring using Quartz
- ✅ Captures:
    - CPU usage (%)
    - Memory usage (%)
    - Timestamp of log
- ✅ Stores logs to `monitoring.csv`
- ✅ REST APIs to:
    - Get monitoring history (last N records)
    - Download monitoring logs as CSV
    - Configure scheduling interval at runtime
    - Get system metadata (OS, CPU, architecture, etc.)

---

## 🏗️ Tech Stack

- Java 17+
- Spring Boot
- Quartz Scheduler
- OSHI (Operating System Hardware Info)
- Maven

---

## ⚙️ Setup Instructions

### ✅ Prerequisites

- Java 17 or above installed
- Maven installed
- Git (optional)

### 📥 Clone the Repository

```bash
git clone https://github.com/your-username/system-monitoring-app.git
cd system-monitoring-app
```

### ▶️ Run the Application
```bash
mvn spring-boot:run
```
### Or build and run the JAR:
```bash
mvn clean install
java -jar target/system-monitoring-app-1.0.0.jar
```
### 📡 API Endpoints
| Method | Endpoint                           | Description                               |
| ------ |------------------------------------| ----------------------------------------- |
| `GET`  | `/api/monitor/history?n=10`        | Fetch the last `n` monitoring entries     |
| `GET`  | `/api/monitor/download`          | Download monitoring log as a CSV file     |
| `POST` | `/api/monitor/schedule?interval=60` | Set the monitoring interval (in seconds)  |
| `GET`  | `/api/monitor/metadata`         | View system metadata (CPU, RAM, OS, etc.) |

### 🧾 Sample Log Format
```text
Timestamp,CPU Usage %,Memory Usage %
2025-05-17T20:00:30,27.5,68.2
2025-05-17T20:01:30,30.1,70.4

```

### 💡 Use Cases
- Monitor system load during application testing

- Observe resource trends in dev/QA environments

- Collect data for system audits

- Lightweight alternative to full monitoring tools

### 🛠️ Possible Enhancements
- 📊 Frontend dashboard for real-time charts

- 🔔 Email/SMS alerts for high CPU or memory usage

- 🗃️ Save logs to a database (e.g., PostgreSQL, MongoDB)

- 🔌 Add metrics like disk usage, thread count, JVM stats

- ⏸️ Pause/resume monitoring without restarting app

### 🙌 Contributing
Pull requests are welcome! If you'd like to add more features or fix bugs, feel free to fork this repo and submit a PR.

### 📝 License
This project is open-source and available under the MIT License.

### 👨‍💻 Author
Developed by Akshay Gadhave




