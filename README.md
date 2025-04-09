# 🧱 backend-starter
Proyecto base para microservicios con Spring Boot 3, configurado para servir como plantilla para nuevos proyectos backend modernos.

---

## 🔧 Stack Tecnológico

- ☕ Java 17
- 🚀 Spring Boot 3.4.4
- 🧱 Spring Web
- 🔐 Spring Security (permitAll por defecto)
- 📝 Swagger / OpenAPI
- 🐳 Docker-ready
- ✅ Validación con `@Valid`
- ⚙️ Estructura por capas
- 🧠 Preparado para integración con IA, Cloud y DevSecOps

---

## 🚀 Ejecutar el proyecto

### 🧪 En local

./mvnw spring-boot:run

🐳 Con Docker

./mvnw clean package -DskipTests
docker build -t backend-starter .
docker run -p 8080:8080 backend-starter

📚 Documentación Swagger
Una vez levantado:

http://localhost:8080/swagger-ui/index.html

📂 Estructura del proyecto

src/
├── main/
│   ├── java/com/carlosrdev/backendstarter/
│   │   ├── config/           # Configuraciones globales (ej. seguridad)
│   │   ├── controller/       # Endpoints REST
│   │   ├── model/            # DTOs o entidades
│   │   ├── repository/       # Repositorios (cuando se usen)
│   │   ├── service/          # Lógica de negocio
│   │   └── BackendStarterApplication.java
│   └── resources/
│       └── application.yml

🔐 Seguridad
Por ahora, todos los endpoints están abiertos (permitAll).
La clase SecurityConfig está lista para añadir autenticación más adelante (JWT, roles, etc.).

🔥 ¿Para qué sirve?
Este proyecto forma parte de un roadmap técnico personal enfocado en la especialización backend:
➡️ Backend moderno,
➡️ Integración con IA,
➡️ Cloud automation,
➡️ DevSecOps progresivo.

Desarrollado por Carlos Adrián Rivera Rodríguez
https://www.linkedin.com/in/carlosrdev/
