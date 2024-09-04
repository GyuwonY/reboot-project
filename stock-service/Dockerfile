FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY /build/libs/*.jar /app/stock-service.jar
ENTRYPOINT ["java", "-jar", "/app/stock-service.jar"]