FROM eclipse-temurin:17-jre

WORKDIR /claims-service

COPY target/capstone-claims-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
