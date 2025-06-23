# 📚 Book Management System (Java Swing + MySQL)

A simple desktop application for managing books, built using **Java Swing** for the user interface and **MySQL** as the backend. This application allows users to register, log in, and manage their personal collection of books through an intuitive dashboard.

---

## 🚀 Features

- 🔐 User Registration and Login
- 📥 Add Books (with Title, Author, and Category)
- 📋 View Book List in Table Format
- ❌ Delete Books by Selection
- 🛢️ MySQL Database Integration using JDBC
- 🧭 Navigation between Login and Register Screens
- 🖥️ Built with Java Swing (GUI)

---

## 🔧 Tech Stack

- **Java SE** (Swing for GUI)
- **MySQL** (Database)
- **JDBC** (Java Database Connectivity)
- **Eclipse IDE** (Recommended for development)
- **Git** + **GitHub** (for version control)

---

## 🗂 Project Structure



src/
└── com.authapp/
├── Login.java # Login screen logic
├── Register.java # Registration screen logic
├── Dashboard.java # Main book management dashboard
└── DBConnection.java # MySQL JDBC database connection



---

## 🔄 Workflow

1. **User opens application**
2. **Login** or **Register** if new
3. On successful login → user is redirected to **Dashboard**
4. In Dashboard:
   - View all books
   - Add new books
   - Delete existing books
5. All data is stored in **MySQL** database

---

## 🛠️ How to Run This Project in Eclipse

### ✅ Prerequisites

- Java JDK installed (11+ recommended)
- MySQL Server running
- Eclipse IDE installed
- MySQL JDBC Driver (Connector/J)

### 📥 Steps

1. **Clone this repository** or download the ZIP.
2. Open Eclipse → **File > Import > Existing Projects into Workspace**.
3. Right-click project → **Build Path > Configure Build Path**.
4. Add MySQL JDBC JAR:
   - Download from [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
   - Go to **Libraries > Add External JARs**
   - Select the downloaded `.jar` file
5. Edit `DBConnection.java` with your MySQL credentials:
   
CREATE DATABASE IF NOT EXISTS bookdb;
USE bookdb;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    author VARCHAR(100),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Insert sample categories
INSERT INTO categories (name) VALUES ('Fiction'), ('Science'), ('Tech');
