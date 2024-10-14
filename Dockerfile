FROM maven:3.9.9-eclipse-temurin-21 AS build

COPY . .
RUN mvn clean package -DskipTests
#RUN mvn test

FROM eclipse-temurin:21-jre-noble

COPY --from=build target/*.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]