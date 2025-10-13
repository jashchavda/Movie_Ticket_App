# Use a lightweight Java 17 runtime as the base image
FROM eclipse-temurin:17-jre

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file from your local target folder into the container
COPY target/movie_ticket_1-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that Spring Boot runs on (default: 8080)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
