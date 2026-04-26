# Importing JDK and copying required files
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copy the JAR from the build stage
COPY target/bf-url-shortner-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java","-jar","/app.jar"]