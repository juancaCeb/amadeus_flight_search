FROM openjdk:23-jdk

WORKDIR /flight_search_backend

COPY build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]