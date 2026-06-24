# Stage 1: Build the application
# Use a modern Maven image based on Eclipse Temurin
FROM maven:3.9.5-eclipse-temurin-17 as builder

WORKDIR /app

# Copy only pom.xml to leverage Docker cache layers
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src/ src/

# Build the application, skipping tests
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
# Use the official Eclipse Temurin JRE (Java Runtime Environment) image, which is smaller
FROM eclipse-temurin:17-jre-slim

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/testify-*.jar app.jar

# Health check to ensure the application is running
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD java -cp app.jar org.springframework.boot.loader.JarLauncher -Dspring.boot.endpoint.health.path=/actuator/health || exit 1

# Expose the application port
EXPOSE 8080

# Set the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Default command can be used to set profiles, but it's better to use environment variables
CMD ["--spring.profiles.active=default"]
