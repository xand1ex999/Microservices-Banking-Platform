FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build/monolith
COPY pom.xml /build/pom.xml
COPY monolith/pom.xml ./pom.xml
RUN mvn dependency:go-offline
COPY monolith/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/monolith/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
