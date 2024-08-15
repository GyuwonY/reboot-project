FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY /build/libs/*.jar /app/user-service.jar
ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]