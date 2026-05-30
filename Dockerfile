FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY build/libs/*.jar app.jar

COPY src/main/webapp /app/src/main/webapp

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
