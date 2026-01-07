FROM eclipse-temurin:17-jre

WORKDIR /eureka-server

COPY target/capstone-Eureka-server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
