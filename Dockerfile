# Use a multi-stage build to create a lean final image
FROM maven:3.8.5-openjdk-17 AS builder

# Set the working directory
WORKDIR /app

# Copy the POM file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src/ src/

# Build the application JAR
RUN mvn clean package -DskipTests

# --- Second Stage: Create the final image ---
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/testify-*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application. All configuration will be passed via environment variables.
ENTRYPOINT ["java", "-jar", "app.jar"]
