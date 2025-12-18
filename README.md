# Inventory Tracker Web Application

A Java-based Inventory Management Web Application developed using **Jakarta EE**, **Servlets**, **JSP**, and **MySQL**, following the **MVC architecture**.  
The application allows users to view, add, update, and delete inventory items through a web interface.

---

## 🔗 Live Deployment
**Render URL:**  
https://inventory-tracker-539a.onrender.com

---

## 📦 GitHub Repository
https://github.com/cse23-093/Inventory-Tracker

---

## 🛠️ Technologies Used

- Java 17 (Jakarta EE)
- Servlets & JSP
- JSTL / EL
- MVC Architecture
- MySQL (AWS RDS)
- JNDI DataSource (Tomcat DBCP)
- Apache Tomcat 10.1
- Maven
- Docker
- Bootstrap 5
- Git & GitHub

---

## 📂 Project Structure

Inventory-Tracker
├── src
│ ├── main
│ │ ├── java
│ │ │ └── com.inventorytracker
│ │ │ ├── controller
│ │ │ │ └── InventoryListServlet.java
│ │ │ ├── dao
│ │ │ │ ├── InventoryItemDAO.java
│ │ │ │ └── InventoryItemDAOImpl.java
│ │ │ ├── model
│ │ │ │ └── InventoryItem.java
│ │ │ └── util
│ │ │ └── DBConnectionUtil.java
│ │ ├── webapp
│ │ │ ├── views
│ │ │ │ └── inventory-list.jsp
│ │ │ ├── index.jsp
│ │ │ └── WEB-INF
│ │ │ └── web.xml
│ └── test
│ └── java
│ └── com.inventorytracker
│ └── dao
│ └── InventoryItemDAOTest.java
├── Dockerfile
├── docker-entrypoint.sh
├── pom.xml
└── README.md

yaml
Copy code

---

## ✅ Application Features

- View all inventory items
- Add new inventory items
- Update existing inventory items
- Delete inventory items
- MySQL database integration
- Connection pooling via JNDI
- Responsive UI using Bootstrap
- Deployed using Docker on Render

---

## 🧪 Testing

- Unit and DAO-level tests are located under:
  src/test/java

markdown
Copy code
- Tests use **JUnit 5** and **H2 in-memory database** for isolated testing.
- Maven Surefire Plugin is configured for test execution.

To run tests locally:
```bash
mvn test
🗄️ Database Schema
sql
Copy code
CREATE DATABASE IF NOT EXISTS inventory_db;
USE inventory_db;

CREATE TABLE IF NOT EXISTS inventory_item (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    category VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO inventory_item (name, quantity, category) VALUES
('USB Keyboard', 12, 'Electronics'),
('HDMI Cable', 3, 'Electronics'),
('Office Chair', 7, 'Furniture'),
('Notebook A4', 2, 'Stationery'),
('Wireless Mouse', 15, 'Electronics');
⚙️ Configuration Notes
Database connection is managed via JNDI (jdbc/InventoryDB)

Environment variables used in deployment:

DB_URL

DB_USERNAME

DB_PASSWORD

Tomcat shutdown port is disabled for cloud compatibility.

👤 Author
Aone Seanego
Computer Systems Engineering Student