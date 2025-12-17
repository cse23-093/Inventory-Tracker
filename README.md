Inventory Tracker with Alerts

A web-based inventory management system that simulates a warehouse inventory tracker with low-stock alerts, built using Java, Jakarta Servlets, JSP, JSTL, Bootstrap, and MySQL.

This project was developed as a technical assignment to demonstrate full-stack development, clean MVC architecture, data persistence, testing, and deployment readiness.

---

## 🚀 Features

- Add new inventory items (name, quantity, category)
- Update existing items
- Delete items
- View all inventory items in a clean, intuitive UI
- Automatically highlight **low-stock items** (quantity < 5)
- Export inventory data as **CSV** or **JSON**
- REST-style API endpoint for inventory data (`/api/inventory`)

---

## 🏗️ Architecture

The application follows a **Model–View–Controller (MVC)** architecture:

- **Model**  
  JavaBeans representing inventory entities and business logic (e.g., low-stock detection, validation)

- **View**  
  JSP pages using **JSTL and EL only** (no scriptlets), styled with **Bootstrap 5**

- **Controller**  
  Jakarta Servlets handling request routing and coordinating between views and data access

- **DAO Layer**  
  Responsible for all database operations using **JNDI connection pooling**

---

## 🧰 Tech Stack

- Java 17+
- Jakarta Servlet API 6
- JSP + JSTL + Expression Language (EL)
- Bootstrap 5 (via WebJars)
- MySQL
- Maven
- JUnit 5
- Embedded Tomcat (for integration testing)
- IntelliJ IDEA

---

## 💾 Data Persistence Choice & Justification

**Chosen storage:** MySQL (Relational Database)

**Why MySQL:**
- Strong consistency and reliability
- Well-suited for structured data such as inventory records
- Supports indexing and constraints
- Easily integrates with Java via JDBC
- Production-ready and widely used in enterprise systems

**Trade-offs:**
- Requires schema design and setup
- Slightly more overhead than flat files
- Less flexible than NoSQL for unstructured data

For this use case (inventory with strict fields and relationships), a relational database is the most appropriate choice.

---

## 🔌 Database Setup

Create the database and table using the following SQL:

```sql
CREATE DATABASE IF NOT EXISTS inventory_db;
USE inventory_db;

CREATE TABLE inventory_item (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    category VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL
);

INSERT INTO inventory_item (name, quantity, category) VALUES
('USB Keyboard', 12, 'Electronics'),
('HDMI Cable', 3, 'Electronics'),
('Office Chair', 7, 'Furniture'),
('Notebook A4', 2, 'Stationery'),
('Wireless Mouse', 15, 'Electronics');
🔄 Connection Pooling
The application uses JNDI connection pooling configured in:

META-INF/context.xml

WEB-INF/web.xml

This improves performance and scalability by reusing database connections instead of creating a new connection per request.

🧪 Running Tests
This project includes unit tests and integration tests using JUnit 5.

Prerequisites
Java 17+

Maven (or IntelliJ’s built-in Maven)

Run All Tests
From the project root directory (the folder containing pom.xml):

bash
Copy code
mvn clean test
Alternatively, tests can be run directly from IntelliJ IDEA:

Using the Maven → Lifecycle → test

Or by right-clicking src/test/java → Run All Tests

Test Coverage
Unit tests

Inventory validation

Low-stock detection logic

DAO tests

CRUD operations using an in-memory database

Integration test

Starts an embedded Tomcat server

Calls /api/inventory

Verifies correct JSON response

🌐 API Endpoint
GET /api/inventory
Returns all inventory items in JSON format.

This endpoint is used for integration testing and export functionality.

▶️ Running the Application
Configure MySQL credentials in META-INF/context.xml

Deploy the project as a WAR to Apache Tomcat

Access the application at:

bash
Copy code
http://localhost:8080/inventory-tracker/
☁️ Deployment
The project is prepared for deployment using:

Apache Tomcat

Cloud platforms supporting Java web applications (Render, Railway, AWS EC2 free tier)

A public GitHub repository contains the full source code and commit history.

📁 Project Structure
css
Copy code
src/
 ├── main/
 │   ├── java/
 │   │   └── com.inventorytracker/
 │   │       ├── controller/
 │   │       ├── dao/
 │   │       ├── model/
 │   │       └── util/
 │   └── webapp/
 │       ├── META-INF/
 │       ├── WEB-INF/
 │       ├── views/
 │       └── index.jsp
 └── test/
     └── java/
         └── com.inventorytracker/
📝 Author
Developed by Aone Seanego
Computer Systems Engineering Student