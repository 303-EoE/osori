# Start from the default Docker image.
FROM openjdk:17-jdk-slim

#Copy the application jar file and resources
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

COPY src/resources/application.yml /app/application.yml

WORKDIR /app

#Set the entrypoint
ENTRYPOINT ["java", "-jar", "/app.jar"]
