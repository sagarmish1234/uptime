# Use Maven image to build the application
FROM maven:3.8.4-openjdk-21 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and the source code
COPY . .

# Package the application
RUN mvn clean package -DskipTests

# Use OpenJDK 21 image to run the application
FROM openjdk:21-jdk-slim


# Copy the jar file from the build stage
COPY --from=build /target/webapp-1.0-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java","-Ddb_password=E3gVAdRt886GCe2V","-jar", "app.jar"]

# Note: Replace "your-app-name.jar" with the actual name of your jar file.
