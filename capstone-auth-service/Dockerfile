FROM eclipse-temurin:17-jre

WORKDIR /auth-service

COPY target/capstone-auth-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
