# Midas Core - Financial Transaction Processing System

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.1.4-blue.svg)](https://kafka.apache.org/)
[![H2 Database](https://img.shields.io/badge/H2%20Database-2.2.224-yellow.svg)](https://www.h2database.com/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)](https://maven.apache.org/)

A comprehensive Spring Boot application that processes financial transactions through Kafka messaging, integrates with external incentive APIs, and provides REST endpoints for balance queries. This project was developed as part of the JPMC Advanced Software Engineering Forage program.

## 🌟 Features

- **Real-time Transaction Processing** via Apache Kafka
- **RESTful API** for balance queries and user management
- **Database Integration** with H2 and JPA/Hibernate
- **External Service Integration** for incentive calculations
- **Comprehensive Testing** with embedded Kafka
- **Event-Driven Architecture** for scalable processing
- **ACID Compliance** for financial data integrity

## 🏗️ Architecture Overview

Midas Core is a microservices-based financial transaction processing system built with Spring Boot that demonstrates modern software engineering practices including:

- **Event-Driven Architecture**: Kafka-based message processing
- **RESTful API Design**: Clean REST endpoints for balance queries
- **Database Integration**: JPA/Hibernate with H2 database
- **External Service Integration**: HTTP client for incentive API calls
- **Transaction Management**: ACID-compliant transaction processing
- **Test-Driven Development**: Comprehensive test suite with embedded Kafka

## 🚀 Key Features

### 1. Kafka Message Processing
- **Real-time Transaction Processing**: Consumes transaction messages from Kafka topics
- **Message Validation**: Validates sender/recipient existence and sufficient balance
- **Error Handling**: Graceful handling of invalid transactions

### 2. Database Integration
- **JPA/Hibernate**: Object-relational mapping for data persistence
- **H2 Database**: In-memory database for development and testing
- **Transaction Records**: Complete audit trail of all processed transactions
- **User Management**: User account management with balance tracking

### 3. Incentive API Integration
- **External Service Calls**: HTTP client integration with incentive service
- **Incentive Processing**: Automatic incentive calculation and application
- **Fallback Handling**: Graceful degradation when incentive service is unavailable

### 4. REST API
- **Balance Queries**: GET endpoint for user balance retrieval
- **JSON Serialization**: Automatic JSON response formatting
- **Error Handling**: Proper HTTP status codes and error responses

## 🛠️ Technology Stack

- **Java 17+**: Modern Java features and performance
- **Spring Boot 3.2.5**: Application framework and auto-configuration
- **Spring Kafka**: Kafka integration and message processing
- **Spring Data JPA**: Database abstraction and ORM
- **H2 Database**: Lightweight, in-memory database
- **Maven**: Build automation and dependency management
- **JUnit 5**: Testing framework
- **Embedded Kafka**: Testing infrastructure

## 📁 Project Structure

```
src/
├── main/java/com/jpmc/midascore/
│   ├── component/
│   │   ├── DatabaseConduit.java          # Core business logic
│   │   └── KafkaTransactionListener.java  # Kafka message processing
│   ├── config/
│   │   └── RestTemplateConfig.java       # HTTP client configuration
│   ├── controller/
│   │   └── BalanceController.java        # REST API endpoints
│   ├── entity/
│   │   ├── TransactionRecord.java        # Transaction JPA entity
│   │   └── UserRecord.java              # User JPA entity
│   ├── foundation/
│   │   ├── Balance.java                  # Balance DTO
│   │   ├── Incentive.java               # Incentive DTO
│   │   └── Transaction.java             # Transaction DTO
│   ├── repository/
│   │   ├── TransactionRecordRepository.java
│   │   └── UserRepository.java
│   └── MidasCoreApplication.java        # Main application class
└── test/java/com/jpmc/midascore/
    ├── TaskOneTests.java                # Basic application startup test
    ├── TaskTwoTests.java                # Kafka integration test
    ├── TaskThreeTests.java              # Database integration test
    ├── TaskFourTests.java               # Incentive API integration test
    └── TaskFiveTests.java               # REST API integration test
```

## 🔧 Configuration

### Application Properties (`application.yml`)
```yaml
server:
  port: 33400

general:
  kafka-topic: transactions

spring:
  kafka:
    bootstrap-servers: ${spring.embedded.kafka.brokers:localhost:9092}
    consumer:
      group-id: midas-core
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "*"
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
```

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Git

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/HRG-OFFICIAL/J.P.-Morgan-Software-Engineering-Job-Simulation.git
   cd J.P.-Morgan-Software-Engineering-Job-Simulation
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run tests**
   ```bash
   mvn test
   ```

4. **Start the application**
   ```bash
   mvn spring-boot:run
   ```

## 🧪 Testing

The project includes comprehensive test suites that demonstrate different aspects of the system:

### Test Suite Overview
- **TaskOneTests**: Basic Spring Boot application startup
- **TaskTwoTests**: Kafka message processing integration
- **TaskThreeTests**: Database transaction processing
- **TaskFourTests**: External incentive API integration
- **TaskFiveTests**: REST API balance queries

### Running Specific Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TaskOneTests

# Run with specific profile
mvn test -Dspring.profiles.active=test
```

## 📡 API Endpoints

### Balance Query
- **Endpoint**: `GET /balance?userId={userId}`
- **Description**: Retrieves the current balance for a specific user
- **Parameters**: 
  - `userId` (Long): The ID of the user
- **Response**: JSON object with balance amount
- **Example**: `GET http://localhost:33400/balance?userId=1`

## 🔄 Transaction Processing Flow

1. **Message Reception**: Kafka listener receives transaction message
2. **Validation**: Verify sender/recipient existence and sufficient balance
3. **Incentive Calculation**: Call external incentive API
4. **Transaction Processing**: Update user balances and create transaction record
5. **Persistence**: Save changes to database
6. **Logging**: Record transaction success/failure

## 🏛️ Database Schema

### UserRecord Entity
- `id`: Primary key
- `name`: User name
- `balance`: Current account balance

### TransactionRecord Entity
- `id`: Primary key
- `sender`: Reference to sender UserRecord
- `recipient`: Reference to recipient UserRecord
- `amount`: Transaction amount
- `incentive`: Incentive amount received
- `timestamp`: Transaction timestamp

## 🔌 External Integrations

### Incentive API
- **Endpoint**: `POST http://localhost:8080/incentive`
- **Purpose**: Calculate incentive amounts for transactions
- **Request**: Transaction object (JSON)
- **Response**: Incentive object with amount
- **Fallback**: Returns 0.0 incentive if service unavailable

## 🚦 Error Handling

- **Invalid Transactions**: Gracefully rejected with logging
- **API Failures**: Fallback to default values
- **Database Errors**: Transaction rollback on failures
- **Network Issues**: Retry logic and timeout handling

## 📊 Performance Considerations

- **Async Processing**: Non-blocking Kafka message processing
- **Connection Pooling**: Efficient database connection management
- **Caching**: Spring's built-in caching mechanisms
- **Batch Processing**: Optimized database operations

## 🔒 Security Features

- **Input Validation**: Comprehensive parameter validation
- **SQL Injection Prevention**: JPA/Hibernate ORM protection
- **Error Information**: Limited error details in responses
- **Transaction Isolation**: ACID compliance for data integrity

## 🧩 Design Patterns

- **Repository Pattern**: Data access abstraction
- **Dependency Injection**: Spring's IoC container
- **Observer Pattern**: Kafka message processing
- **Template Method**: Spring's RestTemplate
- **Builder Pattern**: Entity construction

## 📈 Monitoring & Logging

- **Structured Logging**: SLF4J with Logback
- **Transaction Tracking**: Complete audit trail
- **Performance Metrics**: Built-in Spring Boot Actuator
- **Health Checks**: Application health monitoring

## 🚀 Deployment

### Development
```bash
mvn spring-boot:run
```

### Production
```bash
mvn clean package
java -jar target/midas-core-1.0.0.jar
```

### Docker (Optional)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/midas-core-1.0.0.jar app.jar
EXPOSE 33400
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is part of the JPMC Advanced Software Engineering Forage program.

## 🔗 References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Kafka Documentation](https://spring.io/projects/spring-kafka)
- [JPA/Hibernate Documentation](https://spring.io/projects/spring-data-jpa)
- [Example Implementation](https://github.com/vagabond-systems/forage-midas-complete)

## 🌐 Website & Documentation

- **Repository**: https://github.com/HRG-OFFICIAL/J.P.-Morgan-Software-Engineering-Job-Simulation
- **Documentation**: Complete API documentation and architecture overview in README
- **Examples**: Comprehensive test suite demonstrating all features
- **Deployment**: Docker and production deployment guides included

## 👥 Team

Developed as part of the JPMC Advanced Software Engineering Forage program, demonstrating enterprise-level software development practices and modern Java/Spring Boot technologies.

## 📊 Repository Stats

- **Language**: Java
- **Framework**: Spring Boot 3.2.5
- **Database**: H2 with JPA/Hibernate
- **Messaging**: Apache Kafka
- **Testing**: JUnit 5 with Embedded Kafka
- **Build Tool**: Maven
- **Java Version**: 17+

## 🏷️ Topics

`java` `spring-boot` `kafka` `microservices` `rest-api` `database` `jpa` `hibernate` `h2` `maven` `junit` `testing` `financial-services` `transaction-processing` `event-driven` `jpmc` `forage-program`

---

**Note**: This project is for educational purposes and demonstrates modern software engineering practices including microservices architecture, event-driven design, and comprehensive testing strategies.