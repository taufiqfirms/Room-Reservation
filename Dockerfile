# Tahap 1: Bangun proyek
FROM maven:3.6-openjdk-17-slim AS build
WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests && rm -rf /app/src /app/pom.xml

FROM openjdk:17-jdk-alpine
WORKDIR /app


COPY --from=build /app/target/enigma-booking-management-0.0.1-SNAPSHOT.jar /app/enigma-booking-management-0.0.1-SNAPSHOT.jar


# copy artifact .jar
CMD ["java", "-jar", "/app/enigma-booking-management-0.0.1-SNAPSHOT.jar"]
#CMD ["java", "-jar", "app.jar"]
