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
# Use the same image as the builder stage to ensure it's found by the build environment.
FROM maven:3.8.5-openjdk-17

# Set the working directory
WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/testify-*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application, explicitly setting the 'render' profile.
# This is the most reliable way to ensure the correct properties are loaded.
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=render"]
