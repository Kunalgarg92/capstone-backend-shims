FROM eclipse-temurin:17-jre

WORKDIR /api-gateway-assignment

COPY target/capstone-api-gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
