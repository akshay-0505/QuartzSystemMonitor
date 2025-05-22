# Quartz System Monitor

This is a Spring Boot application that uses the Quartz Scheduler and [OSHI](https://github.com/oshi/oshi) to periodically monitor system metrics like CPU, memory, hardware sensors, network interfaces, and running processes.

GitHub Repo: [QuartzSystemMonitor](https://github.com/akshay-0505/QuartzSystemMonitor)

---

##  Features

-  **Quartz Scheduler Integration** to periodically log system data
-  **CSV Logging** of system resource snapshots
-  **REST APIs** to access real-time and historical monitoring data
-  **CPU & Memory Monitoring** (usage %, available, total)
-  **Hardware Monitoring** (CPU temperature, fan speed, voltage)
-  **Network Monitoring** (bytes sent/received, interface status, IPs)
-  **Process Monitoring** (CPU %, memory, top N processes)
-  **Customizable Schedule Interval** for monitoring frequency
-  **Download Monitoring History** as CSV
-  **System Metadata API** (OS, architecture, processors, version)

---

## Tech Stack

- Java 17+
- Spring Boot 3.x
- Quartz Scheduler
- OSHI (Operating System and Hardware Info)
- Maven

---
## Project Structure
```bash
src
├── main
│   ├── java
│   │   └── com.quartz.monitor
│   │       ├── controller      # REST endpoints
│   │       ├── service         # Business logic
│   │       ├── dto             # API response classes
│   │       ├── job             # Quartz job logic
│   │       └── config          # Quartz scheduler config
│   └── resources
│       └── application.yml
├── monitor-log.csv             # Monitoring data log file
```
---

##  API Endpoints

| Method | Endpoint                                       | Description                                        |
|--------|------------------------------------------------|----------------------------------------------------|
| `GET`  | `/api/monitor/history?n=10`                    | Fetch the last `n` monitoring entries              |
| `GET`  | `/api/monitor/download`                        | Download full monitoring log as a CSV file         |
| `POST` | `/api/monitor/schedule?interval=60`            | Set monitoring interval in seconds (Quartz job)    |
| `GET`  | `/api/monitor/metadata`                        | View static system metadata (CPU, OS, RAM, etc.)   |
| `GET`  | `/api/monitor/hardware`                        | View hardware sensor data (temp, fan, voltage)     |
| `GET`  | `/api/monitor/network`                         | View network interface stats                       |
| `GET`  | `/api/monitor/processes?sort=cpu&count=5`      | View top N running processes sorted by CPU/memory  |


---

## Tech Stack

- Java 17+
- Spring Boot
- Quartz Scheduler
- OSHI (Operating System Hardware Info)
- Maven

---

##  Setup Instructions

### Prerequisites

- Java 17 or above installed
- Maven installed
- Git (optional)

### Clone the Repository

```bash
git clone https://github.com/your-username/system-monitoring-app.git
cd system-monitoring-app
```

###  Run the Application
```bash
mvn spring-boot:run
```
### Or build and run the JAR:
```bash
mvn clean install
java -jar target/system-monitoring-app-1.0.0.jar
```
### Sample Log Format
```text
Timestamp,CPU Usage %,Memory Usage %
2025-05-17T20:00:30,27.5,68.2
2025-05-17T20:01:30,30.1,70.4

```

###  Use Cases
- Monitor system load during application testing

- Observe resource trends in dev/QA environments

- Collect data for system audits

- Lightweight alternative to full monitoring tools

###  Possible Enhancements
-  Frontend dashboard for real-time charts

-  Email/SMS alerts for high CPU or memory usage

- Save logs to a database (e.g., PostgreSQL, MongoDB)

-  Add metrics like disk usage, thread count, JVM stats

-  Pause/resume monitoring without restarting app

###  Contributing
Pull requests are welcome! If you'd like to add more features or fix bugs, feel free to fork this repo and submit a PR.

###  License
This project is open-source and available under the MIT License.

###  Author
Developed by Akshay Gadhave




