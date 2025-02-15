# Ticketing System

## 📌 Overview
This project is a comprehensive **Ticketing System** that includes:
- **Java CLI Application** for parameter configuration and simulation.
- **Spring Boot Backend** for managing ticket operations, vendors, and customers.
- **React Frontend** for a user-friendly web interface.

The system facilitates real-time event ticketing with multi-threading, WebSocket communication, and REST API integration.

---

## 🏗️ Project Structure
```
TicketingSystem/
│-- cli/            # Java CLI application
│-- backend/        # Spring Boot backend
│-- frontend/       # React frontend
│-- README.md       # Project documentation
```

---

## 🚀 Features
✅ **Java CLI**
- Allows configuration of system parameters (e.g., ticket release rate, customer retrieval rate, etc.).
- Simulates ticket vendors and customers using multi-threading.
- Implements synchronization for data integrity.

✅ **Spring Boot Backend**
- RESTful APIs for managing ticket operations.
- WebSocket for real-time ticket updates.
- Database integration for persistence.

✅ **React Frontend**
- Displays available tickets and transaction history.
- Provides a real-time interface using polling/WebSocket.
- User authentication and role-based access.

---

## 🔧 Installation & Setup
### 1️⃣ Clone the Repository
```
git clone https://github.com/hasindi1230/TicketingSystem.git
cd TicketingSystem
```

### 2️⃣ Java CLI Setup
```
cd cli
javac Main.java
java Main
```

### 3️⃣ Backend Setup (Spring Boot)
```
cd backend
mvn spring-boot:run
```

### 4️⃣ Frontend Setup (React)
```
cd frontend
npm install
npm start
```

## 🔗 Technologies Used
- **Java CLI**: Multi-threading, synchronization
- **Spring Boot**: REST API, WebSocket, MySQL
- **React**: Hooks, Context API, WebSocket integration

---

## 📬 Contact
For any inquiries, reach out to me via GitHub or email.


