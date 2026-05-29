FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build/auth-service
COPY pom.xml /build/pom.xml
COPY auth-service/pom.xml ./pom.xml
RUN mvn dependency:go-offline -q
COPY auth-service/src ./src
RUN mvn clean package -DskipTests -q

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build /build/auth-service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
