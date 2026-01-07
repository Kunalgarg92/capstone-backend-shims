FROM eclipse-temurin:17-jre

WORKDIR /hospital-service

COPY target/capstone-hospital-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
