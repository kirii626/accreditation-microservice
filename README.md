# 📄 Accreditation Microservice Documentation

## 📁 Table of Contents

- [English Version](#english-version)
- [Versión en Español](#versión-en-español)

---

## 🇬🇧 English Version

### 🧩 Overview

This microservice manages **accreditations (money orders)** within a distributed system. It handles order creation by users and allows administrators to review all accreditations. It also communicates asynchronously with the Notification Microservice via RabbitMQ to send email alerts.

---

### 🏗️ Project Structure

```
accreditation_service
├── config
├── controllers
│   ├── AdminAccreditationController.java
│   └── UserAccreditationController.java
├── dtos
├── exceptions
├── events
│   └── AccreditationEventPublisher.java
├── feign
│   ├── SalePointClient.java
│   └── UserClient.java
├── models
├── repositories
├── services
│   └── AccreditationServiceImpl.java
├── utils
└── AccreditationServiceApplication.java
```

---

### 🔐 Security & Validation

- JWT authentication and role validation.
- Admin endpoints restricted to users with the `ADMIN` role.
- Internal requests are secured with interceptors.
- Input validation using `javax.validation` annotations.
- Redis Caching enabled via `@EnableCaching`.

---

### 🧾 Endpoints

#### 🎫 User Accreditation (`/api/accreditation/user`)

| Method | Endpoint            | Description                          |
|--------|---------------------|--------------------------------------|
| POST   | `/create`           | Create an accreditation order.       |

![](docs\images\UserAccreditationController-createAccreditation.png)

#### 🔒 Admin Accreditation (`/api/accreditation/admin`)

| Method | Endpoint            | Description                          |
|--------|---------------------|--------------------------------------|
| GET    | `/all`              | Fetch all accreditations.            |


![](docs\images\AdminAccreditationController-getAllAccreditations.png)

---

### 🔁 Inter-service Communication

- **Feign Clients** used to fetch user and sale point data from:
    - `user-microservice`
    - `sale-point-service`
- Validations ensure both user and sale point exist before processing.

---

### 📬 Messaging – RabbitMQ

- On accreditation creation, an event is published:
    - **Exchange**: `${accreditation.exchange}`
    - **Routing Key**: `${rabbitmq.routingkey.accreditation}`
    - **Payload**: `AccreditationDtoOutput`
- A Dead Letter Queue (`accreditation.dlq`) is also configured.

---

### 🧪 Profiles

| Profile    | Description                                   |
|------------|-----------------------------------------------|
| `default`  | Shared base configuration.                    |
| `dev`      | Uses H2 in-memory database.                   |
| `init`     | Loads initial data with PostgreSQL.           |
| `docker`   | Production mode with PostgreSQL + RabbitMQ.   |

---

### 📦 Key Dependencies

- Spring Boot 3.4.4
- Spring Data JPA
- Spring Security + JWT(jjwt)
- Spring Cloud (Eureka, OpenFeign)
- Spring AMQP (RabbitMQ)
- Redis Cache
- H2 & PostgreSQL
- Jacoco + SonarQube

---
### 🎯 SonarQube – Code Quality

![](docs\images\sonarqube-stats.png)

- ✅ Coverage: 95%
- ✅ Security: No issues
- ✅ Maintainability: Grade A

---

### 🚀 Run Locally

Dev profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Init profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=init
```

Docker profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=docker
```

### 🐳 Docker or Podman

Ready for deployment on Docker as part of a larger stack (gateway, config-server, etc.). Typically runs alongside Eureka Server and PostgreSQL.

### ✅ Testing & Coverage
Coverage is managed by Jacoco and reported to SonarQube. To run tests, create an `application-test.properties` profile with all necessary environment variables and configurations, and ensure you have a SonarQube container or installation. You can generate reports locally with:

```bash
./mvnw clean verify
# See: target/site/jacoco/index.html
```

---

## 🇪🇸 Versión en Español

### 🧩 Descripción General

Este microservicio gestiona **acreditaciones (órdenes de dinero)** dentro de un sistema distribuido. Permite a usuarios crear órdenes y a administradores ver todas las acreditaciones.
También envía eventos por RabbitMQ al microservicio de notificaciones para generar un mail con un PDF que contiene todos los datos de la acreditación.

---

### 🏗️ Estructura del Proyecto

```
accreditation_service
├── config
├── controllers
│   ├── AdminAccreditationController.java
│   └── UserAccreditationController.java
├── dtos
├── exceptions
├── events
├── feign
│   ├── SalePointClient.java
│   └── UserClient.java
├── models
├── repositories
├── services
├── utils
└── AccreditationServiceApplication.java
```

---

### 🔐 Seguridad y Validaciones

- Autenticación JWT y validación de roles.
- Endpoints protegidos por interceptores.
- Validaciones con anotaciones `javax.validation`.
- Redis habilitado para caché (`@EnableCaching`).

---

### 🧾 Endpoints

#### 🎫 Acreditación Usuario (`/api/accreditation/user`)

| Método | Endpoint     | Descripción                         |
|--------|--------------|-------------------------------------|
| POST   | `/create`    | Crea una orden de acreditación.     |

![](docs\images\UserAccreditationController-createAccreditation.png)

#### 🔒 Acreditación Admin (`/api/accreditation/admin`)

| Método | Endpoint     | Descripción                         |
|--------|--------------|-------------------------------------|
| GET    | `/all`       | Lista todas las acreditaciones.     |

![](docs\images\AdminAccreditationController-getAllAccreditations.png)


---

### 🔁 Comunicación entre Microservicios

- Clientes Feign para obtener datos de usuario y punto de venta:
    - `user-microservice`
    - `sale-point-service`
- Se valida la existencia de usuario y punto de venta antes de procesar.

---

### 📬 Mensajería – RabbitMQ

- Al crear una acreditación, se envía un evento:
    - **Exchange**: `${accreditation.exchange}`
    - **Routing Key**: `${rabbitmq.routingkey.accreditation}`
    - **Payload**: `AccreditationDtoOutput`
- Se configura también una Dead Letter Queue (`accreditation.dlq`).

---

### 🧪 Perfiles

| Perfil     | Descripción                                      |
|------------|--------------------------------------------------|
| `default`  | Configuración común para todos los entornos.     |
| `dev`      | Usa H2 en memoria.                               |
| `init`     | Usa PostgreSQL y carga datos iniciales.          |
| `docker`   | Producción con PostgreSQL y RabbitMQ.            |

---

### 📦 Dependencias Principales

- Spring Boot 3.4.4
- Spring Data JPA
- Spring Security + JWT(jjwt)
- Spring Cloud (Eureka, OpenFeign)
- Spring AMQP (RabbitMQ)
- Redis Cache
- H2 & PostgreSQL
- Jacoco + SonarQube

---

### 🎯 SonarQube – Calidad de Código

![](docs\images\sonarqube-stats.png)

- ✅ Cobertura: 95%
- ✅ Seguridad: Sin problemas
- ✅ Mantenibilidad: Grado A

### 🚀 Ejecución local

Perfil dev:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Perfil init:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=init
```

Perfil docker:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=docker
```

### 🐳 Docker

Este microservicio está listo para correr en entornos Docker como parte de un stack mayor (gateway, config-server, etc.). Usualmente se ejecuta en conjunto con Eureka Server y una base PostgreSQL.

### ✅ Pruebas y Cobertura

La cobertura es gestionada por Jacoco y reportada a SonarQube, por lo que si quieres ejecutar los tests, deberás crear un perfil application-test.properties y
definir todas las variables de entorno y configuraciones, además deberás tener un contenedor de SonarQube o instalarlo. Puedes generar los reportes localmente con:


```bash
./mvnw clean verify
# Ver: target/site/jacoco/index.html
```