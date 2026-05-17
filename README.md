# ✨ Sparkle & Shine Car Wash Management System

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.x-orange)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📋 Project Overview

A comprehensive **web-based car wash management system** developed for Sparkle & Shine Car Wash in Witbank, Mpumalanga. This system digitizes daily operations, replacing manual paper-based booking systems with a modern, efficient digital solution.

**Course:** INT316D - Web Development  
**Semester:** Semester 1 - 2026  
**Presentation Date:** 6 & 7 May 2026

---

## 🚀 Features

### 👑 Admin Dashboard
- 📊 Real-time statistics (Today's bookings, revenue, pending tasks)
- 📈 Interactive charts (Booking status distribution, popular services)
- 👥 Employee management (Register, view, delete staff)
- 📅 Booking management (Create, view, assign bookings)
- 🏆 Performance leaderboard (Track top employees weekly)
- 🌙 Dark mode support

### 👤 Employee Portal
- 📋 View assigned tasks only
- ▶️ Start / ✅ Complete task buttons
- 📧 Automatic email notifications to customers
- 📊 Personal progress bar
- 📅 Today's schedule view
- 🌙 Dark mode support

### 📧 Email Notifications
- **Booking Confirmation** - Sent when admin creates a booking
- **Completion Email** - Sent when employee marks task as COMPLETED

### 🏆 Performance Tracking
- Weekly leaderboard with 🥇🥈🥉 medals
- Bonus calculation (R50 per 10 cars washed)
- Completion rate tracking
- Employee performance history

---

## 🛠️ Technology Stack

| Layer | Technology |
|-------|------------|
| **Backend** | Java 17, Spring Boot 3.5.14 |
| **Security** | Spring Security, BCrypt |
| **Database** | MySQL 8.x, Spring Data JPA |
| **Frontend** | Thymeleaf, Bootstrap 5 |
| **Charts** | Chart.js |
| **Email** | Spring Mail (JavaMail) |
| **Icons** | FontAwesome 6 |
| **Build Tool** | Maven |
| **Server** | Apache Tomcat (embedded) |

---

## 📁 Database Schema

### Core Tables

| Table | Description |
|-------|-------------|
| `user` | Admin & Employee accounts |
| `booking` | All car wash bookings |
| `employee_performance` | Weekly performance metrics |
| `service_tier` | Service packages (Basic → Full Detail) |
| `customer` | Customer information |
| `addon` | Optional extra services |

### Service Packages

| Package | Duration | Price |
|---------|----------|-------|
| Basic Wash | 20 min | R80 |
| Standard Wash | 35 min | R150 |
| Premium Wash | 60 min | R250 |
| Full Detail | 120 min | R450 |

---

## 🔧 Installation & Setup

### Prerequisites

- Java 17 or higher
- MySQL 8.x
- Maven (or use included Maven wrapper)
- Git

### Step 1: Clone the Repository

```bash
git clone https://github.com/nyalungumduduzi/sparkle-carwash.git
cd sparkle-carwash
Step 2: Create Database
sql
CREATE DATABASE sparkle_carwash;
Step 3: Configure Application
Edit src/main/resources/application.properties:

properties
spring.datasource.url=jdbc:mysql://localhost:3306/sparkle_carwash
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
Step 4: Run the Application
bash
# Using Maven Wrapper (Windows)
./mvnw spring-boot:run

# Using Maven Wrapper (Mac/Linux)
./mvnw spring-boot:run
Step 5: Access the Application
Open browser and go to: http://localhost:8080

🔑 Default Login Credentials
Role	Username	Password
Admin	admin	admin123
Employee	employee1	emp123

📂 Project Structure
text
sparkle-carwash/
├── src/
│   ├── main/
│   │   ├── java/com/sparklshine/carwash/
│   │   │   ├── config/          # Security & Web configuration
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── service/         # Business logic
│   │   │   ├── repository/      # Database access
│   │   │   └── entity/          # JPA entities
│   │   └── resources/
│   │       ├── static/          # CSS, images
│   │       └── templates/       # HTML templates
│   └── test/                    # Unit tests
├── docs/                        # Documentation
├── pom.xml                      # Maven dependencies
└── README.md                    # This file

🎯 Rubric Compliance
Criteria	Status
No NetBeans IDE (used VS Code)	✅
No JEE framework (used Spring Boot)	✅
No Glassfish (used embedded Tomcat)	✅
Uploaded to GitHub	✅
System Report	✅
Reflection Statements	✅
Full Functionality	✅
🚀 Future Enhancements
💳 Online payment integration (PayFast)

📱 WhatsApp Business API integration

📲 Customer mobile app (PWA)

📍 Multi-branch support

📊 PDF report export

🔔 SMS notifications

📞 Contact
Sparkle & Shine Car Wash
📍 123 Main Street, Witbank (eMalahleni), Mpumalanga, 1035
📞 +27 13 000 0000
📧 info@sparklshine.co.za

📝 License
This project is submitted as part of academic requirements for INT316D.

⭐ Show Your Support
If you found this project helpful, please give it a ⭐ on GitHub!

© 2026 Sparkle & Shine Car Wash | All Rights Reserved

