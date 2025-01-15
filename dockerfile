# Use the official Maven image to build the app
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY ./Auth/pom.xml .
COPY ./Auth/src ./src
RUN mvn clean package 

FROM openjdk:17-jdk-alpine
WORKDIR /app
ARG CACHEBUST=324
COPY ./target/Auth-0.0.1-SNAPSHOT.jar app.jar
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/users
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=Himalayan@2024R
ENV SPRING_PROFILES_ACTIVE=docker
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]