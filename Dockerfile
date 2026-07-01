# Dockerfile optimized for Render.com deployment
# Multi-stage build to minimize final image size

# ==========================================
# Stage 1: Build Stage
# ==========================================
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

# Copy pom.xml first for dependency caching
# This layer is cached unless pom.xml changes
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
# -DskipTests: Skip tests for faster builds
# -q: Quiet mode (less log output)
RUN mvn clean package -DskipTests -q

# ==========================================
# Stage 2: Runtime Stage
# ==========================================
# Use Alpine Linux for smaller image size (~400MB)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Install curl for health checks
RUN apk add --no-cache curl

# Copy the built JAR from builder stage
COPY --from=builder /build/target/testify-*.jar app.jar

# Create non-root user for security
RUN addgroup -g 1001 -S spring && \
    adduser -u 1001 -S spring -G spring

# Switch to non-root user
USER spring

# Expose port (Render will use PORT environment variable)
EXPOSE 8080

# Health check for Render
# Render uses this to determine if service is healthy
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# JVM configuration optimized for Render's limited resources
# -Xmx256m: Maximum heap size (Render free tier has limited memory)
# -Xms128m: Initial heap size (reduces startup time)
# -XX:+UseG1GC: Use G1 garbage collector (good for containers)
# -XX:MaxGCPauseMillis=200: Target GC pause time
# -XX:+UseStringDeduplication: Reduce string memory usage
ENTRYPOINT ["java", \
    "-Xmx256m", \
    "-Xms128m", \
    "-XX:+UseG1GC", \
    "-XX:MaxGCPauseMillis=200", \
    "-XX:+UseStringDeduplication", \
    "-jar", \
    "app.jar"]