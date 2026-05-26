FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /build/notification-service
COPY pom.xml /build/pom.xml
COPY notification-service/pom.xml ./pom.xml
RUN mvn dependency:go-offline -q
COPY notification-service/src ./src
RUN mvn clean package -DskipTests -q

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=builder /build/notification-service/target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
