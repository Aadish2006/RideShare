# 🚗 RideShare Backend

RideShare is a simple Spring Boot backend that simulates a basic ride-booking system.  
Users can request rides, drivers can accept them, and both can complete the ride.  
All actions are secured using JWT authentication, and data is stored in MongoDB.

---

## Features
- User & Driver registration and login  
- JWT-based authentication  
- Ride creation by users  
- Drivers view and accept pending ride requests  
- Ride completion workflow  
- User ride history retrieval  

---

## Tech Stack
- Spring Boot  
- MongoDB  
- Spring Security (JWT)  
- Maven  

---

## Running the Application

1. Configure your MongoDB URI and JWT secret in `application.properties`.
2. Start the application:

```bash
mvn spring-boot:run
