# Multi-stage Docker build for Java 21 Spring Boot + Frontend

# Stage 1: Build stage
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy root frontend & backend sources
COPY frontend ./frontend
COPY backend ./backend

# Build backend (runs tests and bundles frontend static assets into JAR)
WORKDIR /app/backend
RUN mvn clean package

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy generated executable JAR from build stage
COPY --from=build /app/backend/target/banking-system-1.0.0.jar app.jar

# Expose default port
EXPOSE 8080

# Run Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
