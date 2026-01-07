FROM eclipse-temurin:17-jre

WORKDIR /policy-service

COPY target/capstone-policy-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
