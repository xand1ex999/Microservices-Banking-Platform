FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build/api-gateway
COPY pom.xml /build/pom.xml
COPY api-gateway/pom.xml ./pom.xml
RUN mvn dependency:go-offline -q
COPY api-gateway/src ./src
RUN mvn clean package -DskipTests -q

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build /build/api-gateway/target/*.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]
