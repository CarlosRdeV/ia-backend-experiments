FROM openjdk:17-jdk-slim
COPY target/backend-starter.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
