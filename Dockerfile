# Stage 1: Build
FROM maven:3.8.1-openjdk-17-slim as builder

WORKDIR /app

# Copy only pom.xml to leverage Docker cache layers
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src/ src/

# Build application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-slim

WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder /app/target/testify-*.jar app.jar

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD java -cp app.jar org.springframework.boot.loader.JarLauncher -Dspring.boot.endpoint.health.path=/actuator/health || exit 1

# Expose port
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Default profile (can be overridden at runtime)
CMD ["--spring.profiles.active=default"]
