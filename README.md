# 🎟️ Evently – Event Management Portal

## 1. 📌 Project Description

Evently is a web-based Event Management System designed to simplify event creation, registration, and participation.

It provides role-based access to two types of users:

### 👨‍💼 Administrator
- Login
- Create & Manage Events
- View Participant Registrations
- Track Payments
- Logout

### 👤 Participant
- Register as New User
- Login as Existing User
- Browse & Register for Events
- Make Payments
- View Registered Events
- Logout

---

## 2. 🚀 Tech Stack

- Frontend: HTML, CSS, JavaScript  
- Backend: Java (Servlets, JDBC)  
- Database: MySQL  
- Server: Apache Tomcat  
- Tools: NetBeans, Git & GitHub  

---

## 3. ⚙️ Installation & Setup

### 🔧 Prerequisites
- Apache NetBeans (13 or above)
- Apache Tomcat Server (10+)
- MySQL Server
- MySQL Workbench (Optional)

---

### 📥 Clone the Repository

```bash
git clone https://github.com/YOUR-USERNAME/Event-Management-System-Java.git
```

---

### ▶️ Run the Project

1. Open project in NetBeans  
2. Start MySQL Server  
3. Start Apache Tomcat Server  
4. Run the application  

---

### 🔐 Login Options

#### 🛠️ Admin Login Credentials
| Username | Password |
|----------|---------|
| A101     | Admin101 |
| A202     | Admin202 |
| A303     | Admin303 |
| A404     | Admin404 |

---

#### 👤 Participant
- Register as a new user  
- OR login with existing credentials  

---

## 4. 💳 Payment Integration (Razorpay)

Evently integrates Razorpay Payment Gateway for secure online transactions.

### Features:
- Secure payment processing  
- Real-time payment confirmation  
- Transaction tracking  
- Payment success handling via servlet  

---

## 5. 🎫 Ticket Generation & QR Verification

After successful registration and payment:

- Digital ticket is generated  
- Unique QR code is created for each ticket  
- QR can be scanned for entry verification  

### Benefits:
- Fast check-in  
- Fraud prevention  
- Easy validation  

---

## 6. ⭐ Future Enhancements

- Email/SMS notifications  
- Payment options (UPI, PayPal, Google Pay)  
- Admin analytics dashboard  
- Mobile responsiveness  

---

## 7. 📸 Screenshot

![Landing Page](web/screenshot.jpeg)

---

## 8. 📂 Project Structure

- src/java/com/evently/ → Backend logic  
- web/ → Frontend files  
- WEB-INF/ → Config files  

---

## 9. 👨‍💻 Author

Shivam Sharma

---

## 🔗 Note

Make sure your database configuration and Razorpay API keys are properly set before running the project.
